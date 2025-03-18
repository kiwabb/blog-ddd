package com.jackmouse.system.system.dataaccess.entity;

import com.jackmouse.system.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    })
//    private SysUserEntity user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    })
//    private SysRoleEntity role;

    @Version
    private Long version;
}
