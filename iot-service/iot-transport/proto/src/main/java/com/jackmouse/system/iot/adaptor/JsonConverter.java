package com.jackmouse.system.iot.adaptor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.server.gen.transport.TransportProtos.*;
import com.jackmouse.server.gen.transport.TransportProtos.KeyValueProto;
import com.jackmouse.server.gen.transport.TransportProtos.TsKvListProto;
import com.jackmouse.server.gen.transport.TransportProtos.PostTelemetryMsg;
import com.jackmouse.system.iot.entry.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JsonConverter
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:23
 * @Version 1.0
 **/
public class JsonConverter {
    private static final String CAN_T_PARSE_VALUE = "Can't parse value: ";

    private static boolean isTypeCastEnabled = true;
    private static int maxStringValueLength = 0;

    public static PostTelemetryMsg convertToTelemetryProto(JsonElement jsonElement, long ts) throws JsonSyntaxException {
        PostTelemetryMsg.Builder builder = PostTelemetryMsg.newBuilder();
        convertToTelemetry(jsonElement, ts, null, builder);
        return builder.build();
    }
    public static PostTelemetryMsg convertToTelemetryProto(JsonElement jsonElement) {
        return convertToTelemetryProto(jsonElement, System.currentTimeMillis());

    }

    private static void convertToTelemetry(JsonElement jsonElement, long systemTs, Map<Long, List<KvEntry>> result, PostTelemetryMsg.Builder builder) {
        if (jsonElement.isJsonObject()) {
            parseObject(systemTs, result, builder, jsonElement.getAsJsonObject());
        } else if (jsonElement.isJsonArray()) {
            jsonElement.getAsJsonArray().forEach(je -> {
                if (je.isJsonObject()) {
                    parseObject(systemTs, result, builder, je.getAsJsonObject());
                } else {
                    throw new JsonSyntaxException(CAN_T_PARSE_VALUE + je);
                }
            });
        } else {
            throw new JsonSyntaxException(CAN_T_PARSE_VALUE + jsonElement);
        }
    }
    private static void parseObject(long systemTs, Map<Long, List<KvEntry>> result, PostTelemetryMsg.Builder builder, JsonObject jo) {
        if (result != null) {
            parseObject(result, systemTs, jo);
        } else {
            parseObject(builder, systemTs, jo);
        }
    }
    private static void parseObject(Map<Long, List<KvEntry>> result, long systemTs, JsonObject jo) {
        if (jo.has("ts") && jo.has("values")) {
            parseWithTs(result, jo);
        } else {
            parseWithoutTs(result, systemTs, jo);
        }
    }
    private static void parseObject(PostTelemetryMsg.Builder builder, long systemTs, JsonObject jo) {
        if (jo.has("ts") && jo.has("values")) {
            parseWithTs(builder, jo);
        } else {
            parseWithoutTs(builder, systemTs, jo);
        }
    }

    private static void parseWithoutTs(Map<Long, List<KvEntry>> result, long systemTs, JsonObject jo) {
        for (KvEntry entry : parseValues(jo)) {
            result.computeIfAbsent(systemTs, tmp -> new ArrayList<>()).add(entry);
        }
    }

    public static void parseWithTs(Map<Long, List<KvEntry>> result, JsonObject jo) {
        long ts = jo.get("ts").getAsLong();
        JsonObject valuesObject = jo.get("values").getAsJsonObject();
        for (KvEntry entry : parseValues(valuesObject)) {
            result.computeIfAbsent(ts, tmp -> new ArrayList<>()).add(entry);
        }
    }
    private static void parseWithoutTs(PostTelemetryMsg.Builder request, long systemTs, JsonObject jo) {
        TsKvListProto.Builder builder = TsKvListProto.newBuilder();
        builder.setTs(systemTs);
        builder.addAllKv(parseProtoValues(jo));
        request.addTsKvList(builder.build());
    }

    private static void parseWithTs(PostTelemetryMsg.Builder request, JsonObject jo) {
        TsKvListProto.Builder builder = TsKvListProto.newBuilder();
        builder.setTs(jo.get("ts").getAsLong());
        builder.addAllKv(parseProtoValues(jo.get("values").getAsJsonObject()));
        request.addTsKvList(builder.build());
    }
    private static List<TransportProtos.KeyValueProto> parseProtoValues(JsonObject valuesObject) {
        List<KeyValueProto> result = new ArrayList<>();
        for (Map.Entry<String, JsonElement> valueEntry : valuesObject.entrySet()) {
            JsonElement element = valueEntry.getValue();
            if (element.isJsonPrimitive()) {
                JsonPrimitive value = element.getAsJsonPrimitive();
                if (value.isString()) {
                    if (maxStringValueLength > 0 && value.getAsString().length() > maxStringValueLength) {
                        String message = String.format("String value length [%d] for key [%s] is greater than maximum allowed [%d]", value.getAsString().length(), valueEntry.getKey(), maxStringValueLength);
                        throw new JsonSyntaxException(message);
                    }
                    if (isTypeCastEnabled && NumberUtils.isParsable(value.getAsString())) {
                        try {
                            result.add(buildNumericKeyValueProto(value, valueEntry.getKey()));
                        } catch (RuntimeException th) {
                            result.add(KeyValueProto.newBuilder().setKey(valueEntry.getKey()).setType(KeyValueType.STRING_V)
                                    .setStringV(value.getAsString()).build());
                        }
                    } else {
                        result.add(KeyValueProto.newBuilder().setKey(valueEntry.getKey()).setType(KeyValueType.STRING_V)
                                .setStringV(value.getAsString()).build());
                    }
                } else if (value.isBoolean()) {
                    result.add(KeyValueProto.newBuilder().setKey(valueEntry.getKey()).setType(KeyValueType.BOOLEAN_V)
                            .setBoolV(value.getAsBoolean()).build());
                } else if (value.isNumber()) {
                    result.add(buildNumericKeyValueProto(value, valueEntry.getKey()));
                } else if (!value.isJsonNull()) {
                    throw new JsonSyntaxException(CAN_T_PARSE_VALUE + value);
                }
            } else if (element.isJsonObject() || element.isJsonArray()) {
                result.add(KeyValueProto.newBuilder().setKey(valueEntry.getKey()).setType(KeyValueType.JSON_V).setJsonV(element.toString()).build());
            } else if (!element.isJsonNull()) {
                throw new JsonSyntaxException(CAN_T_PARSE_VALUE + element);
            }
        }
        return result;
    }
    private static KeyValueProto buildNumericKeyValueProto(JsonPrimitive value, String key) {
        String valueAsString = value.getAsString();
        KeyValueProto.Builder builder = KeyValueProto.newBuilder().setKey(key);
        var bd = new BigDecimal(valueAsString);
        if (bd.stripTrailingZeros().scale() <= 0 && !isSimpleDouble(valueAsString)) {
            try {
                return builder.setType(TransportProtos.KeyValueType.LONG_V).setLongV(bd.longValueExact()).build();
            } catch (ArithmeticException e) {
                if (isTypeCastEnabled) {
                    return builder.setType(KeyValueType.STRING_V).setStringV(bd.toPlainString()).build();
                } else {
                    throw new JsonSyntaxException("Big integer values are not supported!");
                }
            }
        } else {
            if (bd.scale() <= 16) {
                return builder.setType(KeyValueType.DOUBLE_V).setDoubleV(bd.doubleValue()).build();
            } else if (isTypeCastEnabled) {
                return builder.setType(KeyValueType.STRING_V).setStringV(bd.toPlainString()).build();
            } else {
                throw new JsonSyntaxException("Big integer values are not supported!");
            }
        }

    }
    private static List<KvEntry> parseValues(JsonObject valuesObject) {
        List<KvEntry> result = new ArrayList<>();
        for (Map.Entry<String, JsonElement> valueEntry : valuesObject.entrySet()) {
            JsonElement element = valueEntry.getValue();
            if (element.isJsonPrimitive()) {
                JsonPrimitive value = element.getAsJsonPrimitive();
                if (value.isString()) {
                    if (maxStringValueLength > 0 && value.getAsString().length() > maxStringValueLength) {
                        String message = String.format("String value length [%d] for key [%s] is greater than maximum allowed [%d]", value.getAsString().length(), valueEntry.getKey(), maxStringValueLength);
                        throw new JsonSyntaxException(message);
                    }
                    if (isTypeCastEnabled && NumberUtils.isParsable(value.getAsString())) {
                        try {
                            parseNumericValue(result, valueEntry, value);
                        } catch (RuntimeException th) {
                            result.add(new StringDataEntry(valueEntry.getKey(), value.getAsString()));
                        }
                    } else {
                        result.add(new StringDataEntry(valueEntry.getKey(), value.getAsString()));
                    }
                } else if (value.isBoolean()) {
                    result.add(new BooleanDataEntry(valueEntry.getKey(), value.getAsBoolean()));
                } else if (value.isNumber()) {
                    parseNumericValue(result, valueEntry, value);
                } else {
                    throw new JsonSyntaxException(CAN_T_PARSE_VALUE + value);
                }
            } else if (element.isJsonObject() || element.isJsonArray()) {
                result.add(new JsonDataEntry(valueEntry.getKey(), element.toString()));
            } else {
                throw new JsonSyntaxException(CAN_T_PARSE_VALUE + element);
            }
        }
        return result;
    }
    private static void parseNumericValue(List<KvEntry> result, Map.Entry<String, JsonElement> valueEntry, JsonPrimitive value) {
        String valueAsString = value.getAsString();
        String key = valueEntry.getKey();
        var bd = new BigDecimal(valueAsString);
        if (bd.stripTrailingZeros().scale() <= 0 && !isSimpleDouble(valueAsString)) {
            try {
                result.add(new LongDataEntry(key, bd.longValueExact()));
            } catch (ArithmeticException e) {
                if (isTypeCastEnabled) {
                    result.add(new StringDataEntry(key, bd.toPlainString()));
                } else {
                    throw new JsonSyntaxException("Big integer values are not supported!");
                }
            }
        } else {
            if (bd.scale() <= 16) {
                result.add(new DoubleDataEntry(key, bd.doubleValue()));
            } else if (isTypeCastEnabled) {
                result.add(new StringDataEntry(key, bd.toPlainString()));
            } else {
                throw new JsonSyntaxException("Big integer values are not supported!");
            }
        }
    }
    private static boolean isSimpleDouble(String valueAsString) {
        return valueAsString.contains(".") && !valueAsString.contains("E") && !valueAsString.contains("e");
    }

}
