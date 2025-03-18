package com.jackmouse.system.system.dataaccess.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

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
public class SysRoleEntity extends BaseEntity {
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
    @Builder.Default
    private Boolean enabled = true;

    @Version
    private Long version;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;
}
