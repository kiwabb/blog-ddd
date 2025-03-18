DROP SCHEMA IF EXISTS "system" CASCADE;

CREATE SCHEMA "system";
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TYPE IF EXISTS "system".user_status;

CREATE TYPE "system".user_status AS ENUM (
    'NORMAL',
    'LOCKED',
    'DISABLED',
    'DELETED',
    'PENDING_APPROVAL',
    'PENDING_ACTIVATION',
    'PENDING_RESET_PASSWORD',
    'PENDING_CHANGE_PASSWORD',
    'PENDING_RESET_EMAIL',
    'PENDING_CHANGE_EMAIL',
    'PENDING_RESET_PHONE',
    'PENDING_CHANGE_PHONE',
    'PENDING_RESET_USERNAME'
);

DROP TYPE IF EXISTS "system".user_type;

CREATE TYPE "system".user_type AS ENUM (
    'SUPER_ADMIN',
    'ADMIN',
    'NORMAL'
    );
DROP TYPE IF EXISTS "system".sex;

CREATE TYPE "system".sex AS ENUM (
    'MALE',
    'FEMALE',
    'UNKNOWN'
    );
DROP TABLE IF EXISTS "system".sys_user;

CREATE TABLE "system".sys_user (
                          id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY
                              (START WITH 1 INCREMENT BY 1) NOT NULL,
                          username VARCHAR(50) NOT NULL,
                          password VARCHAR(100) NOT NULL,
                          nickname VARCHAR(255),
                          avatar VARCHAR(1024),
                          phone VARCHAR(255),
                          email VARCHAR(255),
                          sex "system".sex,
                          version INT DEFAULT 0,
                          user_type "system".user_type,
                          status "system".user_status NOT NULL DEFAULT 'NORMAL',
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          created_by BIGINT,
                          updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          updated_by BIGINT,
                          is_deleted BOOLEAN DEFAULT false,
                          tenant_id BIGINT NOT NULL
);
CREATE INDEX idx_user_name ON "system".sys_user USING gin (username gin_trgm_ops) where is_deleted = false; -- 模糊查询优化
DROP TYPE IF EXISTS "system".menu_category;
CREATE TYPE menu_category AS ENUM (
    'SYSTEM_CATALOG',  -- 系统目录
    'BUSINESS_MENU',   -- 业务菜单
    'API_RESOURCE' ,    -- API资源
    'BUTTON' --api按钮
);
DROP TABLE IF EXISTS "system".sys_menu;
CREATE TABLE "system".sys_menu (
                          id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY
                              (START WITH 1 INCREMENT BY 1) NOT NULL,
                          parent_id BIGINT NOT NULL DEFAULT 0,
                          name VARCHAR(64),
                          path VARCHAR(256),
                          component VARCHAR(256),
                          type "system".menu_category NOT NULL,
                          hidden BOOLEAN NOT NULL DEFAULT false,
                          icon VARCHAR(32),
                          sort INTEGER NOT NULL CHECK (sort >= 0),
                          version INT DEFAULT 0,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          created_by BIGINT,
                          updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          updated_by BIGINT,
                          is_deleted BOOLEAN NOT NULL DEFAULT false,
                          tenant_id BIGINT NOT NULL,
                          component_name VARCHAR(100)
);
COMMENT ON TABLE "system".sys_menu IS '菜单表';
COMMENT ON COLUMN "system".sys_menu.id IS '主键';
COMMENT ON COLUMN "system".sys_menu.parent_id IS '父菜单ID（0表示根菜单）';
COMMENT ON COLUMN "system".sys_menu.type IS '菜单类型（0-目录 1-菜单 2-按钮 3-外链）';
COMMENT ON COLUMN "system".sys_menu.is_deleted IS '删除标记';
-- 常用查询字段索引
CREATE INDEX idx_parent_id ON "system".sys_menu(tenant_id, parent_id) where is_deleted = false;
CREATE INDEX idx_path ON "system".sys_menu(tenant_id, path) where is_deleted = false;



DROP TABLE IF EXISTS "system".sys_role;
CREATE TABLE "system".sys_role (
                                   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY
                                       (START WITH 1 INCREMENT BY 1) NOT NULL,
                                   code VARCHAR(32) NOT NULL,
                                   name VARCHAR(50) NOT NULL,
                                   data_scope VARCHAR(50) NOT NULL,
                                   is_enabled BOOLEAN NOT NULL DEFAULT true,
                                   version INT DEFAULT 0,
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   created_by BIGINT,
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_by BIGINT,
                                   is_deleted BOOLEAN NOT NULL DEFAULT false,
                                   tenant_id BIGINT NOT NULL,  -- 多租户系统建议非空
    -- 唯一约束
                                   CONSTRAINT uniq_role_code UNIQUE (code, tenant_id)
);

-- 注释配置
COMMENT ON TABLE "system".sys_role IS '系统角色表';
COMMENT ON COLUMN "system".sys_role.code IS '角色唯一编码（租户内唯一）';
COMMENT ON COLUMN "system".sys_role.data_scope IS '数据权限范围（ALL-全部, DEPT-本部门及子部门, DEPT_ONLY-仅本部门, SELF-仅自己）';
COMMENT ON COLUMN "system".sys_role.is_enabled IS '启用状态';

-- 索引建议
CREATE INDEX idx_role_tenant ON "system".sys_role(tenant_id) INCLUDE (code, name) where is_deleted = false ;
CREATE INDEX idx_role_name ON "system".sys_role USING gin (name gin_trgm_ops); -- 模糊查询优化



-- 角色菜单关联表
DROP TABLE IF EXISTS "system".sys_role_menu;
CREATE TABLE "system".sys_role_menu (
                                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
                                        role_id BIGINT NOT NULL,
                                        menu_id BIGINT NOT NULL,
                                        version INT DEFAULT 0,
                                        tenant_id BIGINT NOT NULL,  -- 租户隔离字段
                                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                        created_by BIGINT,
                                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                        updated_by BIGINT,
                                        is_deleted BOOLEAN NOT NULL DEFAULT false,

    -- 复合唯一约束（防止重复授权）
                                        CONSTRAINT uniq_role_menu UNIQUE (role_id, menu_id, tenant_id)
);

COMMENT ON TABLE "system".sys_role_menu IS '角色菜单关联表';
COMMENT ON COLUMN "system".sys_role_menu.role_id IS '角色ID（关联sys_role.id）';
COMMENT ON COLUMN "system".sys_role_menu.menu_id IS '菜单ID（关联sys_menu.id）';
COMMENT ON COLUMN "system".sys_role_menu.tenant_id IS '租户ID（业务隔离）';

-- 索引优化
CREATE INDEX idx_role_menu_tenant ON "system".sys_role_menu(tenant_id) INCLUDE (role_id, menu_id) WHERE is_deleted = false;
CREATE INDEX idx_menu_role ON "system".sys_role_menu(menu_id) WHERE is_deleted = false;

-- 角色用户关联表
DROP TABLE IF EXISTS "system".sys_role_user;
CREATE TABLE "system".sys_role_user (
                                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
                                        user_id BIGINT NOT NULL,
                                        role_id BIGINT NOT NULL,
                                        version INT DEFAULT 0,
                                        tenant_id BIGINT NOT NULL,
                                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                        created_by BIGINT,
                                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                        updated_by BIGINT,
                                        is_deleted BOOLEAN NOT NULL DEFAULT false,

    -- 复合唯一约束
                                        CONSTRAINT uniq_user_role UNIQUE (user_id, role_id, tenant_id)
);

COMMENT ON TABLE "system".sys_role_user IS '角色用户关联表';
COMMENT ON COLUMN "system".sys_role_user.user_id IS '用户ID（关联sys_user.id）';
COMMENT ON COLUMN "system".sys_role_user.role_id IS '角色ID（关联sys_role.id）';

-- 索引优化
CREATE INDEX idx_role_user_tenant ON "system".sys_role_user(tenant_id) INCLUDE (role_id, user_id) WHERE is_deleted = false;
CREATE INDEX idx_user_role ON "system".sys_role_user(user_id) WHERE is_deleted = false;

