package com.hongjf.shardingshperejdbc.test.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程表entity
 *
 * @ClassName CourseEntity
 * @Author hongjf
 * @Date 2021/3/29 下午4:10
 * @Version 1.0
 */
@Data
@TableName("course")
public class CourseEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
