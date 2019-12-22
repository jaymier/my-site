package com.jaymie.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jaymie.model.TPubFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件信息表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-04-17
 */
@Mapper
public interface TPubFileDao extends BaseMapper<TPubFile> {

    /** 
    * @Des: 新增文件信息
    * @Param: [tPubFile] 
    * @return: boolean 
    * @Author: lijunqi
    * @Date: 2019/4/17 17:50
    */ 
    boolean insertPubFile(TPubFile tPubFile);

    /** 
    * @Des: 根据条件查询文件信息
    * @Param: [tPubFile] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: lijunqi
    * @Date: 2019/4/17 19:00
    */ 
    List<Map<String, Object>> selectFile(TPubFile tPubFile);

    /** 
    * @Des: 根据条件删除文件
    * @Param: [tPubFile] 
    * @return: boolean 
    * @Author: lijunqi
    * @Date: 2019/4/17 19:14
    */ 
    boolean deleteFileById(TPubFile tPubFile);


    /**
     * @Description : 获取全部文件信息
     * @author : jiangyuzhen
     * @date : 2019/12/20 15:12
     * @param  :
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getFiles();

    /**
     * @Description : 获取文件数量
     * @author : jiangyuzhen
     * @date : 2019/12/20 15:12
     * @param  :
     * @return : java.lang.Long
     */
    Long getFilesCount();
}
