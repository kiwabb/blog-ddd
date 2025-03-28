package com.jackmouse.system.iot.device.adapter;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.repository.DeviceProfileJpaRepository;
import com.jackmouse.system.iot.device.repository.DeviceProfileRepository;
import com.jackmouse.system.iot.device.specification.query.DeviceProfileQuerySpec;
import com.jackmouse.system.utils.RepositoryUtil;
import org.springframework.stereotype.Component;

/**
 * @ClassName DeviceProfileRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:25
 * @Version 1.0
 **/
@Component
public class DeviceProfileRepositoryImpl implements DeviceProfileRepository {
    private final DeviceProfileJpaRepository deviceProfileJpaRepository;

    public DeviceProfileRepositoryImpl(DeviceProfileJpaRepository deviceProfileJpaRepository) {
        this.deviceProfileJpaRepository = deviceProfileJpaRepository;
    }

    @Override
    public PageResult<DeviceProfile> findDeviceProfiles(DeviceProfileQuerySpec spec) {
        return RepositoryUtil.toPageData(deviceProfileJpaRepository.findByNameLike(spec.getName().value(),
                RepositoryUtil.toPageable(spec)));
    }
}
