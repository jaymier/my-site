package com.jaymie.service.file.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaymie.constant.ErrorConstant;
import com.jaymie.dao.TPubFileDao;
import com.jaymie.exception.BusinessException;
import com.jaymie.model.TPubFile;
import com.jaymie.service.file.ITPubFileService;
import com.jaymie.utils.APIResponse;
import com.jaymie.utils.FastDFSUtil;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 附件信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2019-04-17
 */
@Service
public class TPubFileServiceImpl extends ServiceImpl<TPubFileDao, TPubFile> implements ITPubFileService {

    private final Logger logger = LoggerFactory.getLogger(TPubFileServiceImpl.class);

    @Autowired
    private TPubFileDao pubFileDao;

    /**
     * @param paramMap
     * @Des: 单文件上传
     * @Param: [file, tPubFile]
     * @return: com.zckj.micservice.bean.common.ReturnJson
     * @Author: lijunqi
     * @Date: 2019/4/17 16:49
     */
    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public void uploadSingleFile(Map<String, Object> paramMap) {
            this.saveFileInfoSql(paramMap);
    }


    /**
     * @param lid
     * @Des: 删除文件
     * @Param: [id]
     * @return: com.zckj.micservice.bean.common.ReturnJson
     * @Author: lijunqi
     * @Date: 2019/4/17 18:41
     */
    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public APIResponse deleteFile(int lid) {
        //根据id查询文件的保存路径
        TPubFile tPubFile = new TPubFile();
        tPubFile.setLid(lid);
        List<Map<String, Object>> fileList = this.pubFileDao.selectFile(tPubFile);
        if (null == fileList) {
            throw BusinessException.withErrorCode(ErrorConstant.Att.DELETE_ATT_FAIL +  ": 文件不存在");
        }
        String fileName = ObjectUtils.defaultIfNull(fileList.get(0).get("source_name"), "").toString();
        try {
            //判断文件路径是否为空
            String path = ObjectUtils.defaultIfNull(fileList.get(0).get("file_url"), "").toString();
            if (null == path || "".equals(path)) {
                throw BusinessException.withErrorCode(ErrorConstant.File.DELETE_FILE_FAIL);
            }
            FastDFSUtil client = FastDFSUtil.getFastProperties();
            int isDeleted = client.delete_file(path.substring(path.indexOf("group")));
            if (isDeleted == 0) {
                if (!this.pubFileDao.deleteFileById(tPubFile)){
                    throw BusinessException.withErrorCode(ErrorConstant.File.DELETE_FILE_FAIL);
                }
            } else {
                throw BusinessException.withErrorCode(ErrorConstant.File.DELETE_FILE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除文件失败");
            throw BusinessException.withErrorCode(ErrorConstant.File.DELETE_FILE_FAIL);
        }
        return APIResponse.success();
    }



    private void saveFileInfoSql(Map<String, Object> paramMap) {
        //进行存表
        TPubFile tPubFile = new TPubFile();
        tPubFile.setId(UUID.randomUUID().toString());
        if (null != paramMap.get("flag") && !"".equals("flag")) {
            tPubFile.setFlag(paramMap.get("flag").toString());
        }
        if (null != paramMap.get("temp1") && !"".equals("temp1")) {
            tPubFile.setTemp1(paramMap.get("temp1").toString());
        }
        if (null != paramMap.get("temp2") && !"".equals("temp2")) {
            tPubFile.setTemp2(paramMap.get("temp2").toString());
        }
        if (null != paramMap.get("temp3") && !"".equals("temp3")) {
            tPubFile.setTemp3(paramMap.get("temp3").toString());
        }
        if (null != paramMap.get("temp4") && !"".equals("temp4")) {
            tPubFile.setTemp4(paramMap.get("temp4").toString());
        }
        tPubFile.setSourceName(paramMap.get("sourceName").toString());
        tPubFile.setReName(paramMap.get("reName").toString());
        tPubFile.setFileUrl(paramMap.get("fileUrl").toString());
        tPubFile.setFileSuffix(paramMap.get("fileSuffix").toString());
        tPubFile.setEventTime(String.valueOf(System.currentTimeMillis()));

        //根据文件后缀判断文件类型
        String strFileSuffix = "jpg,gif,png,bmp,jpeg,psd,tiff,tga,eps";
        tPubFile.setFileType("1");
        if (strFileSuffix.indexOf(tPubFile.getFileSuffix().toLowerCase()) != -1) {
            //此文件为图片
            tPubFile.setFileType("0");
        }
        this.pubFileDao.insertPubFile(tPubFile);
    }


    /**
     * @param path
     * @return
     * @Author ysy
     * @Description 获得文件byte
     * @Param
     * @Date 2019/4/18 14:42
     */
    @Override
    public byte[] getFile(String path) {
        byte[] bytes = null;
        try {
            String group = path.substring(path.indexOf("group"));
            FastDFSUtil client = FastDFSUtil.getFastProperties();
            bytes = client.download_bytes(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * @param tPubFile
     * @Des: 查看文件信息
     * @Param: [lid]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: lijunqi
     * @Date: 2019/4/19 11:31
     */
    @Override
    public List<Map<String, Object>> selectPicByLid(TPubFile tPubFile) {
        return this.pubFileDao.selectFile(tPubFile);
    }


    /**
     * @Description : 查看全部文件
     * @author : jiangyuzhen
     * @date : 2019/12/20 15:15
     * @param pageNum :
     * @param pageSize :
     * @return : com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    @Cacheable(value = "attCaches", key = "'atts' + #p0")
    public PageInfo<Map<String, Object>> getfiles(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> atts = pubFileDao.getFiles();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(atts);
        return pageInfo;
    }

}
