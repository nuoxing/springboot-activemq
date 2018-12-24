package com.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.work.domain.User;


@Mapper
public interface IUserDao {

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    public User queryByUserId(@Param("userId") int userId);
}
