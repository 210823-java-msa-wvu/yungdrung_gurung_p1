package com.gurung.services.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidationService {

    public static  boolean isValidEmail(String email){
        String regex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        return false;
    }
}
