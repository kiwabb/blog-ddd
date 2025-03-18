package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.blog.domain.valueobject.Sex;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName User
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:28
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE system.sys_user SET is_deleted = true WHERE id = ?  AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
@Table(name = "sys_user", schema = "system")
public class SysUserEntity extends BaseEntity {
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
    @Column(nullable = false)
    private Sex sex;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.NORMAL;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId = 1L;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<SysRoleUserEntity> roles = new HashSet<>();

    @Version
    private Long version;
}
