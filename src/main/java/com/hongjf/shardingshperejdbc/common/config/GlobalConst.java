package com.hongjf.shardingshperejdbc.common.config;

import cn.hutool.core.date.DatePattern;

import java.time.format.DateTimeFormatter;

/**
 * @ClassName GlobalConst
 * @Author hongjf
 * @Date 2021/4/6 下午2:04
 * @Version 1.0
 */
public class GlobalConst {

    public final static DateTimeFormatter YYYY_MM = DateTimeFormatter.ofPattern("yyyyMM");

    public final static DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);

}
