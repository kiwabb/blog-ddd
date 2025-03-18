package com.jackmouse.system.system.dataaccess.adapter;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.dataaccess.mapper.UserDataAccessMapper;
import com.jackmouse.system.system.dataaccess.entity.SysUserEntity;
import com.jackmouse.system.system.dataaccess.repositooy.UserJpaRepository;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.repository.SystemUserRepository;
import com.jackmouse.system.system.infra.domain.user.specification.query.UserPageQuerySpec;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserId;
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
 * @ClassName UserRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 15:55
 * @Version 1.0
 **/
@Component
public class SystemUserRepositoryImpl implements SystemUserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserDataAccessMapper userDataAccessMapper;

    public SystemUserRepositoryImpl(UserJpaRepository userJpaRepository, UserDataAccessMapper userDataAccessMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userDataAccessMapper = userDataAccessMapper;
    }

    @Override
    public PageResult<User> findPage(UserPageQuerySpec query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        Specification<SysUserEntity> specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getUsername().value())) {
                predicates.add(cb.like(root.get("username"), "%" + query.getUsername().value() + "%"));
            }
            if (StringUtils.hasText(query.getMobile().value())) {
                predicates.add(cb.like(root.get("phone"), "%" + query.getMobile().value() + "%"));
            }
            if (StringUtils.hasText(query.getEmail().value())) {
                predicates.add(cb.like(root.get("email"), "%" + query.getEmail().value() + "%"));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (query.getUserType() != null) {
                predicates.add(cb.equal(root.get("userType"), query.getUserType()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<SysUserEntity> userPage = userJpaRepository.findAll(specification, pageable);

        return new PageResult<>(userPage.getContent().stream().map(userDataAccessMapper::userEntityToUser).toList(),
                userPage.getTotalElements(),
                userPage.getNumber() + 1,
                userPage.getTotalPages());
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue()).map(userDataAccessMapper::userEntityToUser);
    }

    @Override
    public void save(User user) {
        SysUserEntity entity;
        if (user.getId() != null) {
            entity = userDataAccessMapper.userToUpdateUserEntity(user);
            userJpaRepository.findById(user.getId().getValue()).ifPresent(sysUserEntity -> {
                entity.setPassword(sysUserEntity.getPassword());
            });
        } else {
            entity = userDataAccessMapper.userToCreateUserEntity(user);
        }
        userJpaRepository.save(entity);
    }

    @Override
    public void remove(List<User> users) {
        List<Long> userIds = users.stream()
                .map(user -> user.getId().getValue())
                .toList();
        userJpaRepository.deleteAllById(userIds);
    }
}
