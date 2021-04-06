# ShardingSphereJSBC

### 简介
* [官网](http://shardingsphere.apache.org)
* [Git](https://github.com/apache/shardingsphere)
* [ShardingSphere-example](https://github.com/apache/shardingsphere-example)

ShardingSphere于2020年4月16日成为Apache顶级基金项目,现有三部分，ShardingSphere-JDBC、ShardingSphere-Proxy、ShardingSphere-Sidecar。

### 引包
    <dependency>
        <groupId>org.apache.shardingsphere</groupId>
        <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
        <version>5.0.0-alpha</version>
    </dependency>

### 可能发生的bug
#### java.sql.SQLFeatureNotSupportedException: getObject with type
* [bug链接](https://blog.csdn.net/weixin_43356458/article/details/108101896)
  在ShardingSphere-JDBC5.0之前的版本中，sql语句select查询中包含时间字段时会发生此错误。原因是ShardingSphere-JDBC的时间类型handel有问题，需要重写handel
  
#### 使用时间范围分片算法，无上下限引发的异常
  使用自定义的时间范围算法时，没有上限时间和下限时间的话，会引发时间范围的一个报错。解决的方案是，在无上下限时间的查询条件时，默认加上上下限时间，或者在5.0版本后，可以使用ShardingSphere自带的时间范围分片算法。

#### 使用时间范围分片算法时，查询条件中，分片字段格式异常
  在使用ShardingSphere-JDBC5.0的自带的时间范围分片算法时，在查询语句中使用到分片字段条件，则分片字段的条件参数格式需和定义分片算法时的分片字段格式一致，否则会引发String格式的的数组异常。

#### 不支持的sql
* [不支持的sql](https://shardingsphere.apache.org/document/current/cn/features/sharding/use-norms/sql/#%E4%B8%8D%E6%94%AF%E6%8C%81%E7%9A%84sql)
  ShardingSphere-JDBC有很多不支持的sql，要解决这个问题就需要引入多数据源

#### 配置规则
* [配置规则](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/configuration/spring-boot-starter/sharding/)
* [内置算法](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/configuration/built-in-algorithm/sharding/)

#### 自定义算法规则
###### 自定义算法类
    新建类实现StandardShardingAlgorithm接口并给定分片字段的类型，重写接口的抽象方法
###### 配置文件算法配置
        calssBased: 算法名称
            type: CLASS_BASED #算法类型-自定义算法
            props: #算法规则
              strategy: STANDARD 策略-标准策略
              algorithmClassName: com.hongjf.shardingshperejdbc.common.config.TableShardingAlgorithm 分类算法类全路径名称
###### 新建spi文件
    在resource下新建META-INF包，在META-INF包里新建services包，在services包里新建org.apache.shardingsphere.sharding.spi.ShardingAlgorithm文件，在文件里写上自定义算法类的全路径类名称
