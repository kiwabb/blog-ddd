package com.jackmouse.system.system.infra.domain.rolemenu.entity;

import com.jackmouse.system.blog.domain.entity.BaseEntity;
import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuComponent;

import java.awt.*;


/**
 * @ClassName Menu
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:20
 * @Version 1.0
 **/
public class Menu extends BaseEntity<MenuId> {
    private final MenuId parentId;
    private final MenuName name;
    private final MenuPath path;
    private final MenuComponent component;
    private final MenuType type;
    private final MenuIcon icon;
    private final MenuSort sort;
    private final Version version;
    private final CreatedAt createdAt;
    private final CreatedBy createdBy;
    private final UpdatedAt updatedAt;
    private final UpdatedBy updatedBy;
    private Checked menuChecked;
    private MenuHidden hidden;

    private Menu(Builder builder) {
        setId(builder.menuId);
        hidden = builder.hidden;
        menuChecked = builder.menuChecked;
        updatedBy = builder.updatedBy;
        updatedAt = builder.updatedAt;
        createdBy = builder.createdBy;
        createdAt = builder.createdAt;
        version = builder.version;
        sort = builder.sort;
        icon = builder.icon;
        type = builder.type;
        component = builder.component;
        path = builder.path;
        name = builder.name;
        parentId = builder.parentId;
    }

    public static Builder builder() {
        return new Builder();
    }
    public void setChecked() {
        menuChecked = new Checked(true);
    }

    public Checked getMenuChecked() {
        return menuChecked;
    }
    public MenuId getParentId() {
        return parentId;
    }

    public MenuName getName() {
        return name;
    }

    public MenuPath getPath() {
        return path;
    }

    public MenuComponent getComponent() {
        return component;
    }

    public MenuType getType() {
        return type;
    }

    public MenuIcon getIcon() {
        return icon;
    }

    public MenuSort getSort() {
        return sort;
    }


    public Version getVersion() {
        return version;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public UpdatedBy getUpdatedBy() {
        return updatedBy;
    }

    public MenuHidden getHidden() {
        return hidden;
    }

    public static final class Builder {
        private MenuId menuId;
        private MenuId parentId;
        private MenuName name;
        private MenuPath path;
        private MenuComponent component;
        private MenuType type;
        private MenuIcon icon;
        private MenuSort sort;
        private Version version;
        private CreatedAt createdAt;
        private CreatedBy createdBy;
        private UpdatedAt updatedAt;
        private UpdatedBy updatedBy;
        private MenuHidden hidden;
        private Checked menuChecked = new Checked(false);

        private Builder() {
        }

        public Builder id(MenuId val) {
            menuId = val;
            return this;
        }

        public Builder parentId(MenuId val) {
            parentId = val;
            return this;
        }

        public Builder name(MenuName val) {
            name = val;
            return this;
        }

        public Builder path(MenuPath val) {
            path = val;
            return this;
        }

        public Builder component(MenuComponent val) {
            component = val;
            return this;
        }

        public Builder type(MenuType val) {
            type = val;
            return this;
        }

        public Builder icon(MenuIcon val) {
            icon = val;
            return this;
        }

        public Builder sort(MenuSort val) {
            sort = val;
            return this;
        }


        public Builder version(Version val) {
            version = val;
            return this;
        }

        public Builder createdAt(CreatedAt val) {
            createdAt = val;
            return this;
        }

        public Builder createdBy(CreatedBy val) {
            createdBy = val;
            return this;
        }

        public Builder updatedAt(UpdatedAt val) {
            updatedAt = val;
            return this;
        }

        public Builder updatedBy(UpdatedBy val) {
            updatedBy = val;
            return this;
        }

        public Builder hidden(MenuHidden val) {
            hidden = val;
            return this;
        }

        public Builder menuChecked(Checked val) {
            menuChecked = val;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
