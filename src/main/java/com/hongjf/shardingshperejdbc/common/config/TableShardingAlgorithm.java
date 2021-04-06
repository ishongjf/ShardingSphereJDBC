package com.hongjf.shardingshperejdbc.common.config;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * 自定义分片算法
 *
 * @ClassName TableShardingAlgorithm
 * @Author hongjf
 * @Date 2021/4/6 下午2:01
 * @Version 1.0
 */
@Slf4j
public class TableShardingAlgorithm implements StandardShardingAlgorithm<LocalDateTime> {

    private static final String TYPE = "CLASS_BASED";

    private static Properties properties = new Properties();

    private final static Integer MIN = 202012;

    private final static Integer MAX = 202312;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<LocalDateTime> preciseShardingValue) {
        // 基本的表名_年份月份  base_199001
        String tableName = null;
        if (preciseShardingValue.getValue() instanceof LocalDateTime) {
            tableName = preciseShardingValue.getValue().format(GlobalConst.YYYY_MM);
        } else {
            Object value = preciseShardingValue.getValue();
            LocalDate time = LocalDate.parse((String) value , GlobalConst.YYYY_MM_DD_HH_MM_SS);
            tableName = time.format(GlobalConst.YYYY_MM);
        }
        String targetTable = preciseShardingValue.getLogicTableName() + "_" + tableName;
        if (collection.contains(targetTable)) {
            return targetTable;
        }
        throw new UnsupportedOperationException("无效的表名称: " + targetTable);
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<LocalDateTime> rangeShardingValue) {
        Collection<String> collect = new ArrayList<>();
        Range<LocalDateTime> valueRange = rangeShardingValue.getValueRange();
        //这种写法只支持between, 但是效率很高
        Integer between, and;
        Object lowerEndpoint, upperEndpoint;
        try {
            lowerEndpoint = valueRange.lowerEndpoint();
            if (lowerEndpoint != null && lowerEndpoint instanceof LocalDateTime) {
                between = Integer.valueOf(valueRange.lowerEndpoint().format(GlobalConst.YYYY_MM));
            } else {
                String dateStr = (String) lowerEndpoint;
                LocalDateTime dateTime = LocalDateTime.parse(dateStr, GlobalConst.YYYY_MM_DD);
                between = Integer.valueOf(dateTime.format(GlobalConst.YYYY_MM));
            }
            if (between < MIN) {
                between = MIN;
            }
        } catch (Exception e) {
            between = MIN;
        }
        try {
            upperEndpoint = valueRange.upperEndpoint();
            if (upperEndpoint != null && upperEndpoint instanceof LocalDateTime) {
                and = Integer.valueOf(valueRange.upperEndpoint().format(GlobalConst.YYYY_MM));
            } else {
                String dateStr = (String) upperEndpoint;
                LocalDateTime dateTime = LocalDateTime.parse(dateStr, GlobalConst.YYYY_MM_DD);
                and = Integer.valueOf(dateTime.format(GlobalConst.YYYY_MM));
            }
            if (and > MAX) {
                and = MAX;
            }
        } catch (Exception e) {
            and = MAX;
        }
        for (String each : collection) {
            if (checkValue(between, and, each, rangeShardingValue.getLogicTableName())) {
                collect.add(each);
            }
        }
        return collect;
    }

    @Override
    public void init() {
        properties.put("strategy", "STANDARD");
        properties.put("algorithmClassName", "com.linkcld.rptanalysis.common.config.TrafficVehicleInfoTableShardingAlgorithm");
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Properties getProps() {
        return properties;
    }

    @Override
    public void setProps(Properties props) {
        properties = props;
    }

    private Boolean checkValue(Integer start, Integer end, String each, String tableName) {
        String table = each.substring(0, each.lastIndexOf("_"));
        if (tableName.equals(table)) {
            Integer value = Integer.valueOf(each.substring(each.lastIndexOf("_") + 1));
            if (start <= value && end >= value) {
                return true;
            }
        }
        return false;
    }
}
