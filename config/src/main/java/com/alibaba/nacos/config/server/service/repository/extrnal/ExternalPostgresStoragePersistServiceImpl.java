package com.alibaba.nacos.config.server.service.repository.extrnal;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.alibaba.nacos.config.server.configuration.ConditionOnPostgresStorage;
import com.alibaba.nacos.config.server.constant.Constants;
import com.alibaba.nacos.config.server.model.ConfigInfo;
import com.alibaba.nacos.config.server.service.repository.PersistService;
import com.alibaba.nacos.config.server.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.TransactionCallback;

import java.sql.*;
import java.util.Map;

/**
 * @author ：linhw
 * @date ：21.7.15 09:36
 * @description：扩展PG外部存储持久化服务
 * @modified By：
 */
@Conditional(ConditionOnPostgresStorage.class)
@Component
public class ExternalPostgresStoragePersistServiceImpl extends ExternalStoragePersistServiceImpl implements PersistService {

    private static final String SQL_FIND_CURRENT_SEQ_VALUE = "select currval('%s')";

    ExternalPostgresStoragePersistServiceImpl(){
        System.out.println("======================================");
        System.out.println("PG扩展外部存储持久化服务加载了");
        System.out.println("======================================");
    }

//    @Override
//    public void addConfigInfo4Beta(ConfigInfo configInfo, String betaIps, String srcIp, String srcUser, Timestamp time,
//                                   boolean notify) {
//        String appNameTmp = StringUtils.isBlank(configInfo.getAppName()) ? StringUtils.EMPTY : configInfo.getAppName();
//        String tenantTmp = StringUtils.isBlank(configInfo.getTenant()) ? StringUtils.EMPTY : configInfo.getTenant();
//        String md5 = MD5Utils.md5Hex(configInfo.getContent(), Constants.ENCODE);
//        try {
//            jt.update("INSERT INTO config_info_beta(id,data_id,group_id,tenant_id,app_name,content,md5,beta_ips,src_ip,"
//                            + "src_user,gmt_create,gmt_modified) VALUES(nextval('seq_config_info_beta'),?,?,?,?,?,?,?,?,?,?,?)", configInfo.getDataId(),
//                    configInfo.getGroup(), tenantTmp, appNameTmp, configInfo.getContent(), md5, betaIps, srcIp, srcUser,
//                    time, time);
//        } catch (CannotGetJdbcConnectionException e) {
//            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
//            throw e;
//        }
//    }
//
//    @Override
//    public void addConfigInfo4Tag(ConfigInfo configInfo, String tag, String srcIp, String srcUser, Timestamp time,
//                                  boolean notify) {
//        String appNameTmp = StringUtils.isBlank(configInfo.getAppName()) ? StringUtils.EMPTY : configInfo.getAppName();
//        String tenantTmp = StringUtils.isBlank(configInfo.getTenant()) ? StringUtils.EMPTY : configInfo.getTenant();
//        String tagTmp = StringUtils.isBlank(tag) ? StringUtils.EMPTY : tag.trim();
//        String md5 = MD5Utils.md5Hex(configInfo.getContent(), Constants.ENCODE);
//        try {
//            jt.update(
//                    "INSERT INTO config_info_tag(id,data_id,group_id,tenant_id,tag_id,app_name,content,md5,src_ip,src_user,"
//                            + "gmt_create,gmt_modified) VALUES(nextval('seq_config_info_tag'),?,?,?,?,?,?,?,?,?,?,?)", configInfo.getDataId(),
//                    configInfo.getGroup(), tenantTmp, tagTmp, appNameTmp, configInfo.getContent(), md5, srcIp, srcUser,
//                    time, time);
//        } catch (CannotGetJdbcConnectionException e) {
//            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
//            throw e;
//        }
//    }
//
//    @Override
//    public boolean addAggrConfigInfo(final String dataId, final String group, String tenant, final String datumId,
//                                     String appName, final String content) {
//        String appNameTmp = StringUtils.isBlank(appName) ? StringUtils.EMPTY : appName;
//        String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
//        final Timestamp now = new Timestamp(System.currentTimeMillis());
//        String select = "SELECT content FROM config_info_aggr WHERE data_id = ? AND group_id = ? AND tenant_id = ?  AND datum_id = ?";
//        String insert = "INSERT INTO config_info_aggr(id,data_id, group_id, tenant_id, datum_id, app_name, content, gmt_modified) VALUES(nextval('seq_config_info_aggr'),?,?,?,?,?,?,?) ";
//        String update = "UPDATE config_info_aggr SET content = ? , gmt_modified = ? WHERE data_id = ? AND group_id = ? AND tenant_id = ? AND datum_id = ?";
//
//        try {
//            try {
//                String dbContent = jt
//                        .queryForObject(select, new Object[] {dataId, group, tenantTmp, datumId}, String.class);
//
//                if (dbContent != null && dbContent.equals(content)) {
//                    return true;
//                } else {
//                    return jt.update(update, content, now, dataId, group, tenantTmp, datumId) > 0;
//                }
//            } catch (EmptyResultDataAccessException ex) { // no data, insert
//                return jt.update(insert, dataId, group, tenantTmp, datumId, appNameTmp, content, now) > 0;
//            }
//        } catch (DataAccessException e) {
//            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
//            throw e;
//        }
//    }
//
//    @Override
//    public boolean replaceAggr(final String dataId, final String group, final String tenant,
//                               final Map<String, String> datumMap, final String appName) {
//        try {
//            Boolean isReplaceOk = tjt.execute(new TransactionCallback<Boolean>() {
//                @Override
//                public Boolean doInTransaction(TransactionStatus status) {
//                    try {
//                        String appNameTmp = appName == null ? "" : appName;
//                        removeAggrConfigInfo(dataId, group, tenant);
//                        String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
//                        String sql = "INSERT INTO config_info_aggr(id,data_id, group_id, tenant_id, datum_id, app_name, content, gmt_modified) VALUES(nextval('seq_config_info_aggr'),?,?,?,?,?,?,?) ";
//                        for (Map.Entry<String, String> datumEntry : datumMap.entrySet()) {
//                            jt.update(sql, dataId, group, tenantTmp, datumEntry.getKey(), appNameTmp,
//                                    datumEntry.getValue(), new Timestamp(System.currentTimeMillis()));
//                        }
//                    } catch (Throwable e) {
//                        throw new TransactionSystemException("error in addAggrConfigInfo");
//                    }
//                    return Boolean.TRUE;
//                }
//            });
//            if (isReplaceOk == null) {
//                return false;
//            }
//            return isReplaceOk;
//        } catch (TransactionException e) {
//            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
//            return false;
//        }
//
//    }

    @Override
    public long addConfigInfoAtomic(final long configId, final String srcIp, final String srcUser,
                                    final ConfigInfo configInfo, final Timestamp time, Map<String, Object> configAdvanceInfo) {
        final String appNameTmp =
                StringUtils.isBlank(configInfo.getAppName()) ? StringUtils.EMPTY : configInfo.getAppName();
        final String tenantTmp =
                StringUtils.isBlank(configInfo.getTenant()) ? StringUtils.EMPTY : configInfo.getTenant();

        final String desc = configAdvanceInfo == null ? null : (String) configAdvanceInfo.get("desc");
        final String use = configAdvanceInfo == null ? null : (String) configAdvanceInfo.get("use");
        final String effect = configAdvanceInfo == null ? null : (String) configAdvanceInfo.get("effect");
        final String type = configAdvanceInfo == null ? null : (String) configAdvanceInfo.get("type");
        final String schema = configAdvanceInfo == null ? null : (String) configAdvanceInfo.get("schema");

        final String md5Tmp = MD5Utils.md5Hex(configInfo.getContent(), Constants.ENCODE);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String sql =
                "INSERT INTO config_info(id,data_id,group_id,tenant_id,app_name,content,md5,src_ip,src_user,gmt_create,"
                        + "gmt_modified,c_desc,c_use,effect,type,c_schema) VALUES(nextval('config_info_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        final String sql =
//                "INSERT INTO config_info(data_id,group_id,tenant_id,app_name,content,md5,src_ip,src_user,gmt_create,"
//                        + "gmt_modified,c_desc,c_use,effect,type,c_schema) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            jt.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, configInfo.getDataId());
                    ps.setString(2, configInfo.getGroup());
                    ps.setString(3, tenantTmp);
                    ps.setString(4, appNameTmp);
                    ps.setString(5, configInfo.getContent());
                    ps.setString(6, md5Tmp);
                    ps.setString(7, srcIp);
                    ps.setString(8, srcUser);
                    ps.setTimestamp(9, time);
                    ps.setTimestamp(10, time);
                    ps.setString(11, desc);
                    ps.setString(12, use);
                    ps.setString(13, effect);
                    ps.setString(14, type);
                    ps.setString(15, schema);
                    return ps;
                }
            }, keyHolder);

            //因为这里用不了keyHolder，也不能直接查询，所以这个位置暂时用查询当前序列值并返回的方式处理
//            String seqQuery = SQL_FIND_CURRENT_SEQ_VALUE + " seq_config_info";
            String seqQuery = String.format(SQL_FIND_CURRENT_SEQ_VALUE,"config_info_seq");
            return (long)jt.queryForList(seqQuery).get(0).get("currval");

            //因为事务未提交，所以这里是查不到的
//            String querySql = SQL_FIND_CONFIG_INFO_BY_IDS +
//                    " data_id = '" + configInfo.getDataId() +
//                    "' group_id = '" + configInfo.getGroup() +
//                    "' tenant_id = '" + tenantTmp + "'";
//            ConfigInfo result = jt.query(querySql,(resultSet) -> {
//                ConfigInfo configInfo1 = new ConfigInfo(resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        resultSet.getString(6));
//                configInfo1.setId(resultSet.getLong(1));
//                return configInfo1;
//            });
//            if (Objects.isNull(result)){
//                throw new IllegalArgumentException("insert config_info fail");
//            }
//            return result.getId();
        } catch (CannotGetJdbcConnectionException e) {
            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
            e.printStackTrace();
            throw e;
        }
    }


//    @Override
//    public void addConfigTagRelationAtomic(long configId, String tagName, String dataId, String group, String tenant) {
//        try {
//            jt.update(
//                    "INSERT INTO config_tags_relation(id,tag_name,tag_type,data_id,group_id,tenant_id,nid) VALUES(?,?,?,?,?,?,nextval('seq_config_tags_relation'))",
//                    configId, tagName, null, dataId, group, tenant);
//        } catch (CannotGetJdbcConnectionException e) {
//            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @Override
//    public void insertConfigHistoryAtomic(long id, ConfigInfo configInfo, String srcIp, String srcUser,
//                                          final Timestamp time, String ops) {
//        String appNameTmp = StringUtils.isBlank(configInfo.getAppName()) ? StringUtils.EMPTY : configInfo.getAppName();
//        String tenantTmp = StringUtils.isBlank(configInfo.getTenant()) ? StringUtils.EMPTY : configInfo.getTenant();
//        final String md5Tmp = MD5Utils.md5Hex(configInfo.getContent(), Constants.ENCODE);
//        try {
//            jt.update(
//                    "INSERT INTO his_config_info (id,data_id,group_id,tenant_id,app_name,content,md5,src_ip,src_user,gmt_modified,op_type) "
//                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?)", id, configInfo.getDataId(), configInfo.getGroup(),
//                    tenantTmp, appNameTmp, configInfo.getContent(), md5Tmp, srcIp, srcUser, time, ops);
//        } catch (DataAccessException e) {
//            LogUtil.FATAL_LOG.error("[db-error] " + e.toString(), e);
//            e.printStackTrace();
//            throw e;
//        }
//    }

}
