package com.jackmouse.system.blog.domain.interaction.valueobject;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * @ClassName InteractionStatus
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:16
 * @Version 1.0
 **/
public class InteractionStatus {
    private final boolean active;
    private OffsetDateTime lastModified;

    public InteractionStatus(boolean active) {
        this.active = active;
    }

    public InteractionStatus(boolean active, OffsetDateTime lastModified) {
        this.active = active;
        this.lastModified = lastModified;
    }

    public boolean isActive() { return active; }
    public OffsetDateTime getLastModified() { return lastModified; }

}
