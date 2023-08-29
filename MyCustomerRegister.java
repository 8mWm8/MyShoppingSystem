package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Scanner;

import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCustomerRegister {
    private Scanner scanner = null;
    private Customer customer = null;

    public MyCustomerRegister(Scanner scanner, Customer customer) {
        this.scanner = scanner;
        this.customer = customer;
    }
    public void runRegister(List<Customer> customerList) {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        do {
            boolean flag = false;
            System.out.println("请输入用户名:");
            username = scanner.next();
            if(username.length()<6){
                System.out.println("用户名长度不少于5个字符，请重新输入用户名！");
            }else {
                //flag = true;
                if (checkUsername(username,customerList)) {
                    flag = true;
                } else {
                    System.out.println("用户名已存在，请重新输入！");
                }
            }
            if(flag){
                break;
            }
        } while (true);
        String password ="";
        do {
            System.out.println("请输入密码:");
            password = scanner.next();
            if (validatePassword(password)) {
                break;
            } else {
                System.out.println("密码格式不正确，密码长度大于 8 个字符，必须是大小写字母、数字和标点符号的组合！");
            }
        } while (true);
        // MD5 加密
        password = DigestUtils.md5Hex(password);
        System.out.println("请输入会员等级:");
        String level = scanner.next();
        Date registrationDate = new Date(); // 使用当前日期
        System.out.println("请输入总消费金额:");
        double totalAmountSpent = scanner.nextDouble();
        System.out.println("请输入电话号码:");
        String phoneNumber = scanner.next();
        System.out.println("请输入邮箱地址:");
        String email = scanner.next();
        // 创建客户对象
        Customer customer = new Customer(username, password, level, registrationDate, totalAmountSpent, phoneNumber, email);
        // 调用注册函数
        customer.register(customer);
    }

    private boolean checkUsername(String userName,List<Customer> customerList){
        // 检查用户名是否已存在
        for (Customer customer : customerList) {
            if (customer.getUsername().equals(userName)) {
                return false;
            }else {
                return true;
            }
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        // 校验密码长度
        if (password.length() < 8) {
            return false;
        }
        // 校验密码是否包含大小写字母、数字和标点符号的组合
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
