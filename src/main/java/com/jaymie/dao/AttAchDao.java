package com.jaymie.dao;

import com.jaymie.dto.AttAchDto;
import com.jaymie.model.AttAchDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:28
 * @description : TODO
 */
@Mapper
public interface AttAchDao {


    /**
     * 添加单个附件信息
     * @param attAchDomain
     * @return
     */
    int addAttAch(AttAchDomain attAchDomain);

    /**
     * 批量添加附件信息
     * @param list
     * @return
     */
    int batchAddAttAch(List<AttAchDomain> list);

    /**
     * 根据主键编号删除附件信息
     * @param id
     * @return
     */
    int deleteAttAch(int id);

    /**
     * 更新附件信息
     * @param attAchDomain
     * @return
     */
    int updateAttAch(AttAchDomain attAchDomain);

    /**
     * 根据主键获取附件信息
     * @param id
     * @return
     */
    AttAchDto getAttAchById(@Param("id") int id);

    /**
     * 获取所有的附件信息
     * @return
     */
    List<AttAchDto> getAtts();

    /**
     * 查找附件的数量
     * @return
     */
    Long getAttsCount();
}
