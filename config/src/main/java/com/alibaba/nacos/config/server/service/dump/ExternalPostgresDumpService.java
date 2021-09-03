package com.alibaba.nacos.config.server.service.dump;

import com.alibaba.nacos.config.server.configuration.ConditionOnPostgresStorage;
import com.alibaba.nacos.config.server.service.repository.PersistService;
import com.alibaba.nacos.core.cluster.ServerMemberManager;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ：linhw
 * @date ：21.7.15 09:33
 * @description：
 * @modified By：
 */
@Conditional(ConditionOnPostgresStorage.class)
@Component
@DependsOn({"rpcConfigChangeNotifier"})
public class ExternalPostgresDumpService extends DumpService {
    /**
     * Here you inject the dependent objects constructively, ensuring that some of the dependent functionality is
     * initialized ahead of time.
     *
     * @param persistService {@link PersistService}
     * @param memberManager  {@link ServerMemberManager}
     */
    public ExternalPostgresDumpService(PersistService persistService, ServerMemberManager memberManager) {
        super(persistService, memberManager);
    }

    @PostConstruct
    @Override
    protected void init() throws Throwable {
        dumpOperate(processor, dumpAllProcessor, dumpAllBetaProcessor, dumpAllTagProcessor);
    }

    @Override
    protected boolean canExecute() {
        return memberManager.isFirstIp();
    }
}
