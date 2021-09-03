/*
 Navicat Premium Data Transfer

 Source Server         : 本地PG5432
 Source Server Type    : PostgreSQL
 Source Server Version : 90616
 Source Host           : localhost:5432
 Source Catalog        : nacos
 Source Schema         : nacos

 Target Server Type    : PostgreSQL
 Target Server Version : 90616
 File Encoding         : 65001

 Date: 09/07/2021 15:15:55
*/


-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."config_info";
CREATE TABLE "nacos"."config_info" (
  "id" int8 NOT NULL DEFAULT nextval('config_info_seq'::regclass),
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(255) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "gmt_modified" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(20) COLLATE "pg_catalog"."default",
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default",
  "c_desc" varchar(256) COLLATE "pg_catalog"."default",
  "c_use" varchar(64) COLLATE "pg_catalog"."default",
  "effect" varchar(64) COLLATE "pg_catalog"."default",
  "type" varchar(64) COLLATE "pg_catalog"."default",
  "c_schema" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO "nacos"."config_info" VALUES (1, 'test1', 'DEFAULT_GROUP', 'server:
  port: 8900', '85480ced0ca25af3a2293921461b2980', '2021-07-02 08:30:13', '2021-07-02 08:30:13', NULL, '0:0:0:0:0:0:0:1', '', '', 'asd', NULL, NULL, 'yaml', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."config_info_aggr";
CREATE TABLE "nacos"."config_info_aggr" (
  "id" int8 NOT NULL DEFAULT nextval('config_info_aggr_seq'::regclass),
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "datum_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "gmt_modified" timestamp(6) NOT NULL,
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."config_info_beta";
CREATE TABLE "nacos"."config_info_beta" (
  "id" int8 NOT NULL DEFAULT nextval('config_info_beta_seq'::regclass),
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "beta_ips" varchar(1024) COLLATE "pg_catalog"."default",
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "gmt_modified" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(20) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."config_info_tag";
CREATE TABLE "nacos"."config_info_tag" (
  "id" int8 NOT NULL DEFAULT nextval('config_info_tag_seq'::regclass),
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default",
  "tag_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "gmt_modified" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(20) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."config_tags_relation";
CREATE TABLE "nacos"."config_tags_relation" (
  "id" int8 NOT NULL,
  "tag_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tag_type" varchar(64) COLLATE "pg_catalog"."default",
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default",
  "nid" int8 NOT NULL DEFAULT nextval('config_tags_relation_seq'::regclass)
)
;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."group_capacity";
CREATE TABLE "nacos"."group_capacity" (
  "id" int8 NOT NULL DEFAULT nextval('group_capacity_seq'::regclass),
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "quota" int8 NOT NULL DEFAULT 0,
  "usage" int8 NOT NULL DEFAULT 0,
  "max_size" int8 NOT NULL DEFAULT 0,
  "max_aggr_count" int8 NOT NULL DEFAULT 0,
  "max_aggr_size" int8 NOT NULL DEFAULT 0,
  "max_history_count" int8 NOT NULL DEFAULT 0,
  "gmt_create" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "gmt_modified" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone
)
;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."his_config_info";
CREATE TABLE "nacos"."his_config_info" (
  "id" int8 NOT NULL,
  "nid" int8 NOT NULL DEFAULT nextval('his_config_info_seq'::regclass),
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "gmt_modified" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(20) COLLATE "pg_catalog"."default",
  "op_type" char(10) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO "nacos"."his_config_info" VALUES (0, 1, 'test1', 'DEFAULT_GROUP', '', 'server:
  port: 8900', '85480ced0ca25af3a2293921461b2980', '2010-05-05 00:00:00', '2021-07-02 08:30:13', NULL, '0:0:0:0:0:0:0:1', 'I         ', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."permissions";
CREATE TABLE "nacos"."permissions" (
  "role" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "resource" varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
  "action" varchar(8) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO "nacos"."permissions" VALUES ('ROLE_ADMIN1', ':*:*', 'rw');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."roles";
CREATE TABLE "nacos"."roles" (
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "role" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO "nacos"."roles" VALUES ('lhw', 'ROLE_ADMIN1');
INSERT INTO "nacos"."roles" VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."tenant_capacity";
CREATE TABLE "nacos"."tenant_capacity" (
  "id" int8 NOT NULL DEFAULT nextval('tenant_capacity_seq'::regclass),
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "quota" int8 NOT NULL DEFAULT 0,
  "usage" int8 NOT NULL DEFAULT 0,
  "max_size" int8 NOT NULL DEFAULT 0,
  "max_aggr_count" int8 NOT NULL DEFAULT 0,
  "max_aggr_size" int8 NOT NULL DEFAULT 0,
  "max_history_count" int8 NOT NULL DEFAULT 0,
  "gmt_create" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone,
  "gmt_modified" timestamp(6) NOT NULL DEFAULT '2010-05-05 00:00:00'::timestamp without time zone
)
;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."tenant_info";
CREATE TABLE "nacos"."tenant_info" (
  "id" int8 NOT NULL DEFAULT nextval('tenant_info_seq'::regclass),
  "kp" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default",
  "tenant_name" varchar(128) COLLATE "pg_catalog"."default",
  "tenant_desc" varchar(256) COLLATE "pg_catalog"."default",
  "create_source" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" int8 NOT NULL,
  "gmt_modified" int8 NOT NULL
)
;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO "nacos"."tenant_info" VALUES (1, '1', '4f18d594-53a1-48ce-972e-96258136f39a', 'myNamespace', '我的命名空间', 'nacos', 1625215945065, 1625215945065);
INSERT INTO "nacos"."tenant_info" VALUES (3, '1', '6b7db12b-085f-40a0-873e-40f3d53ce6f7', 'new', '是', 'nacos', 1625814745964, 1625814745964);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "nacos"."users";
CREATE TABLE "nacos"."users" (
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" int2 NOT NULL
)
;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "nacos"."users" VALUES ('lhw', '$2a$10$7ghkWtJjfCAYuKh0uBZFSuBZ2sefFXV15rBb.D71Sc3hv33nwgspa', 1);
INSERT INTO "nacos"."users" VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

-- ----------------------------
-- Indexes structure for table config_info
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfo_datagrouptenant" ON "nacos"."config_info" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_info
-- ----------------------------
ALTER TABLE "nacos"."config_info" ADD CONSTRAINT "config_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_aggr
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfoaggr_datagrouptenantdatum" ON "nacos"."config_info_aggr" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "datum_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_info_aggr
-- ----------------------------
ALTER TABLE "nacos"."config_info_aggr" ADD CONSTRAINT "config_info_aggr_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_beta
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfobeta_datagrouptenant" ON "nacos"."config_info_beta" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_info_beta
-- ----------------------------
ALTER TABLE "nacos"."config_info_beta" ADD CONSTRAINT "config_info_beta_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_tag
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfotag_datagrouptenanttag" ON "nacos"."config_info_tag" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tag_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_info_tag
-- ----------------------------
ALTER TABLE "nacos"."config_info_tag" ADD CONSTRAINT "config_info_tag_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_tags_relation
-- ----------------------------
CREATE INDEX "idx_tenant_id" ON "nacos"."config_tags_relation" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_configtagrelation_configidtag" ON "nacos"."config_tags_relation" USING btree (
  "id" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "tag_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tag_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_tags_relation
-- ----------------------------
ALTER TABLE "nacos"."config_tags_relation" ADD CONSTRAINT "config_tags_relation_pkey" PRIMARY KEY ("nid");

-- ----------------------------
-- Indexes structure for table group_capacity
-- ----------------------------
CREATE UNIQUE INDEX "uk_group_id" ON "nacos"."group_capacity" USING btree (
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table group_capacity
-- ----------------------------
ALTER TABLE "nacos"."group_capacity" ADD CONSTRAINT "group_capacity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table his_config_info
-- ----------------------------
CREATE INDEX "idx_did" ON "nacos"."his_config_info" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_gmt_create" ON "nacos"."his_config_info" USING btree (
  "gmt_create" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "idx_gmt_modified" ON "nacos"."his_config_info" USING btree (
  "gmt_modified" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table his_config_info
-- ----------------------------
ALTER TABLE "nacos"."his_config_info" ADD CONSTRAINT "his_config_info_pkey" PRIMARY KEY ("nid");

-- ----------------------------
-- Indexes structure for table permissions
-- ----------------------------
CREATE UNIQUE INDEX "uk_role_permission" ON "nacos"."permissions" USING btree (
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "resource" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "action" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table roles
-- ----------------------------
CREATE UNIQUE INDEX "uk_username_role" ON "nacos"."roles" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table tenant_capacity
-- ----------------------------
CREATE UNIQUE INDEX "uk_tenant_id" ON "nacos"."tenant_capacity" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table tenant_capacity
-- ----------------------------
ALTER TABLE "nacos"."tenant_capacity" ADD CONSTRAINT "tenant_capacity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table tenant_info
-- ----------------------------
CREATE INDEX "idx_tenant_id1" ON "nacos"."tenant_info" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_tenant_info_kptenantid" ON "nacos"."tenant_info" USING btree (
  "kp" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table tenant_info
-- ----------------------------
ALTER TABLE "nacos"."tenant_info" ADD CONSTRAINT "tenant_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "nacos"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("username");


-- create sequence config_info_seq
DROP SEQUENCE IF EXISTS "nacos"."config_info_seq"	;
CREATE SEQUENCE "nacos"."config_info_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."config_info_seq" OWNER TO nacos;


-- create sequence config_info_aggr_seq
DROP SEQUENCE IF EXISTS "nacos"."config_info_aggr_seq";
CREATE SEQUENCE "nacos"."config_info_aggr_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."config_info_aggr_seq" OWNER TO nacos;

-- create sequence config_info_beta_seq
DROP SEQUENCE IF EXISTS "nacos"."config_info_beta_seq";
CREATE SEQUENCE "nacos"."config_info_beta_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."config_info_beta_seq" OWNER TO nacos;

-- create sequence config_info_tag_seq
DROP SEQUENCE IF EXISTS "nacos"."config_info_tag_seq";
CREATE SEQUENCE "nacos"."config_info_tag_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."config_info_tag_seq" OWNER TO nacos;

-- create sequence config_tags_relation_seq
DROP SEQUENCE IF EXISTS "nacos"."config_tags_relation_seq";
CREATE SEQUENCE "nacos"."config_tags_relation_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."config_tags_relation_seq" OWNER TO nacos;

-- create sequence group_capacity_seq
DROP SEQUENCE IF EXISTS "nacos"."group_capacity_seq";
CREATE SEQUENCE "nacos"."group_capacity_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."group_capacity_seq" OWNER TO nacos;

-- create sequence his_config_info_seq
DROP SEQUENCE IF EXISTS "nacos"."his_config_info_seq";
CREATE SEQUENCE "nacos"."his_config_info_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."his_config_info_seq" OWNER TO nacos;



-- create sequence tenant_capacity_seq
DROP SEQUENCE IF EXISTS "nacos"."tenant_capacity_seq";
CREATE SEQUENCE "nacos"."tenant_capacity_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."tenant_capacity_seq" OWNER TO nacos;

-- create sequence tenant_info_seq
DROP SEQUENCE IF EXISTS "nacos"."tenant_info_seq";
CREATE SEQUENCE "nacos"."tenant_info_seq"
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 999999
CACHE 1;

ALTER SEQUENCE "nacos"."tenant_info_seq" OWNER TO nacos;