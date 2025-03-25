DROP SCHEMA IF EXISTS "blog" CASCADE;

CREATE SCHEMA "blog";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TYPE IF EXISTS article_status;
CREATE TYPE article_status AS ENUM ('DRAFT', 'PUBLISHED', 'PENDING_APPROVAL', 'DELETED', 'TAKEN_DOWN');

--------------------------
-- 分类表（核心基础数据）
--------------------------
DROP TABLE IF EXISTS "blog".category CASCADE;
CREATE TABLE "blog".category (
                                 id BIGSERIAL PRIMARY KEY,
                                 name VARCHAR(50) NOT NULL UNIQUE,
                                 sort INT NOT NULL DEFAULT 0 CHECK (sort >= 0),
                                 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 is_deleted BOOLEAN DEFAULT false
);
COMMENT ON TABLE "blog".category IS '文章分类表';
COMMENT ON COLUMN "blog".category.name IS '分类名称（唯一）';


DROP TABLE IF EXISTS "blog".tag CASCADE;
--------------------------
-- 标签表（核心基础数据）
--------------------------
CREATE TABLE "blog".tag (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE,
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            is_deleted BOOLEAN DEFAULT false
);
COMMENT ON TABLE "blog".tag IS '文章标签表';
COMMENT ON COLUMN "blog".tag.name IS '标签名称（唯一）';

--------------------------
-- 文章主表（核心业务表）
--------------------------
DROP TABLE IF EXISTS "blog".article CASCADE;
CREATE TABLE "blog".article (
                                id UUID PRIMARY KEY,
                                title VARCHAR(255) NOT NULL,
                                content TEXT NOT NULL,
                                cover_url VARCHAR(512),
                                category_id BIGINT NOT NULL,
                                version BIGINT DEFAULT 0,
                                author_id VARCHAR(32) NOT NULL,
                                author_name VARCHAR(64) NOT NULL,
                                view_count INT NOT NULL DEFAULT 0 CHECK (view_count >= 0),
                                like_count INT NOT NULL DEFAULT 0 CHECK (like_count >= 0),
                                status article_status NOT NULL,
                                publish_time TIMESTAMP WITH TIME ZONE,
                                is_top BOOLEAN DEFAULT false,
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP


);
CREATE INDEX idx_article_category ON "blog".article (category_id);
CREATE INDEX idx_article_status ON "blog".article (status);
CREATE INDEX idx_publish_time ON "blog".article (publish_time);
COMMENT ON TABLE "blog".article IS '文章主表';
COMMENT ON COLUMN "blog".article.status IS '状态: DRAFT-草稿, PUBLISHED-已发布, DELETED-已删除';

--------------------------
-- 文章-标签关系表（多对多）
--------------------------
DROP TABLE IF EXISTS "blog".article_tag CASCADE;
CREATE TABLE "blog".article_tag (
                                    article_id uuid NOT NULL,
                                    tag_id BIGINT NOT NULL,
                                    PRIMARY KEY (article_id, tag_id),
                                    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE "blog".article_tag IS '文章-标签多对多关系表';
CREATE INDEX idx_article_tag ON "blog".article_tag(tag_id);

DROP TYPE IF EXISTS target_type;
CREATE TYPE target_type AS ENUM ('ARTICLE', 'COMMENT');
DROP TABLE IF EXISTS "blog".like CASCADE;
CREATE TABLE "blog".like (
                                id UUID PRIMARY KEY,
                                target_id UUID NOT NULL,
                                user_id BIGINT NOT NULL,
                                target_type target_type NOT NULL,
                                active BOOLEAN,
                                last_modified TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                version BIGINT DEFAULT 0,
                                is_deleted BOOLEAN DEFAULT false
);
CREATE INDEX idx_like_user_id_target_id ON "blog".like(user_id, target_id) where is_deleted = false;

DROP TYPE IF EXISTS "blog".favorite_type;
CREATE TYPE "blog".favorite_type AS ENUM ('ARTICLE');
DROP TABLE IF EXISTS "blog".favorite CASCADE;
CREATE TABLE "blog".favorite (
                             id UUID PRIMARY KEY,
                             target_id UUID NOT NULL,
                             user_id BIGINT NOT NULL,
                             favorite_type favorite_type NOT NULL,
                             active BOOLEAN,
                             last_modified TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             version BIGINT DEFAULT 0,
                             is_deleted BOOLEAN DEFAULT false
);
CREATE INDEX idx_favorite_user_id_target_id ON "blog".like(user_id, target_id) where is_deleted = false;



-- 插入20条文章数据
INSERT INTO "blog".category (name, sort, is_deleted) VALUES
                                                         ('技术', 1, false),
                                                         ('生活', 2, false),
                                                         ('旅游', 3, false),
                                                         ('美食', 4, false),
                                                         ('健康', 5, false);
INSERT INTO "blog".tag (name, is_deleted) VALUES
                                              ('Java', false),
                                              ('Spring Boot', false),
                                              ('数据库', false),
                                              ('旅行攻略', false),
                                              ('健康饮食', false),
                                              ('健身', false),
                                              ('编程', false),
                                              ('前端开发', false),
                                              ('后端开发', false),
                                              ('云原生', false);
DO $$
    DECLARE
        article_id UUID;
        category_id BIGINT;
    BEGIN
        -- 获取分类ID
        SELECT id INTO category_id FROM "blog".category WHERE name = '技术';

        -- 插入20条文章数据
        FOR i IN 1..20 LOOP
                -- 手动生成 UUID（示例值，可根据需要修改）
                article_id := '00000000-0000-0000-0000-' || LPAD(i::TEXT, 12, '0');

                -- 插入文章数据
                INSERT INTO "blog".article (id, title, content, cover_url, category_id, author_id, author_name, view_count, like_count, status, publish_time, is_top)
                VALUES (
                           article_id,
                           '文章标题 ' || i,
                           '这是第 ' || i || ' 篇文章的内容。',
                           'https://example.com/cover' || i || '.jpg',
                           category_id,
                           'user' || LPAD(i::TEXT, 3, '0'),
                           '作者 ' || i,
                           (i * 10),
                           (i * 5),
                           CASE WHEN i % 2 = 0 THEN 'PUBLISHED'::article_status ELSE 'DRAFT'::article_status END,
                           NOW() - (i || ' days')::INTERVAL,
                           CASE WHEN i % 5 = 0 THEN true ELSE false END
                       );

                -- 插入文章-标签关系数据
                INSERT INTO "blog".article_tag (article_id, tag_id)
                VALUES (article_id, (i % 10) + 1);
            END LOOP;
    END $$;

