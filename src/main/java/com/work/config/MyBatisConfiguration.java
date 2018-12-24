package com.work.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.pagehelper.PageHelper;
import com.work.config.datasource.DynamicDataSource;
import com.work.config.datasource.DynamicDataSourceContextHolder;
/**
 * 配置 mybatis分页的bean
 * @author ljj
 *
 */
//@Configuration
//@MapperScan(basePackages = MyBatisConfiguration.PACKAGE)
public class MyBatisConfiguration {
	
	//基础包
	public static final String PACKAGE = "com.work.dao";
	//xml文件路径
    public static final String MAPPER_LOCATION = "classpath:mapper/*/*.xml";
    //读取配置信息 环境
    @Autowired
    private Environment env;
	
	//@Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        
        /**
         * ## 设置的参数属性
         * 1. 增加dialect属性，使用时必须指定该属性，
         * 可选值为oracle,mysql,mariadb,sqlite,hsqldb,postgresql,
         * 没有默认值，必须指定该属性。

		   2. 增加offsetAsPageNum属性，默认值为false，
		       使用默认值时不需要增加该配置，需要设为true时，需要配置该参数。
		       当该参数设置为true时，使用RowBounds分页时，会将offset参数当成pageNum使用，可以用页码和页面大小两个参数进行分页。

		   3. 增加rowBoundsWithCount属性，默认值为false，
		       使用默认值时不需要增加该配置，需要设为true时，需要配置该参数。
		       当该参数设置为true时，使用RowBounds分页会进行count查询。

		   4. 增加pageSizeZero属性，默认值为false，
		      使用默认值时不需要增加该配置，需要设为true时，需要配置该参数。
		      当该参数设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是Page类型）。

		   5. 增加reasonable属性，默认值为false，
		       使用默认值时不需要增加该配置，需要设为true时，需要配置该参数。
		       具体作用请看上面配置文件中的注释内容。
		       
		  6.autoDialect：true or false，是否自动检测dialect。

		  7.autoRuntimeDialect：true or false，多数据源时，是否自动检测dialect。

		  8.closeConn：true or false，检测完dialect后，是否关闭Connection连接。
         * 
         */
        
        //RowBounds参数offset作为PageNum使用 - 默认不使用
        p.setProperty("offsetAsPageNum", "true");
        //RowBounds是否进行count查询 - 默认不查询
        p.setProperty("rowBoundsWithCount", "true");
        //分页合理化
        p.setProperty("reasonable", "true");
        p.setProperty("autoRuntimeDialect ", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
	
	/*
     * 数据源添加
     * 新增数据源需要添加一个bean
     */
    
    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     * master DataSource
     * @Primary 注解用于标识默认使用的 DataSource Bean，因为有三个 DataSource Bean，该注解可用于 master
     * 或 slave DataSource Bean, 但不能用于 dynamicDataSource Bean, 否则会产生循环调用 
     *
     *在@Bean中可以添加initMethod和destroyMethod属性
     *initMethod 在构造方法执行后执行
     *destroyMethod 在bean销毁之前时候执行
     *
     *
     *spring boot允许你通过命名约定按照一定的格式(application-{profile}.properties)来定义多个配置文件，
     *然后通过在application.properyies通过spring.profiles.active来具体激活一个
     *或者多个配置文件，如果没有没有指定任何profile的配置文件的话，
     *spring boot默认会启动application-default.properties。
     *
     *@Profile("testdb") 作用同上 在application.properties进行配置激活状态。
     */
   // @Bean
	//@Primary
    public DataSource masterDataSource() throws Exception {
        Properties props = new Properties();
        props.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, env.getProperty("master.datasource.driver-class-name"));
        props.put(DruidDataSourceFactory.PROP_URL, env.getProperty("master.datasource.url"));
        props.put(DruidDataSourceFactory.PROP_USERNAME, env.getProperty("master.datasource.username"));
        props.put(DruidDataSourceFactory.PROP_PASSWORD, env.getProperty("master.datasource.password"));
        //druid的配置信息
        props.put(DruidDataSourceFactory.PROP_INITIALSIZE, env.getProperty("master.datasource.initialSize"));
        props.put(DruidDataSourceFactory.PROP_MINIDLE, env.getProperty("master.datasource.minIdle"));
        props.put(DruidDataSourceFactory.PROP_MAXACTIVE, env.getProperty("master.datasource.maxActive"));
        props.put(DruidDataSourceFactory.PROP_MAXWAIT, env.getProperty("master.datasource.maxWait"));
        props.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, env.getProperty("master.datasource.timeBetweenEvictionRunsMillis"));
        props.put(DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS, env.getProperty("master.datasource.minEvictableIdleTimeMillis"));
        props.put(DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS, env.getProperty("master.datasource.poolPreparedStatements"));
        props.put(DruidDataSourceFactory.PROP_MAXOPENPREPAREDSTATEMENTS, env.getProperty("master.datasource.maxPoolPreparedStatementPerConnectionSize"));
        props.put(DruidDataSourceFactory.PROP_FILTERS, env.getProperty("master.datasource.filters"));
        props.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES, env.getProperty("master.datasource.connectionProperties"));
        props.put("useGlobalDataSourceStat", env.getProperty("master.datasource.useGlobalDataSourceStat"));
        
        
        return DruidDataSourceFactory.createDataSource(props);
    }

  //  @Bean
    public DataSource clusterDataSource() throws Exception {
    	Properties props = new Properties();
        props.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, env.getProperty("cluster.datasource.driver-class-name"));
        props.put(DruidDataSourceFactory.PROP_URL, env.getProperty("cluster.datasource.url"));
        props.put(DruidDataSourceFactory.PROP_USERNAME, env.getProperty("cluster.datasource.username"));
        props.put(DruidDataSourceFactory.PROP_PASSWORD, env.getProperty("cluster.datasource.password"));
        //druid的配置信息
        props.put(DruidDataSourceFactory.PROP_INITIALSIZE, env.getProperty("cluster.datasource.initialSize"));
        props.put(DruidDataSourceFactory.PROP_MINIDLE, env.getProperty("cluster.datasource.minIdle"));
        props.put(DruidDataSourceFactory.PROP_MAXACTIVE, env.getProperty("cluster.datasource.maxActive"));
        props.put(DruidDataSourceFactory.PROP_MAXWAIT, env.getProperty("cluster.datasource.maxWait"));
        props.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, env.getProperty("cluster.datasource.timeBetweenEvictionRunsMillis"));
        props.put(DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS, env.getProperty("cluster.datasource.minEvictableIdleTimeMillis"));
        props.put(DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS, env.getProperty("cluster.datasource.poolPreparedStatements"));
        props.put(DruidDataSourceFactory.PROP_MAXOPENPREPAREDSTATEMENTS, env.getProperty("cluster.datasource.maxPoolPreparedStatementPerConnectionSize"));
        props.put(DruidDataSourceFactory.PROP_FILTERS, env.getProperty("cluster.datasource.filters"));
        props.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES, env.getProperty("cluster.datasource.connectionProperties"));
        props.put("useGlobalDataSourceStat", env.getProperty("cluster.datasource.useGlobalDataSourceStat"));
        
        
        return DruidDataSourceFactory.createDataSource(props);
    }
	
	// @Bean
	 public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
	              @Qualifier("clusterDataSource") DataSource clusterDataSource) {
		 //目标数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource);
        targetDataSources.put("cluster", clusterDataSource);
        //新增数据源需要添加
  
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(masterDataSource);// 默认的datasource设置为myTestDbDataSource
  
        //将DataSource的名称添加进去，不添加进去是获取不到DataSource的
        for (Object key : targetDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add((String)key);
        }
        return dataSource;
      }
	
	 /**
	     * 根据数据源创建SqlSessionFactory
	     */
  //  @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(ds);
        //扫描 mapper的路径
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources(MyBatisConfiguration.MAPPER_LOCATION);
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        //增加分页
        Interceptor[] plugins =  new Interceptor[]{new PageHelper()};
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置事务管理器
     * 动态的设置事务
     */
 //   @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
    
    
    
}
