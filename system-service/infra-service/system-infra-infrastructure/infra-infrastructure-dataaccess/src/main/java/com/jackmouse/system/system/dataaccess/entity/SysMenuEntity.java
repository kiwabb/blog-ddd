package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.system.infra.domain.menu.valueobject.MenuCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SysMenu
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 16:51
 * @Version 1.0
 **/
@Entity
@Table(name = "sys_menu", schema = "system")
@Where(clause = "is_deleted = false")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(length = 64)
    private String name;

    @Column(length = 256)
    private String path;

    @Column(length = 256)
    private String component;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuCategory type;

    @Column(nullable = false)
    private Boolean hidden = false;

    @Column(length = 32)
    private String icon;

    @Column(nullable = false)
    private Integer sort;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "component_name", length = 100)
    private String componentName;

//    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<SysRoleMenuEntity> roles = new HashSet<>();
    @Version
    private Long version;
}
