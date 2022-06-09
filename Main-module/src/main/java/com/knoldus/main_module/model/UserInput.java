package com.knoldus.main_module.model;

import org.springframework.stereotype.Component;

@Component
public class UserInput {
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
