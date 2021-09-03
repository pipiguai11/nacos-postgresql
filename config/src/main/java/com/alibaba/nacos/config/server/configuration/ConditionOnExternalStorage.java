/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.config.server.configuration;

import com.alibaba.nacos.config.server.constant.PropertiesConstant;
import com.alibaba.nacos.config.server.utils.PropertyUtil;
import com.alibaba.nacos.sys.env.EnvUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Judge whether to user ExternalStorage by condition.
 * 默认认为这个扩展的外部存储库就是mysql，针对这个做一个扩展，就是ConditionOnPostgresStorage
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 */
public class ConditionOnExternalStorage implements Condition {
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !PropertyUtil.isEmbeddedStorage() && PropertiesConstant.MYSQL
                .equalsIgnoreCase(EnvUtil.getProperty(PropertiesConstant.SPRING_DATASOURCE_PLATFORM));
    }
    
}
