package com.jaymie.dao;

import com.jaymie.dto.cond.CommentCond;
import com.jaymie.model.CommentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:26
 * @description : 评论实体类
 */
@Mapper
public interface CommentDao {

    /**
     * 新增评论
     * @param commentDomain
     * @return
     */
    int addComment(CommentDomain commentDomain);

    /**
     * 删除评论
     * @param coid
     * @return
     */
    int deleteComment(@Param("coid") Integer coid);

    /**
     * 更新评论的状态
     * @param coid
     * @return
     */
    int updateCommentStatus(@Param("coid") Integer coid, @Param("status") String status);

    /**
     * 获取单条评论
     * @param coid
     * @return
     */
    CommentDomain getCommentById(@Param("coid") Integer coid);
    /**
     * 根据文章编号获取评论列表
     * @param cid
     * @return
     */
    List<CommentDomain> getCommentsByCId(@Param("cid") Integer cid);

    /**
     * 根据条件获取评论列表
     * @param commentCond
     * @return
     */
    List<CommentDomain> getCommentsByCond(CommentCond commentCond);

    /**
     * 获取文章数量
     * @return
     */
    Long getCommentsCount();
}
