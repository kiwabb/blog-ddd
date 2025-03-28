package com.jackmouse.system.iot.rest;

import com.jackmouse.system.iot.divice.dto.query.DeviceProfileQuery;
import com.jackmouse.system.iot.divice.dto.query.DeviceProfileResponse;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceProfileApplicationService;
import com.jackmouse.system.response.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeviceProfileController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 13:48
 * @Version 1.0
 **/
@Tag(name = "设备配置接口", description = "设备配置接口")
@RestController
@RequestMapping("/admin/iot/device-profiles")
public class DeviceProfileController {

    private final DeviceProfileApplicationService deviceProfileApplicationService;

    public DeviceProfileController(DeviceProfileApplicationService deviceProfileApplicationService) {
        this.deviceProfileApplicationService = deviceProfileApplicationService;
    }

    @Operation(
            summary = "获取设备配置列表",
            description = "获取设备配置列表"
    )
    @GetMapping
    public PageResult<DeviceProfileResponse> getDeviceProfiles(DeviceProfileQuery query) {
        return deviceProfileApplicationService.getDeviceProfiles(query);
    }
}
