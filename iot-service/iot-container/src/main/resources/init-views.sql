DROP VIEW IF EXISTS device_info_active_ts_view CASCADE;
CREATE OR REPLACE VIEW device_info_active_ts_view AS
SELECT d.*
     , c.title as customer_title
     , COALESCE((c.additional_info::json->>'isPublic')::bool, FALSE) as customer_is_public
     , d.type as device_profile_name
     , COALESCE(dt.bool_v, FALSE) as active
FROM device d
         LEFT JOIN customer c ON c.id = d.customer_id
         LEFT JOIN ts_kv_latest dt ON dt.entity_id = d.id and dt.key =
                                                              (select key_id from key_dictionary where key = 'active');
DROP VIEW IF EXISTS device_info_active_attribute_view CASCADE;
CREATE OR REPLACE VIEW device_info_active_attribute_view AS
SELECT d.*
     , c.title as customer_title
     , COALESCE((c.additional_info::json->>'isPublic')::bool, FALSE) as customer_is_public
     , d.type as device_profile_name
     , COALESCE(da.bool_v, FALSE) as active
FROM device d
         LEFT JOIN customer c ON c.id = d.customer_id
         LEFT JOIN attribute_kv da ON da.entity_id = d.id AND da.attribute_type = 2 AND da.attribute_key = (select key_id from key_dictionary  where key = 'active');
DROP VIEW IF EXISTS device_info_view CASCADE;
CREATE OR REPLACE VIEW device_info_view AS SELECT * FROM device_info_active_attribute_view;