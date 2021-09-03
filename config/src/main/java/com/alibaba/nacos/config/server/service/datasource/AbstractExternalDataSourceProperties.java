package com.alibaba.nacos.config.server.service.datasource;

import com.google.common.base.Preconditions;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.alibaba.nacos.common.utils.CollectionUtils.getOrDefault;

/**
 * @author ：linhw
 * @date ：21.7.9 13:56
 * @description：抽象扩展数据源配置类
 * @modified By：
 */
public abstract class AbstractExternalDataSourceProperties {

    protected static String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String TEST_QUERY = "SELECT 1";

    private Integer num;

    private List<String> url = new ArrayList<>();

    private List<String> user = new ArrayList<>();

    private List<String> password = new ArrayList<>();

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    /**
     * Build serveral HikariDataSource.
     *
     * @param environment {@link Environment}
     * @param callback    Callback function when constructing data source
     * @return List of {@link HikariDataSource}
     */
    List<HikariDataSource> build(Environment environment, ExternalDataSourceProperties.Callback<HikariDataSource> callback) {
        List<HikariDataSource> dataSources = new ArrayList<>();
        //通过Binder和Bindable这个给本对象中的属性赋值，会找到配置文件中对应的db开头的配置
        Binder.get(environment).bind("db", Bindable.ofInstance(this));
        //检查参数是否正常赋值，如果没有配置，则会报错，也就是说如果开启了使用外部存储库的配置，那如下这三个信息就必须配置
        Preconditions.checkArgument(Objects.nonNull(num), "db.num is null");
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(user), "db.user or db.user.[index] is null");
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(password), "db.password or db.password.[index] is null");
        //因为num表示有多少个数据源，所以这里就遍历num遍
        for (int index = 0; index < num; index++) {
            int currentSize = index + 1;
            //进来之后再检查一遍url是否正常配置了
            Preconditions.checkArgument(url.size() >= currentSize, "db.url.%s is null", index);
            DataSourcePoolProperties poolProperties = DataSourcePoolProperties.build(environment);
            //设置数据库驱动，这里默认是mysql的，如果需要适配别的数据库则这里需要修改（其实这里修改的就是HikariDataSource对象的配置参数）
            poolProperties.setDriverClassName(JDBC_DRIVER_NAME);
            //设置jdbc的url
            poolProperties.setJdbcUrl(url.get(index).trim());
            //设置账号密码
            poolProperties.setUsername(getOrDefault(user, index, user.get(0)).trim());
            poolProperties.setPassword(getOrDefault(password, index, password.get(0)).trim());
            //设置springboot默认数据源的参数配置
            HikariDataSource ds = poolProperties.getDataSource();
            ds.setConnectionTestQuery(TEST_QUERY);
            ds.setIdleTimeout(TimeUnit.MINUTES.toMillis(10L));
            ds.setConnectionTimeout(TimeUnit.SECONDS.toMillis(3L));
            dataSources.add(ds);
            /** callback.accept会调用以下逻辑（主要就是设置jdbcTemplate和健康监测的状态）
             * JdbcTemplate jdbcTemplate = new JdbcTemplate();
             * jdbcTemplate.setQueryTimeout(queryTimeout);
             * jdbcTemplate.setDataSource(dataSource);
             * testJtList.add(jdbcTemplate);
             * isHealthList.add(Boolean.TRUE);
             */
            callback.accept(ds);
        }
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dataSources), "no datasource available");
        return dataSources;
    }

    // 函数式接口
    interface Callback<D> {

        /**
         * Perform custom logic.
         *
         * @param datasource dataSource.
         */
        void accept(D datasource);
    }

}
