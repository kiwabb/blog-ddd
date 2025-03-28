package com.jackmouse.system.iot.divice.ports.input.service;

import com.jackmouse.system.iot.divice.dto.query.DeviceProfileQuery;
import com.jackmouse.system.iot.divice.dto.query.DeviceProfileResponse;
import com.jackmouse.system.response.PageResult;

/**
 * @ClassName DeviceProfileApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:00
 * @Version 1.0
 **/
public interface DeviceProfileApplicationService {
    PageResult<DeviceProfileResponse> getDeviceProfiles(DeviceProfileQuery query);
}
