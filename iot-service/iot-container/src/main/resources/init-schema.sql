DROP SCHEMA IF EXISTS "iot" CASCADE;
CREATE SCHEMA "iot";
SET search_path TO "iot";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS device_profile;
CREATE TABLE IF NOT EXISTS device_profile (
              id UUID PRIMARY KEY,  -- 主键
              name VARCHAR(255),  -- 配置名称
              type VARCHAR(255),  -- 类型
              image TEXT,  -- 设备图片（更优于 VARCHAR(1000000)）
              transport_type VARCHAR(255),  -- 传输类型
              provision_type VARCHAR(255),  -- 提供方式
              profile_data JSONB,  -- JSON 格式的设备配置
              description TEXT,  -- 设备描述
              firmware_id UUID,  -- 固件 ID
              software_id UUID,  -- 软件 ID
              default_rule_chain_id UUID,  -- 规则链 ID
              default_dashboard_id UUID,  -- 仪表盘 ID
              default_queue_name VARCHAR(255),  -- 默认队列
              provision_device_key VARCHAR UNIQUE,  -- 设备密钥（自动排除 NULL 值）
              default_edge_rule_chain_id UUID,  -- 默认边缘规则链
              external_id UUID,  -- 外部 ID
              is_default BOOLEAN,  -- 是否为默认配置
              is_deleted BOOLEAN DEFAULT false,
              tenant_id BIGINT,  -- 租户 ID
              version BIGINT DEFAULT 1,  -- 版本号
              created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
              created_by BIGINT,
              updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
              updated_by BIGINT,

    -- **唯一性约束**
              CONSTRAINT device_profile_name_unq_key UNIQUE (tenant_id, name),  -- 每个租户下的设备配置名称必须唯一
              CONSTRAINT device_provision_key_unq_key UNIQUE (provision_device_key),  -- 设备配置密钥唯一
              CONSTRAINT device_profile_external_id_unq_key UNIQUE (tenant_id, external_id)  -- 每个租户下外部 ID 唯一个租户下外部 ID 唯一


    -- **外键约束**
--               CONSTRAINT fk_default_rule_chain FOREIGN KEY (default_rule_chain_id) REFERENCES rule_chain(id) ON DELETE SET NULL,
--               CONSTRAINT fk_default_dashboard FOREIGN KEY (default_dashboard_id) REFERENCES dashboard(id) ON DELETE SET NULL,
--               CONSTRAINT fk_firmware FOREIGN KEY (firmware_id) REFERENCES ota_package(id) ON DELETE CASCADE,
--               CONSTRAINT fk_software FOREIGN KEY (software_id) REFERENCES ota_package(id) ON DELETE CASCADE,
--               CONSTRAINT fk_default_edge_rule_chain FOREIGN KEY (default_edge_rule_chain_id) REFERENCES rule_chain(id) ON DELETE SET NULL
);
DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer (
        id uuid NOT NULL CONSTRAINT customer_pkey PRIMARY KEY,
        additional_info varchar,
        address varchar,
        address2 varchar,
        city varchar(255),
        country varchar(255),
        email varchar(255),
        phone varchar(255),
        state varchar(255),
        tenant_id BIGINT,
        title varchar(255),
        zip varchar(255),
        external_id uuid,
        is_public boolean,
        version BIGINT DEFAULT 1,
        is_deleted BOOLEAN DEFAULT false,
        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
        created_by BIGINT,
        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
        updated_by BIGINT,
        CONSTRAINT customer_title_unq_key UNIQUE (tenant_id, title),
        CONSTRAINT customer_external_id_unq_key UNIQUE (tenant_id, external_id)
);
DROP TABLE IF EXISTS device;
CREATE TABLE IF NOT EXISTS device (
          id uuid NOT NULL CONSTRAINT device_pkey PRIMARY KEY,
          created_time bigint NOT NULL,
          additional_info varchar,
          customer_id uuid,
          device_profile_id uuid NOT NULL,
          device_data jsonb,
          type varchar(255),
          name varchar(255),
          label varchar(255),
          tenant_id BIGINT,
          firmware_id uuid,
          software_id uuid,
          external_id uuid,
          version BIGINT DEFAULT 1,
          is_deleted BOOLEAN DEFAULT false,
          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
          created_by BIGINT,
          updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
          updated_by BIGINT,
          CONSTRAINT device_name_unq_key UNIQUE (tenant_id, name),
          CONSTRAINT device_external_id_unq_key UNIQUE (tenant_id, external_id),
--           CONSTRAINT fk_device_profile FOREIGN KEY (device_profile_id) REFERENCES device_profile(id),
--           CONSTRAINT fk_firmware_device FOREIGN KEY (firmware_id) REFERENCES ota_package(id),
--           CONSTRAINT fk_software_device FOREIGN KEY (software_id) REFERENCES ota_package(id)
);

CREATE SEQUENCE IF NOT EXISTS ts_kv_latest_version_seq cache 1;

CREATE TABLE IF NOT EXISTS ts_kv_latest
(
    entity_id uuid   NOT NULL,
    key       int    NOT NULL,
    ts        bigint NOT NULL,
    bool_v    boolean,
    str_v     varchar(10000000),
    long_v    bigint,
    dbl_v     double precision,
    json_v    json,
    version bigint default 0,
    CONSTRAINT ts_kv_latest_pkey PRIMARY KEY (entity_id, key)
);

CREATE TABLE IF NOT EXISTS key_dictionary
(
    key    varchar(255) NOT NULL,
    key_id serial UNIQUE,
    CONSTRAINT key_dictionary_id_pkey PRIMARY KEY (key)
);

CREATE TABLE IF NOT EXISTS attribute_kv (
    entity_id uuid,
    attribute_type int,
    attribute_key int,
    bool_v boolean,
    str_v varchar(10000000),
    long_v bigint,
    dbl_v double precision,
    json_v json,
    last_update_ts bigint,
    version bigint default 0,
    CONSTRAINT attribute_kv_pkey PRIMARY KEY (entity_id, attribute_type, attribute_key)
);
