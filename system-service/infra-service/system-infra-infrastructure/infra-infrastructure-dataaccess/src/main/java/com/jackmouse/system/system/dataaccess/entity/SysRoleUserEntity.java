package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

/**
 * @ClassName SysRoleUser
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 16:50
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role_user", schema = "system",
        indexes = {
                @Index(name = "idx_role_user_tenant", columnList = "tenant_id"),
                @Index(name = "idx_user_role", columnList = "user_id")
        },
        uniqueConstraints = @UniqueConstraint(name = "uniq_user_role",
                columnNames = {"user_id", "role_id", "tenant_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleUserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private SysUserEntity user;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "tenant_id", nullable = false)
    @Builder.Default
    private Long tenantId = 0L;

    @Version
    private Long version;

    public static List<SysRoleUserEntity> from(Role role) {
        return role.getUsers().stream().map(m -> SysRoleUserEntity.builder()
                .userId(m.getId().getValue())
                .roleId(role.getId().getValue())
                .build()).toList();
    }
}
