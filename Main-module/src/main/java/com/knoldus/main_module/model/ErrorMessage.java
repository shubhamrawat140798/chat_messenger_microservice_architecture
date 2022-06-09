package com.knoldus.main_module.model;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessage {
    private String searchFormErrorMessage;

    public String getSearchFormErrorMessage() {
        return searchFormErrorMessage;
    }

    public void setSearchFormErrorMessage(String searchFormErrorMessage) {
        this.searchFormErrorMessage = searchFormErrorMessage;
    }
}
