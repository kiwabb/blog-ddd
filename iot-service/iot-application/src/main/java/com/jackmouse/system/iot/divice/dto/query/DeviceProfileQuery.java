package com.jackmouse.system.iot.divice.dto.query;

import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.dto.query.PageQuery;
import com.jackmouse.system.iot.device.specification.query.DeviceProfileQuerySpec;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName DeviceProfileQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 13:56
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceProfileQuery extends PageQuery {
    private String name;

    public DeviceProfileQuerySpec toSpec() {
        return DeviceProfileQuerySpec.builder()
                .name(new DeviceProfileName(name))
                .pageParam(new PageParam(getCurrent(), getPageSize(), this))
                .build();
    }
}
