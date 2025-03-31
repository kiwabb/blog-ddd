package com.jackmouse.system.iot.device.adapter;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.repository.DeviceInfoJpaRepository;
import com.jackmouse.system.iot.device.repository.DeviceJpaRepository;
import com.jackmouse.system.iot.device.repository.DeviceRepository;
import com.jackmouse.system.iot.device.specification.query.DeviceQuerySpec;
import com.jackmouse.system.iot.device.valueobject.DeviceInfo;
import com.jackmouse.system.utils.RepositoryUtil;
import org.springframework.stereotype.Component;

/**
 * @ClassName DeviceRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 17:25
 * @Version 1.0
 **/
@Component
public class DeviceRepositoryImpl implements DeviceRepository {
    private final DeviceJpaRepository deviceJpaRepository;
    private final DeviceInfoJpaRepository deviceInfoJpaRepository;

    public DeviceRepositoryImpl(DeviceJpaRepository deviceJpaRepository,
                                DeviceInfoJpaRepository deviceInfoJpaRepository) {
        this.deviceJpaRepository = deviceJpaRepository;
        this.deviceInfoJpaRepository = deviceInfoJpaRepository;
    }

    @Override
    public PageResult<Device> findDevices(DeviceQuerySpec spec) {
        return RepositoryUtil.toPageData(deviceJpaRepository.findByNameLike(spec.getName(),
                RepositoryUtil.toPageable(spec)));
    }

    @Override
    public PageResult<DeviceInfo> findDeviceInfos(DeviceQuerySpec spec) {
        return RepositoryUtil.toPageData(deviceInfoJpaRepository.findByNameLike(spec.getName(),
                RepositoryUtil.toPageable(spec)));
    }
}
