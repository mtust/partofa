package com.partofa.domain;


import org.assertj.core.util.Strings;

/**
 * Created by tust on 09.09.2016.
 */
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private String paramName;

    Role(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

    public static Role getRole(String paramName) {
        if (Strings.isNullOrEmpty(paramName)) {
            return null;
        }
        switch (paramName.toUpperCase()) {
            case "ROLE_ADMIN":
                return ROLE_ADMIN;
            case "ROLE_USER":
                return ROLE_USER;
        }
        return null;
    }

}
