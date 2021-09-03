package com.alibaba.nacos.config.server.auth;

import com.alibaba.nacos.config.server.configuration.ConditionOnPostgresStorage;
import com.alibaba.nacos.config.server.utils.LogUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：linhw
 * @date ：21.7.15 09:28
 * @description：扩展PG的用户校验服务
 * @modified By：
 */
@Conditional(ConditionOnPostgresStorage.class)
@Component
public class ExternalPostgresUserPersistServiceImpl extends ExternalUserPersistServiceImpl implements UserPersistService {

    @Override
    public void createUser(String username, String password) {
        String sql = "INSERT into users (username, password, enabled) VALUES (?, ?, ?)";

        try {
            jt.update(sql, username, password, 1);
        } catch (CannotGetJdbcConnectionException e) {
            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
            throw e;
        }
    }

    @Override
    public List<String> findUserLikeUsername(String username) {
        String sql = "SELECT username FROM users WHERE username like ";
        String querySql = sql + "'%" + username + "%'";
        List<String> users = this.jt.queryForList(querySql,String.class);
//        List<String> users = this.jt.queryForList(sql, new String[]{username}, String.class);
        return users;
    }

}
