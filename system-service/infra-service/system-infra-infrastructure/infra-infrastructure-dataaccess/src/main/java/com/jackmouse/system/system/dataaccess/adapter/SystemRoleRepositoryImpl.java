package com.jackmouse.system.system.dataaccess.adapter;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.dataaccess.entity.SysRoleEntity;
import com.jackmouse.system.system.dataaccess.mapper.RoleMenuDataAccessMapper;
import com.jackmouse.system.system.dataaccess.repositooy.RoleJpaRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemRoleRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.RolePageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName SystemRoleRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 11:00
 * @Version 1.0
 **/
@Component
public class SystemRoleRepositoryImpl implements SystemRoleRepository {
    private final RoleJpaRepository roleJpaRepository;
    private final RoleMenuDataAccessMapper roleDataAccessMapper;

    public SystemRoleRepositoryImpl(RoleJpaRepository roleJpaRepository, RoleMenuDataAccessMapper roleDataAccessMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.roleDataAccessMapper = roleDataAccessMapper;
    }

    @Override
    public PageResult<Role> findPage(RolePageQuerySpec query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        Specification<SysRoleEntity> specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName().value())) {
                predicates.add(cb.like(root.get("name"), "%" + query.getName().value() + "%"));
            }
            if (StringUtils.hasText(query.getCode().value())) {
                predicates.add(cb.like(root.get("code"), "%" + query.getCode().value() + "%"));
            }
            if (query.getEnabled().value() != null) {
                predicates.add(cb.equal(root.get("enabled"), query.getEnabled().value()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<SysRoleEntity> rolePage = roleJpaRepository.findAll(specification, pageable);
        return new PageResult<>(rolePage.getContent().stream().map(roleDataAccessMapper::roleEntityToRole).toList(),
                rolePage.getTotalElements(),
                rolePage.getNumber() + 1,
                rolePage.getTotalPages());
    }

    @Override
    public Optional<Role> findById(RoleId roleId) {
        return roleJpaRepository.findById(roleId.getValue()).map(roleDataAccessMapper::roleEntityToRole);
    }

    @Override
    public void save(Role role) {
        SysRoleEntity roleEntity = roleDataAccessMapper.roleToRoleEntity(role);
        roleJpaRepository.save(roleEntity);

    }

    @Override
    public void remove(List<Role> roles) {
        List<Long> roleIds = roles.stream()
                .map(role -> role.getId().getValue())
                .toList();
        roleJpaRepository.deleteAllById(roleIds);
    }
}
