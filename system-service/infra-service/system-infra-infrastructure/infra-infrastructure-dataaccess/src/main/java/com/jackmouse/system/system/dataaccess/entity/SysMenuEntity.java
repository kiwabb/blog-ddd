package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.blog.domain.valueobject.CreatedAt;
import com.jackmouse.system.blog.domain.valueobject.CreatedBy;
import com.jackmouse.system.blog.domain.valueobject.UpdatedAt;
import com.jackmouse.system.blog.domain.valueobject.UpdatedBy;
import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.entity.ToData;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysMenuEntity extends BaseEntity implements ToData<Menu> {
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
    private MenuType type;

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

    @OneToMany(mappedBy = "menu")
    private Set<SysRoleMenuEntity> roleMenus;

    @Version
    private Long version;

    @Override
    public Menu toData() {
        return Menu.builder()
                .id(new MenuId(getId()))
                .parentId(new MenuId(getParentId()))
                .name(new MenuName(getName()))
                .path(new MenuPath(getPath()))
                .component(new MenuComponent(getComponent(), getComponentName()))
                .hidden(new MenuHidden(getHidden()))
                .icon(new MenuIcon(getIcon()))
                .sort(new MenuSort(getSort()))
                .hidden(new MenuHidden(getHidden()))
                .version(new com.jackmouse.system.blog.domain.valueobject.Version(getVersion()))
                .createdAt(new CreatedAt(getCreatedAt()))
                .createdBy(new CreatedBy(getCreatedBy()))
                .updatedAt(new UpdatedAt(getUpdatedAt()))
                .updatedBy(new UpdatedBy(getUpdatedBy()))
                .type(getType())
                .build();
    }
}
