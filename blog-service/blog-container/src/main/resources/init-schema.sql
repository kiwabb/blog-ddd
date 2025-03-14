DROP SCHEMA IF EXISTS "blog" CASCADE;

CREATE SCHEMA "blog";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS article_status;
create type article_status as enum ('DRAFT', 'PUBLISHED', 'PENDING_APPROVAL', 'DELETED', 'TAKEN_DOWN');

-- Drop table

-- DROP TABLE article;

CREATE TABLE article (
                         id uuid NOT NULL,
                         title varchar(255) NOT NULL,
                         "content" text NOT NULL,
                         cover_url varchar(512) NULL,
                         category_id int8 NOT NULL,
                         author_id varchar(32) NOT NULL,
                         author_name varchar(64) NOT NULL,
                         view_count int4 DEFAULT 0 NOT NULL,
                         like_count int4 DEFAULT 0 NOT NULL,
                         status public."article_status" NOT NULL,
                         publish_time timestamptz NULL,
                         is_top bool DEFAULT false NULL,
                         created_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                         updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                         "version" int8 DEFAULT 0 NULL,
                         CONSTRAINT article_like_count_check CHECK ((like_count >= 0)),
                         CONSTRAINT article_pkey PRIMARY KEY (id),
                         CONSTRAINT article_view_count_check CHECK ((view_count >= 0))
);
CREATE INDEX idx_article_category ON blog.article USING btree (category_id);
CREATE INDEX idx_article_status ON blog.article USING btree (status);
CREATE INDEX idx_publish_time ON blog.article USING btree (publish_time);


-- blog.article_tag definition

-- Drop table

-- DROP TABLE article_tag;

CREATE TABLE article_tag (
                             article_id uuid NOT NULL,
                             tag_id int8 NOT NULL,
                             created_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                             CONSTRAINT article_tag_pkey PRIMARY KEY (article_id, tag_id)
);
CREATE INDEX idx_article_tag ON blog.article_tag USING btree (tag_id);


-- blog.category definition

-- Drop table

-- DROP TABLE category;

CREATE TABLE category (
                          id bigserial NOT NULL,
                          "name" varchar(50) NOT NULL,
                          sort int4 DEFAULT 0 NOT NULL,
                          created_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                          updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                          is_deleted bool DEFAULT false NULL,
                          CONSTRAINT category_name_key UNIQUE (name),
                          CONSTRAINT category_pkey PRIMARY KEY (id),
                          CONSTRAINT category_sort_check CHECK ((sort >= 0))
);


-- blog.tag definition

-- Drop table

-- DROP TABLE tag;

CREATE TABLE tag (
                     id bigserial NOT NULL,
                     "name" varchar(50) NOT NULL,
                     created_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                     updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
                     is_deleted bool DEFAULT false NULL,
                     CONSTRAINT tag_name_key UNIQUE (name),
                     CONSTRAINT tag_pkey PRIMARY KEY (id)
);
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

