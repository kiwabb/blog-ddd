package com.jackmouse.system.iot.divice;

import com.jackmouse.system.iot.device.DeviceDomainService;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.exception.DeviceNotFoundException;
import com.jackmouse.system.iot.device.exception.DeviceProfileNotFoundException;
import com.jackmouse.system.iot.device.repository.DeviceProfileRepository;
import com.jackmouse.system.iot.device.repository.DeviceRepository;
import com.jackmouse.system.iot.device.valueobject.*;
import com.jackmouse.system.iot.divice.dto.query.*;
import com.jackmouse.system.iot.divice.dto.save.SaveDeviceCommand;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceApplicationService;
import com.jackmouse.system.response.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @ClassName DeviceApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 09:36
 * @Version 1.0
 **/
@Slf4j
@Service
public class DeviceApplicationServiceImpl implements DeviceApplicationService {
    private final DeviceRepository deviceRepository;
    private final DeviceProfileRepository deviceProfileRepository;
    private final DeviceDomainService deviceDomainService;

    public DeviceApplicationServiceImpl(DeviceRepository deviceRepository,
                                        DeviceProfileRepository deviceProfileRepository,
                                        DeviceDomainService deviceDomainService) {
        this.deviceRepository = deviceRepository;
        this.deviceProfileRepository = deviceProfileRepository;
        this.deviceDomainService = deviceDomainService;
    }
    @Transactional(readOnly = true)
    @Override
    public PageResult<DeviceResponse> getDevices(DeviceQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<Device> page = deviceRepository.findDevices(query.toSpec());
        return new PageResult<>(page.total(), page.currentPage(), page.data().stream().map(DeviceResponse::from).toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PageResult<DeviceInfoResponse> getDeviceInfos(DeviceQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<DeviceInfo> page = deviceRepository.findDeviceInfos(query.toSpec());
        return new PageResult<>(page.total(), page.currentPage(), page.data().stream().map(DeviceInfoResponse::from).toList());
    }
    @Transactional
    @Override
    public void saveDevice(SaveDeviceCommand command, String accessToken) {
        Device saveDevice = saveDeviceWithoutCredentials(command.toDevice(), true);
        if (command.getId() == null) {
            //TODO 创建设备凭证
        }
    }

    private Device saveDeviceWithoutCredentials(Device device, boolean doValidate) {
        log.trace("Executing saveDevice [{}]", device);
        Device oldDevice = null;
        if (device.getId() != null) {
            oldDevice = findDeviceById(device.getId());
        }
        DeviceProfileId deviceProfileId = device.getDeviceProfileId();
        DeviceProfile deviceProfile = deviceProfileRepository.findDeviceProfileById(deviceProfileId).orElseThrow(
                () -> new DeviceProfileNotFoundException(deviceProfileId.getValue())
        );
        device = deviceDomainService.syncDeviceWithProfile(device, deviceProfile);
        Device savedDevice = deviceRepository.saveAndFlush(device);
        // TODO EventPublisher
        return savedDevice;
    }



    @Override
    public Device findDeviceById(DeviceId deviceId) {
        log.trace("Executing findDeviceById [{}]", deviceId);
        return deviceRepository.findById(deviceId).orElse(null);
    }

    private DeviceData syncDeviceData(DeviceProfile deviceProfile, DeviceData deviceData) {
        if (deviceData == null) {
            deviceData = new DeviceData();
        }
        if (deviceData.getConfiguration() == null || !deviceProfile.getType().equals(deviceData.getConfiguration().getType())) {
            if (deviceProfile.getType().equals(DeviceProfileType.DEFAULT)) {
                deviceData.setConfiguration(new DefaultDeviceConfiguration());
            }
        }
        if (deviceData.getTransportConfiguration() == null ||
                !deviceProfile.getTransportType().equals(deviceData.getTransportConfiguration().getType())) {
            switch (deviceProfile.getTransportType()) {
                case DEFAULT -> deviceData.setTransportConfiguration(new DefaultDeviceTransportConfiguration());
            }
        }

        return deviceData;
    }
}
