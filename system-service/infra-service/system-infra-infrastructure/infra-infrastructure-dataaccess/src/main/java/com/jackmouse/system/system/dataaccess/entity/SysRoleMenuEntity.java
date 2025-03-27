package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @ClassName SysRoleMenu
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 16:53
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role_menu", schema = "system",
        indexes = {
                @Index(name = "idx_role_menu_tenant", columnList = "tenant_id"),
                @Index(name = "idx_menu_role", columnList = "menu_id")
        },
        uniqueConstraints = @UniqueConstraint(name = "uniq_role_menu",
                columnNames = {"role_id", "menu_id", "tenant_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleMenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private SysRoleEntity role;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false) // 外键列
    private SysMenuEntity menu;  //

    @Column(name = "tenant_id", nullable = false)
    @Builder.Default
    private Long tenantId = 0L;

    @Version
    private Long version;

    public SysRoleMenuEntity(SysRoleEntity sysRoleEntity, SysMenuEntity byId) {
        this.role = sysRoleEntity;
        this.menu = byId;
        this.tenantId = 1L;
    }

}
