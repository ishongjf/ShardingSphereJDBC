package com.hongjf.shardingshperejdbc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author hongjf
 */
@MapperScan(basePackages = "com.hongjf.shardingshperejdbc.**.mapper")
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
public class ShardingShpereJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingShpereJdbcApplication.class, args);
    }

}
