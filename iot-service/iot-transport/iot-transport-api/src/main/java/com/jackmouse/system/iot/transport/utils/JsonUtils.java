package com.jackmouse.system.iot.transport.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jackmouse.server.gen.transport.TransportProtos;

import java.util.List;

/**
 * @ClassName JsonUtils
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 16:58
 * @Version 1.0
 **/
public class JsonUtils {
    public static JsonObject getJsonObject(List<TransportProtos.KeyValueProto> tsKv) {
        JsonObject json = new JsonObject();
        for (TransportProtos.KeyValueProto kv : tsKv) {
            switch (kv.getType()) {
                case BOOLEAN_V:
                    json.addProperty(kv.getKey(), kv.getBoolV());
                    break;
                case LONG_V:
                    json.addProperty(kv.getKey(), kv.getLongV());
                    break;
                case DOUBLE_V:
                    json.addProperty(kv.getKey(), kv.getDoubleV());
                    break;
                case STRING_V:
                    json.addProperty(kv.getKey(), kv.getStringV());
                    break;
                case JSON_V:
                    json.add(kv.getKey(), JsonParser.parseString(kv.getJsonV()));
                    break;
            }
        }
        return json;
    }
}
