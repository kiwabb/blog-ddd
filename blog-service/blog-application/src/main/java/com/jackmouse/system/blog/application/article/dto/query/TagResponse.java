package com.jackmouse.system.blog.application.article.dto.query;

import com.jackmouse.system.blog.domain.article.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @ClassName Tag
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 15:19
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class TagResponse {
    private Long tagId;
    private String tagName;

    public static List<TagResponse> fromTagList(List<Tag> tags) {
        return tags.stream().map(TagResponse::fromTag).toList();
    }
    public static TagResponse fromTag(Tag tag) {
        return TagResponse.builder()
                .tagId(tag.getId().getValue())
                .tagName(tag.getName().value())
                .build();
    }
}
