package com.jackmouse.system.blog.domain.valueobject;

/**
 * @ClassName Password
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 09:10
 * @Version 1.0
 **/
public class Password {
    private final String password;
    public Password(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
