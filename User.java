package org.example;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        if (this.username.equals((username)) && this.password.equals(password)) {
            System.out.println("登录成功！");
            return true;
        }else {
            System.out.println("用户名或密码出错，登录失败");
        }
        return false;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (DigestUtils.md5Hex(oldPassword).equals(getPassword())) {
            setPassword(DigestUtils.md5Hex(newPassword));
            System.out.println("新密码设置成功: " + getUsername());
        } else {
            System.out.println("原密码错误，修改密码失败!");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return  password;
    }

    public void setPassword(String password) {
        this.password= password;
    }

    public void resetPassword(Scanner scanner) {
    }

}
