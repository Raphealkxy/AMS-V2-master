package com.eric.modules.sys.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义token
 * @author Chen 2018/1/10
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
