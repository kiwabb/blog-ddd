package com.jackmouse.system.iot.divice.ports.input.service;

import com.jackmouse.system.iot.divice.dto.query.*;
import com.jackmouse.system.response.PageResult;

/**
 * @ClassName DeviceProfileApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:00
 * @Version 1.0
 **/
public interface DeviceApplicationService {
    PageResult<DeviceResponse> getDevices(DeviceQuery query);
    PageResult<DeviceInfoResponse> getDeviceInfos(DeviceQuery query);
}
