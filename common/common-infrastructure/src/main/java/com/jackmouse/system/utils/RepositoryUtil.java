package com.jackmouse.system.utils;

import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.entity.ToData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * @ClassName RepositoryUtil
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:32
 * @Version 1.0
 **/
public class RepositoryUtil {
    private RepositoryUtil() {
    }
    protected static final String DEFAULT_SORT_PROPERTY = "id";
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, DEFAULT_SORT_PROPERTY);
    public static <T> PageResult<T> toPageData(Page<? extends ToData<T>> page) {
        List<T> data = convertDataList(page.getContent());
        return new PageResult<>(data, page.getTotalElements(), page.getNumber() + 1, page.getTotalPages());
    }

    public static <T> List<T> convertDataList(Collection<? extends ToData<T>> toDataList) {
        List<T> list = Collections.emptyList();
        if (toDataList != null && !toDataList.isEmpty()) {
            list = new ArrayList<>();
            for (ToData<T> object : toDataList) {
                if (object != null) {
                    list.add(object.toData());
                }
            }
        }
        return list;
    }

    public static Pageable toPageable(PageSpec spec) {
        return toPageable(spec, true);
    }

    public static Pageable toPageable(PageSpec spec, boolean addDefaultSorting) {
        return toPageable(spec, Collections.emptyMap(), addDefaultSorting);
    }

    public static Pageable toPageable(PageSpec spec, Map<String, String> columnMap, boolean addDefaultSorting) {

        return PageRequest.of(spec.getPage() - 1, spec.getSize(),
                spec.getSort().getSortField() == null ? DEFAULT_SORT :
                        Sort.by(spec.getSort().getSortField(), spec.getSort().getDirection().name())
                );
    }
}
