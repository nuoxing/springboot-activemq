package com.work.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.work.domain.City;


@Mapper
public interface ICityDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityId 城市名
     */
    public City queryById(@Param("cityId") int cityId);

	public List<City> queryLikeName(@Param("name")String name);
}
