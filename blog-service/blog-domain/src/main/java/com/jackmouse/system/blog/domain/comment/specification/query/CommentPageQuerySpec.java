package com.jackmouse.system.blog.domain.comment.specification.query;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.specification.SortSpec;
import com.jackmouse.system.blog.domain.valueobject.Depth;
import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.blog.domain.valueobject.PageParam;

import java.util.function.Predicate;

/**
 * @ClassName ArticlePageQuerySpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:19
 * @Version 1.0
 **/
public class CommentPageQuerySpec implements PageSpec<Comment> {
    private final TargetId targetId;
    private final Depth depth;
    private final PageParam pageParam;

    public TargetId getTargetId() {
        return targetId;
    }

    public Depth getDepth() {
        return depth;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    private CommentPageQuerySpec(Builder builder) {
        targetId = builder.targetId;
        depth = builder.depth;
        pageParam = builder.pageParam;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int getPage() {
        return pageParam.page();
    }

    @Override
    public int getSize() {
        return pageParam.size();
    }

    @Override
    public SortSpec getSort() {
        return pageParam.sort();
    }

    @Override
    public Predicate<Comment> getPredicate() {
        return null;
    }

    public static final class Builder {
        private PageParam pageParam;
        private TargetId targetId;
        private Depth depth;

        private Builder() {
        }


        public Builder pageParam(PageParam val) {
            pageParam = val;
            return this;
        }

        public CommentPageQuerySpec build() {
            return new CommentPageQuerySpec(this);
        }

        public Builder targetId(TargetId val) {
            targetId = val;
            return this;
        }

        public Builder depth(Depth val) {
            depth = val;
            return this;
        }
    }
}
