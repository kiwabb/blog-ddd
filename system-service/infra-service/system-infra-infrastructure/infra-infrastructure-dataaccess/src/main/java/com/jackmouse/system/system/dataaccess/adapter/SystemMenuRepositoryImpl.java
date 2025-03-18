package com.jackmouse.system.system.dataaccess.adapter;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.dataaccess.entity.SysMenuEntity;
import com.jackmouse.system.system.dataaccess.mapper.RoleMenuDataAccessMapper;
import com.jackmouse.system.system.dataaccess.repositooy.MenuJpaRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemMenuRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.MenuPageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuId;
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
 * @ClassName SystemMenuRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:59
 * @Version 1.0
 **/
@Component
public class SystemMenuRepositoryImpl implements SystemMenuRepository {
    private final MenuJpaRepository menuJpaRepository;
    private final RoleMenuDataAccessMapper roleDataAccessMapper;
    public SystemMenuRepositoryImpl(MenuJpaRepository menuJpaRepository, RoleMenuDataAccessMapper roleDataAccessMapper) {
        this.menuJpaRepository = menuJpaRepository;
        this.roleDataAccessMapper = roleDataAccessMapper;
    }

    @Override
    public PageResult<Menu> findPage(MenuPageQuerySpec query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        Specification<SysMenuEntity> specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getMenuName().value())) {
                predicates.add(cb.like(root.get("menuName"), "%" + query.getMenuName().value() + "%"));
            }
            if (query.getMenuType() != null) {
                predicates.add(cb.equal(root.get("menuType"), query.getMenuType()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<SysMenuEntity> rolePage = menuJpaRepository.findAll(specification, pageable);
        return new PageResult<>(rolePage.getContent().stream().map(roleDataAccessMapper::menuEntityToMenu).toList(),
                rolePage.getTotalElements(),
                rolePage.getNumber() + 1,
                rolePage.getTotalPages());
    }

    @Override
    public Optional<Menu> findById(MenuId roleId) {
        return menuJpaRepository.findById(roleId.getValue()).map(roleDataAccessMapper::menuEntityToMenu);
    }

    @Override
    public void save(Menu menu) {
        menuJpaRepository.save(roleDataAccessMapper.menuToMenuEntity(menu));
    }

    @Override
    public void remove(List<Menu> menu) {
        List<Long> roleIds = menu.stream()
                .map(role -> role.getId().getValue())
                .toList();
        menuJpaRepository.deleteAllById(roleIds);
    }
}
