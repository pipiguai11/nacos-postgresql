package com.alibaba.nacos.config.server.auth;

import com.alibaba.nacos.config.server.configuration.ConditionOnPostgresStorage;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @author ：linhw
 * @date ：21.7.15 09:23
 * @description：扩展pg的权限校验服务
 * @modified By：
 */
@Conditional(ConditionOnPostgresStorage.class)
@Component
public class ExternalPostgresPermissionPersistServiceImpl extends ExternalPermissionPersistServiceImpl implements PermissionPersistService {
}
