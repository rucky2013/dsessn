package com.github.dsessn.security.oauth;


/**
 * 令牌信息
 *
 * @author pinian.lpn
 */
public class OauthToken {
    private String username;
    private String password;

    public OauthToken() {
    }

    public OauthToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
