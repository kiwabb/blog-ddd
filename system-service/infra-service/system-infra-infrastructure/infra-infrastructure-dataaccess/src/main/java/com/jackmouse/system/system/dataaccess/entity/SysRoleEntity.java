package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.blog.domain.valueobject.CreatedAt;
import com.jackmouse.system.blog.domain.valueobject.CreatedBy;
import com.jackmouse.system.blog.domain.valueobject.UpdatedAt;
import com.jackmouse.system.blog.domain.valueobject.UpdatedBy;
import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.entity.ToData;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;

/**
 * @ClassName SysRoleEntity
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 17:01
 * @Version 1.0
 **/

@Entity
@Table(name = "sys_role", schema = "system",
        indexes = {
                @Index(name = "idx_role_tenant", columnList = "tenant_id"),
                @Index(name = "idx_role_name", columnList = "name")
        },
        uniqueConstraints = @UniqueConstraint(
                name = "uniq_role_code",
                columnNames = {"code", "tenant_id"}
        ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false") // 逻辑删除过滤
public class SysRoleEntity extends BaseEntity implements ToData<Role> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 32, nullable = false)
    private String code;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "data_scope", length = 50, nullable = false)
    private String dataScope;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Version
    private Long version;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToMany
    @JoinTable(
            name = "sys_role_menu",
            schema = "system",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<SysMenuEntity> menuEntities;

    @Override
    public Role toData() {
        return Role.builder()
                .id(new RoleId(getId()))
                .code(new RoleCode(getCode()))
                .name(new RoleName(getName()))
                .enabled(new Enabled(getEnabled()))
                .dataScope(new RoleDataScope(getDataScope()))
                .version(new com.jackmouse.system.blog.domain.valueobject.Version(getVersion()))
                .createdAt(new CreatedAt(getCreatedAt()))
                .createdBy(new CreatedBy(getCreatedBy()))
                .updatedAt(new UpdatedAt(getUpdatedAt()))
                .updatedBy(new UpdatedBy(getUpdatedBy()))
                .build();
    }
}
