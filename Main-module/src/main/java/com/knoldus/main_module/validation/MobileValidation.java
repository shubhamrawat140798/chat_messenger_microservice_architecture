package com.knoldus.main_module.validation;

import org.springframework.stereotype.Service;

@Service
public class MobileValidation {
    public boolean validateMobileField(String mobileField){
        String result="";
        System.out.println("validation:"+mobileField);
        if (mobileField.matches("\\d{10}")) {
            System.out.println(true);
            return true;
        }
        System.out.println(result);
        return false;
    }
}
