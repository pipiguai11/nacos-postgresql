package com.alibaba.nacos.config.server.configuration;

import com.alibaba.nacos.config.server.constant.PropertiesConstant;
import com.alibaba.nacos.config.server.utils.PropertyUtil;
import com.alibaba.nacos.sys.env.EnvUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author ：linhw
 * @date ：21.7.14 17:21
 * @description：外部存储库使用的是postgreSQL
 * @modified By：
 */
public class ConditionOnPostgresStorage implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !PropertyUtil.isEmbeddedStorage() && PropertiesConstant.POSTGRES
                .equalsIgnoreCase(EnvUtil.getProperty(PropertiesConstant.SPRING_DATASOURCE_PLATFORM));
    }

}
