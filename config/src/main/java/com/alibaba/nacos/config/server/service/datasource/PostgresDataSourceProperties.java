package com.alibaba.nacos.config.server.service.datasource;


/**
 * @author ：linhw
 * @date ：21.7.9 13:51
 * @description：postgres数据源
 * @modified By：
 */
public class PostgresDataSourceProperties extends AbstractExternalDataSourceProperties {

    static {
        JDBC_DRIVER_NAME = "org.postgresql.Driver";
    }

}
