package com.jackmouse.system.iot.device.specification.query;

import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.specification.SortSpec;
import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileName;

import java.util.function.Predicate;

/**
 * @ClassName ArticlePageQuerySpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:19
 * @Version 1.0
 **/
public class DeviceProfileQuerySpec implements PageSpec {

    private final DeviceProfileName name;
    private final PageParam pageParam;


    public DeviceProfileName getName() {
        return name;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    private DeviceProfileQuerySpec(Builder builder) {
        name = builder.name;
        pageParam = builder.pageParam;
    }


    public static Builder builder() {
        return new Builder();
    }


    @Override
    public int getPage() {
        return pageParam.page();
    }

    @Override
    public int getSize() {
        return pageParam.size();
    }

    @Override
    public SortSpec getSort() {
        return pageParam.sort();
    }


    public static final class Builder {
        private PageParam pageParam;
        private DeviceProfileName name;

        private Builder() {
        }

        public Builder pageParam(PageParam val) {
            pageParam = val;
            return this;
        }

        public DeviceProfileQuerySpec build() {
            return new DeviceProfileQuerySpec(this);
        }


        public Builder name(DeviceProfileName val) {
            name = val;
            return this;
        }
    }
}
