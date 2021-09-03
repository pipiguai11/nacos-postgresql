package com.alibaba.nacos.config.server.auth;

import com.alibaba.nacos.config.server.configuration.ConditionOnPostgresStorage;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：linhw
 * @date ：21.7.15 09:26
 * @description：扩展PG的角色校验服务
 * @modified By：
 */
@Conditional(ConditionOnPostgresStorage.class)
@Component
public class ExternalPostgresRolePersistServiceImpl extends ExternalRolePersistServiceImpl implements RolePersistService {

    @Override
    public List<String> findRolesLikeRoleName(String role) {
        String sql = "SELECT role FROM roles WHERE role like ";
        String querySql = sql + "'%" +  role +  "%'";
        List<String> users = this.jt.queryForList(querySql, String.class);
        return users;
    }

}
