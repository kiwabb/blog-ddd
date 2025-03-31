package com.jackmouse.system.iot.device.specification.query;

import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.specification.SortSpec;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileName;

/**
 * @ClassName DeviceQuerySpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 17:17
 * @Version 1.0
 **/
public class DeviceQuerySpec implements PageSpec {
    private final String name;
    private final String type;
    private final PageParam pageParam;


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    private DeviceQuerySpec(Builder builder) {
        name = builder.name;
        type = builder.type;
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
        private String name;
        private String type;

        private Builder() {
        }

        public Builder pageParam(PageParam val) {
            pageParam = val;
            return this;
        }

        public DeviceQuerySpec build() {
            return new DeviceQuerySpec(this);
        }


        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }
    }
}
