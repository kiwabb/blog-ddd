package com.jackmouse.system.iot.divice;

import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.repository.DeviceRepository;
import com.jackmouse.system.iot.device.valueobject.DeviceInfo;
import com.jackmouse.system.iot.divice.dto.query.*;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceApplicationService;
import com.jackmouse.system.response.PageResult;
import org.springframework.stereotype.Service;

/**
 * @ClassName DeviceApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 09:36
 * @Version 1.0
 **/
@Service
public class DeviceApplicationServiceImpl implements DeviceApplicationService {
    private final DeviceRepository deviceRepository;

    public DeviceApplicationServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public PageResult<DeviceResponse> getDevices(DeviceQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<Device> page = deviceRepository.findDevices(query.toSpec());
        return new PageResult<>(page.total(), page.currentPage(), page.data().stream().map(DeviceResponse::from).toList());
    }

    @Override
    public PageResult<DeviceInfoResponse> getDeviceInfos(DeviceQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<DeviceInfo> page = deviceRepository.findDeviceInfos(query.toSpec());
        return new PageResult<>(page.total(), page.currentPage(), page.data().stream().map(DeviceInfoResponse::from).toList());
    }
}
