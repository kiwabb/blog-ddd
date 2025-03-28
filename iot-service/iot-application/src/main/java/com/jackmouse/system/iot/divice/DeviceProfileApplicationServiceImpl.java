package com.jackmouse.system.iot.divice;

import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.repository.DeviceProfileRepository;
import com.jackmouse.system.iot.divice.dto.query.DeviceProfileQuery;
import com.jackmouse.system.iot.divice.dto.query.DeviceProfileResponse;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceProfileApplicationService;
import com.jackmouse.system.response.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName DeviceProfileApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:01
 * @Version 1.0
 **/
@Service
public class DeviceProfileApplicationServiceImpl implements DeviceProfileApplicationService {
    private final DeviceProfileRepository deviceProfileRepository;

    public DeviceProfileApplicationServiceImpl(DeviceProfileRepository deviceProfileRepository) {
        this.deviceProfileRepository = deviceProfileRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResult<DeviceProfileResponse> getDeviceProfiles(DeviceProfileQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<DeviceProfile> page = deviceProfileRepository.findDeviceProfiles(query.toSpec());
        return new PageResult<>(page.total(), page.currentPage(), page.data().stream().map(DeviceProfileResponse::from).toList());

    }
}
