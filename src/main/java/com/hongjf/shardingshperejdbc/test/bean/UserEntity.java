package com.hongjf.shardingshperejdbc.test.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表entity
 *
 * @ClassName UserEntity
 * @Author hongjf
 * @Date 2021/3/29 下午4:08
 * @Version 1.0
 */
@Data
@TableName("user")
public class UserEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;
}
