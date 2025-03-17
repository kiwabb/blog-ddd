DROP SCHEMA IF EXISTS "system" CASCADE;

CREATE SCHEMA "system";
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
                          id BIGSERIAL PRIMARY KEY,
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
                          create_by BIGINT,
                          updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          update_by BIGINT,
                          is_deleted BOOLEAN DEFAULT false,
                          tenant_id BIGINT
);