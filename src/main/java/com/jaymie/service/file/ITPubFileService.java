package com.jaymie.service.file;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.jaymie.model.TPubFile;
import com.jaymie.utils.APIResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件信息表 服务类
 * </p>
 *
 * @author 
 * @since 2019-04-17
 */
public interface ITPubFileService extends IService<TPubFile> {

    /** 
    * @Des: 单文件上传
    * @Param: [file, tPubFile]
    * @return: com.zckj.micservice.bean.common.ReturnJson
    * @Author: lijunqi
    * @Date: 2019/4/17 16:59
    */ 
    void uploadSingleFile(Map<String, Object> paramMap);

    /** 
    * @Des: 删除文件
    * @Param: [id]
    * @return: com.zckj.micservice.bean.common.ReturnJson 
    * @Author: lijunqi
    * @Date: 2019/4/17 18:48
    */
    APIResponse deleteFile(int lid);

    /**
    *
    * @Author  ysy
    * @Description 获得文件byte
    * @Param
    * @return
    * @Date 2019/4/18 14:42
    */
    byte[] getFile(String path);
    
    /** 
    * @Des: 查看文件信息
    * @Param: [tPubFile]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: lijunqi
    * @Date: 2019/4/19 11:33
    */ 
    List<Map<String, Object>> selectPicByLid(TPubFile tPubFile);


    /**
     * @Description : 获取全部文件
     * @author : jiangyuzhen
     * @date : 2019/12/20 15:14
     * @param pageNum :
     * @param pageSize :
     * @return : com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>
     */
    PageInfo<Map<String, Object>> getfiles(int pageNum, int pageSize);
}
