package com.jackmouse.system.iot.transport.http;

import com.google.gson.JsonParser;
import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.adaptor.JsonConverter;
import com.jackmouse.system.iot.transport.TransportContext;
import com.jackmouse.system.iot.transport.TransportService;
import com.jackmouse.system.iot.transport.TransportServiceCallback;
import com.jackmouse.system.iot.transport.auth.SessionInfoCreator;
import com.jackmouse.system.iot.transport.auth.ValidateDeviceCredentialsResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @ClassName DeviceApiController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 11:04
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DeviceApiController {
    @Autowired
    private HttpTransportContext transportContext;

    @Operation(
            summary = "发布时间序列数据（postTelemetry）",
            description = "发布时间序列数据（postTelemetry）",
            method = "POST"
    )
    @PostMapping("{deviceToken}/telemetry")
    public DeferredResult<ResponseEntity> postTelemetry(@PathVariable("deviceToken") String deviceToken,
                                                        @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<>();
        transportContext.getTransportService().process(DeviceTransportType.DEFAULT,
                TransportProtos.ValidateDeviceTokenRequestMsg.newBuilder().setToken(deviceToken).build(),
                new DeviceAuthCallback(transportContext, responseWriter, sessionInfo -> {
                    TransportService transportService = transportContext.getTransportService();
                    transportService.process(sessionInfo, JsonConverter.convertToTelemetryProto(JsonParser.parseString(json)),
                            new HttpOkCallback(responseWriter));
                }));
        return responseWriter;
    }

    public static class HttpOkCallback implements TransportServiceCallback<Void> {
        private final DeferredResult<ResponseEntity> responseWriter;

        public HttpOkCallback(DeferredResult<ResponseEntity> responseWriter) {
            this.responseWriter = responseWriter;
        }

        @Override
        public void onSuccess(Void msg) {
            responseWriter.setResult(ResponseEntity.ok().build());
        }

        @Override
        public void onFailure(Throwable t) {
            responseWriter.setResult(ResponseEntity.internalServerError().build());
        }
    }

    @RequiredArgsConstructor
    static class DeviceAuthCallback implements TransportServiceCallback<ValidateDeviceCredentialsResponse> {
        private final TransportContext transportContext;
        private final DeferredResult<ResponseEntity> responseWriter;
        private final Consumer<TransportProtos.SessionInfoProto> onSuccess;

        @Override
        public void onSuccess(ValidateDeviceCredentialsResponse msg) {
            onSuccess.accept(SessionInfoCreator.create(msg, transportContext, UUID.randomUUID()));
        }

        @Override
        public void onFailure(Throwable t) {
            log.debug("Failed to process request in DeviceAuthCallback: {}", t.getMessage());
            responseWriter.setResult(new ResponseEntity<>(t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));

        }
    }
}
