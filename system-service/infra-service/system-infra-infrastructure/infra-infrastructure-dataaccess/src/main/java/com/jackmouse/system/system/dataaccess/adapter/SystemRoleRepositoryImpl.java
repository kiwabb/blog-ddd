package com.jackmouse.system.system.dataaccess.adapter;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.dataaccess.entity.SysMenuEntity;
import com.jackmouse.system.system.dataaccess.entity.SysRoleEntity;
import com.jackmouse.system.system.dataaccess.entity.SysRoleMenuEntity;
import com.jackmouse.system.system.dataaccess.entity.SysRoleUserEntity;
import com.jackmouse.system.system.dataaccess.mapper.RoleMenuDataAccessMapper;
import com.jackmouse.system.system.dataaccess.repositooy.MenuJpaRepository;
import com.jackmouse.system.system.dataaccess.repositooy.RoleJpaRepository;
import com.jackmouse.system.system.dataaccess.repositooy.RoleMenuJpaRepository;
import com.jackmouse.system.system.dataaccess.repositooy.RoleUserJpaRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemRoleRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.RolePageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;
import com.jackmouse.system.utils.RepositoryUtil;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    private final MenuJpaRepository menuJpaRepository;
    private final RoleMenuJpaRepository roleMenuJpaRepository;
    private final RoleUserJpaRepository roleUserJpaRepository;
    private final RoleMenuDataAccessMapper roleDataAccessMapper;

    public SystemRoleRepositoryImpl(RoleJpaRepository roleJpaRepository, MenuJpaRepository menuJpaRepository, RoleMenuJpaRepository roleMenuJpaRepository, RoleUserJpaRepository roleUserJpaRepository, RoleMenuDataAccessMapper roleDataAccessMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.menuJpaRepository = menuJpaRepository;
        this.roleMenuJpaRepository = roleMenuJpaRepository;
        this.roleUserJpaRepository = roleUserJpaRepository;
        this.roleDataAccessMapper = roleDataAccessMapper;
    }

    @Override
    public PageResult<Role> findPage(RolePageQuerySpec query) {
        Pageable pageable = RepositoryUtil.toPageable(query);
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
        return RepositoryUtil.toPageData(roleJpaRepository.findAll(specification, pageable));
    }

    @Override
    public Optional<Role> findById(RoleId roleId) {
        return roleJpaRepository.findById(roleId.getValue()).map(SysRoleEntity::toData);
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

    @Override
    public void assignMenu(Role role) {
        // 获取角色实体（新增关键代码）
        SysRoleEntity sysRoleEntity = roleJpaRepository.findById(role.getId().getValue())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 获取现有关联
        List<SysRoleMenuEntity> existing = roleMenuJpaRepository.findByRoleId(role.getId().getValue());

        // 计算需要删除的旧关联
        Set<Long> newMenuIds = role.getMenus().stream()
                .map(m -> m.getId().getValue())
                .collect(Collectors.toSet());

        List<SysRoleMenuEntity> toDelete = existing.stream()
                .filter(e -> !newMenuIds.contains(e.getMenu().getId()))
                .toList();

        // 计算需要新增的关联（使用上面获取的sysRoleEntity）
        Set<Long> existingMenuIds = existing.stream()
                .map(e -> e.getMenu().getId())
                .collect(Collectors.toSet());


        List<SysRoleMenuEntity> toAdd = role.getMenus().stream()
                .filter(m -> !existingMenuIds.contains(m.getId().getValue()))
                .map(m -> new SysRoleMenuEntity(sysRoleEntity, menuJpaRepository.findById(m.getId().getValue()).orElseThrow(() -> new RuntimeException("Menu not found"))))
                .toList();

        // 执行批量操作
        roleMenuJpaRepository.deleteAll(toDelete);
        roleMenuJpaRepository.saveAll(toAdd);
    }

    @Override
    public void assignUser(Role role) {
        roleUserJpaRepository.saveAll(SysRoleUserEntity.from(role));
    }
}
