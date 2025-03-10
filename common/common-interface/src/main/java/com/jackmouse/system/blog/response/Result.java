package com.jackmouse.system.blog.response;


import com.jackmouse.system.blog.enums.CodeEnum;
import com.jackmouse.system.blog.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    @Schema(description = "数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;
    private Integer errorCode;
    private String errorMessage;
    private Boolean success;
    private Integer showType;

    public static <T> Result<T> succeed(T model) {
        return of(model);
    }

    public static <T> Result<T> of(T datas) {
        return new Result<>(datas, null, null,true, CodeEnum.SUCCESS.getCode());
    }

    public static <T> Result<T> of(ErrorCode errorEnum) {
        return new Result<>(null, errorEnum.getCode(), errorEnum.getMsg(),true, CodeEnum.SUCCESS.getCode());
    }

    public static <T> Result<T> failed(ErrorCode error) {
        return of(error);
    }
}
