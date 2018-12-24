package com.work.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.work.domain.City;
import com.work.domain.User;
import com.work.service.Userservice;

/**
 * 
 */
//@RestController
//@RequestMapping("user/")
public class UserController {
	//建议在重要方法上都打上日志
	protected static Logger logger=LoggerFactory.getLogger(UserController.class);  
	
    @Autowired
    private Userservice userService;

    /**
     * @GetMapping("/") 该注解相当于@RequestMapping (method = RequestMethod.GET)
     * @PostMapping("/")该注解相当于@RequestMapping (method = RequestMethod.POST)
     * @param id
     * @return
     */
    @RequestMapping(value="query/{id}",method = RequestMethod.GET)
    public User queryById(@PathVariable("id") int id){
        User user = this.userService.queryById(id);
        City city = userService.querycityById(id);
        if(null != user && null != city){
            user.setCity(city);
        }
        return user;
    }
    
    @RequestMapping(value="querycity",method = RequestMethod.GET)
    public List<City> queryLikeName(String name,int currentPage,int pageSize){
    	logger.info("访问UserController");  
    	List<City> city = this.userService.queryLikeName(name,currentPage,pageSize);
        return city;
    }
}
