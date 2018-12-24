package com.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.work.config.datasource.TargetDataSource;
import com.work.dao.ICityDao;
import com.work.dao.IUserDao;
import com.work.domain.City;
import com.work.domain.User;
import com.work.util.PageBean;

//@Service
public class Userservice {
	@Autowired
    private IUserDao userDao; // user数据源
    @Autowired
    private ICityDao cityDao; //city数据源

    /**
     * 根据用户id获取用户信息，包括从库的地址信息
     * @Transactional
     * isolation 隔离级别
     *  DEFAULT ：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是： READ_COMMITTED 。
		READ_UNCOMMITTED ：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
		READ_COMMITTED ：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
		REPEATABLE_READ ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
		SERIALIZABLE ：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
     * 
     * propagation  传播行为
     *  REQUIRED ：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
		SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
		MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
		REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
		NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
		NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。
		NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED 。
     * 
     * 
     * @Transactional(rollbackFor=Exception.class)  事务回滚，在某个异常下
     * @Transactional(norollbackFor=Exception.class) 事务不回滚，在某个异常下
     * 
     * @param id
     * @return
     */
    @TargetDataSource(name="cluster")
    public User queryById(int id) {
        User user = this.userDao.queryByUserId(id);
        return user;
    }
    
    @TargetDataSource(name="master")
    public City querycityById(int id) {
        City city = this.cityDao.queryById(id);
        return city;
    }
    
    @TargetDataSource(name="master")
	public List<City> queryLikeName(String name,int currentPage,int pageSize) {
		/*
		 * 第一个参数：第几页;
		 * 第二个参数：每页获取的条数.
		 */
    	PageHelper.startPage(currentPage, pageSize);
		List<City> city = cityDao.queryLikeName(name);
		int countNums = city.size();            //总记录数
		PageBean<City> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(city);
		return pageData.getItems();
	}
}
