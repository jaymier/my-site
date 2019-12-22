package com.jaymie.controller.admin;

import com.github.pagehelper.PageInfo;
import com.jaymie.constant.ErrorConstant;
import com.jaymie.constant.Types;
import com.jaymie.constant.WebConst;
import com.jaymie.exception.BusinessException;
import com.jaymie.model.FastDFSFile;
import com.jaymie.model.TPubFile;
import com.jaymie.service.file.ITPubFileService;
import com.jaymie.utils.APIResponse;
import com.jaymie.utils.Commons;
import com.jaymie.utils.FastDFSUtil;
import com.zckj.micservice.bean.BaseController;
import com.zckj.micservice.bean.common.ReturnJson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author lijunqi
 * @Description 文件管理控制类
 * @Date 2019/4/17 15:54
 */

@Controller
@RequestMapping("admin/attach")
public class PubFileController extends BaseController {

    @Autowired
    private ITPubFileService pubFileService;

    @ApiOperation("文件管理首页")
    @GetMapping(value = "")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "12")
                    int limit,
            HttpServletRequest request
    ){
        PageInfo<Map<String, Object>> file = pubFileService.getfiles(page, limit);
        request.setAttribute("attachs", file);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConst.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }

    /**
     * @Des: 单文件上传
     * @Param: [file, tPubFile]
     * @return: com.zckj.micservice.bean.common.ReturnJson
     * @Author: lijunqi
     * @Date: 2019/4/17 16:44
     */
    @ApiOperation("markdown文件上传")
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public void uploadSingleFile(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @ApiParam(name = "editormd-image-file", value = "文件数组", required = true)
                                 @RequestParam(name = "editormd-image-file", required = true)
                                 MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw BusinessException.withErrorCode(ErrorConstant.File.UPLOAD_FILE_FAIL);
            }
            Map<String,Object> paramMap = new HashMap<>(9);
            String sourceName = file.getOriginalFilename();
            String fileSuffix = sourceName.substring(sourceName.lastIndexOf(".") + 1, sourceName.length());
            //上传文件到fastDFS服务器
            FastDFSUtil client = FastDFSUtil.getFastProperties();
            FastDFSFile fsfile = new FastDFSFile(file.getBytes(), fileSuffix);
            String savePath = client.uploadFile(fsfile.getContent(), fileSuffix);
            paramMap.put("sourceName", sourceName);
            paramMap.put("fileSuffix", fileSuffix);
            paramMap.put("reName", savePath.substring(savePath.lastIndexOf("/") + 1));
            paramMap.put("fileUrl", client.getIpPort() + savePath);
            pubFileService.uploadSingleFile(paramMap);
            response.getWriter().write( "{\"success\": 1, \"message\":\"上传成功\",\"url\":\"" + client.getIpPort() + savePath + "\"}" );
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.getWriter().write( "{\"success\":0}" );
            } catch (IOException e1) {
                throw BusinessException.withErrorCode(ErrorConstant.File.UPLOAD_FILE_FAIL)
                        .withErrorMessageArguments(e.getMessage());
            }
            throw BusinessException.withErrorCode(ErrorConstant.File.UPLOAD_FILE_FAIL)
                    .withErrorMessageArguments(e.getMessage());
        }
    }

    /**
     * @Des: 批量上传文件
     * @Param: [files, paramMap]
     * @return: com.zckj.micservice.bean.common.ReturnJson
     * @Author: lijunqi
     * @Date: 2019/4/18 14:38
     */
    @ApiOperation("多文件上传")
    @PostMapping(value = "upload")
    @ResponseBody
    public APIResponse uploadFiles(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @ApiParam(name = "file", value = "文件数组", required = true)
                                   @RequestParam(name = "file", required = true)
                                   MultipartFile[] files) {
        try {
            //判断files数组不能为空并且长度大于0
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    if (file.isEmpty()) {
                        throw BusinessException.withErrorCode(ErrorConstant.File.UPLOAD_FILE_FAIL);
                    }
                    Map<String,Object> paramMap = new HashMap<>(9);
                    String sourceName = file.getOriginalFilename();
                    String fileSuffix = sourceName.substring(sourceName.lastIndexOf(".") + 1, sourceName.length());
                    //上传文件到fastDFS服务器
                    FastDFSUtil client = FastDFSUtil.getFastProperties();
                    FastDFSFile fsfile = new FastDFSFile(file.getBytes(), fileSuffix);
                    String savePath = client.uploadFile(fsfile.getContent(), fileSuffix);
                    paramMap.put("sourceName", sourceName);
                    paramMap.put("fileSuffix", fileSuffix);
                    paramMap.put("reName", savePath.substring(savePath.lastIndexOf("/") + 1));
                    paramMap.put("fileUrl", client.getIpPort() + savePath);
                    pubFileService.uploadSingleFile(paramMap);
                }
                return APIResponse.success();
            } else {
                throw BusinessException.withErrorCode(ErrorConstant.File.FILE_ARR_ISNULL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(ErrorConstant.File.UPLOAD_FILE_FAIL)
                    .withErrorMessageArguments(e.getMessage());
        }
    }


    /**
     * @Des: 删除文件
     * @Param: [id]
     * @return: com.zckj.micservice.bean.common.ReturnJson
     * @Author: lijunqi
     * @Date: 2019/4/17 18:40
     */
    @ApiOperation("删除文件记录")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public APIResponse deleteFile(@ApiParam(name = "lid", value = "文件主键", required = true)
                                 @RequestParam(name = "lid", required = true)
                                 int lid,
                                  HttpServletRequest request
    ) {
        return this.pubFileService.deleteFile(lid);
    }

}
