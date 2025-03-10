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

