package com.jackmouse.system.iot.rest;

import com.jackmouse.system.iot.divice.dto.query.DeviceInfoResponse;
import com.jackmouse.system.iot.divice.dto.query.DeviceQuery;
import com.jackmouse.system.iot.divice.dto.query.DeviceResponse;
import com.jackmouse.system.iot.divice.dto.save.SaveDeviceCommand;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceApplicationService;
import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DeviceController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 13:38
 * @Version 1.0
 **/
@Tag(name = "设备接口", description = "设备接口")
@RestController
@RequestMapping("/admin/iot/devices")
public class DeviceController {
    private final DeviceApplicationService deviceApplicationService;
    public DeviceController(DeviceApplicationService deviceApplicationService) {
        this.deviceApplicationService = deviceApplicationService;
    }
    @Operation(summary = "获取设备列表")
    @GetMapping
    public PageResult<DeviceResponse> getDevices(DeviceQuery query) {
        return deviceApplicationService.getDevices(query);
    }

    @Operation(summary = "获取设备信息列表")
    @GetMapping("/infos")
    public PageResult<DeviceInfoResponse> getDeviceInfos(DeviceQuery query) {
        return deviceApplicationService.getDeviceInfos(query);
    }

    @Operation(summary = "保存设备")
    @PostMapping("")
    public Result<Void> saveDevice(SaveDeviceCommand command,
                                   @RequestParam(value = "accessToken", required = false) String accessToken) {
        deviceApplicationService.saveDevice(command, accessToken);
        return Result.succeed(null);
    }

}
