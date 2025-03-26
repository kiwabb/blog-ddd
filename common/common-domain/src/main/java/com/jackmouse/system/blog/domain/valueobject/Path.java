package com.jackmouse.system.blog.domain.valueobject;

import java.util.UUID;

/**
 * @ClassName Path
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:02
 * @Version 1.0
 **/
public class Path {
    private static final String ROOT_PREFIX = "root";
    private final String path;

    public Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static Path createRootPath() {
        return new Path(ROOT_PREFIX);
    }

    public Path createChildPath(UUID id) {
        return new Path(path + "." + id);
    }
}
