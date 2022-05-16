package com.example.demo.web.vo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tag {

    private String name;

    public Tag(String name) {
        String pattern = "^[\\w]{3,15}$";

        if(name == null) {
            throw new IllegalArgumentException("tag name cannot be null");
        }

        if(!matches(name, pattern)) {
            throw new IllegalArgumentException("tag name does not match with pattern " + pattern);
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    private boolean matches(String value, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return  m.matches();
    }
}
