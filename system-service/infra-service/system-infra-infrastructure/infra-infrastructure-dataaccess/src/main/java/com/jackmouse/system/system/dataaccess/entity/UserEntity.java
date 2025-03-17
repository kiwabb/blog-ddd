package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.blog.domain.valueobject.Sex;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:28
 * @Version 1.0
 **/
@Data
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "sys_user", schema = "system")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 255)
    private String nickname;

    @Column(length = 1024)
    private String avatar;

    @Column(length = 255)
    private String phone;

    @Column(length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "sex")
    private Sex sex;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "user_type")
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "user_status")
    private UserStatus status = UserStatus.NORMAL;

    private LocalDateTime createdAt;
    private Long createBy;
    private LocalDateTime updatedAt;
    private Long updateBy;
    @Version
    private Long version;

    private Long tenantId;

    private Boolean isDeleted;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
