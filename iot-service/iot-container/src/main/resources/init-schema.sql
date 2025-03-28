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

