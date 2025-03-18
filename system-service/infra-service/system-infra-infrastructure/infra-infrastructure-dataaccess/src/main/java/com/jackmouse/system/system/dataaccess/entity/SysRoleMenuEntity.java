package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    })
//    private SysRoleEntity role;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns(value = {
//            @JoinColumn(name = "menu_id", referencedColumnName = "id"),
//            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", insertable=false, updatable=false)
//    })
//    private SysMenuEntity menu;

    @Version
    private Long version;
}
