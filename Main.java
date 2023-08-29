package org.example;

import java.util.*;
public class Main {
    private static List<User> userList = new ArrayList<>();
    private static List<Product> productList = new ArrayList<>();
    private static Admin admin;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        oneMenu(scanner);
        scanner.close();
    }

    private static void oneMenu(Scanner scanner){
        boolean mainFlag = true;
        int identityChoice = 0;
        while (mainFlag) {
            System.out.println("<一级目录----身份选择>");
            System.out.println("请选择您的身份： 1-管理员，2-客户，0-退出登录");
            String str = scanner.next();
            identityChoice = Integer.valueOf(str);
            switch (identityChoice) {
                case 0:
                    if(admin!= null){
                        admin.saveCustomers(); // 保存客户信息到文件中
                        admin.saveProducts(); // 保存商品信息到文件中
                    }
                    System.out.println("即将退出系统，感谢您的使用！");
                    mainFlag = false;
                    break;
                case 1:
                    login(scanner);
                    break;
                case 2:
                    customerMenu(scanner);
                    break;
                default:
                    System.out.println("请输入正确的数字!");
                    break;
            }
        }
    }

    private static void login(Scanner scanner){
        admin = new Admin();
        if (!userList.contains(admin)) {
            userList.add(admin);
        }
        System.out.println("管理员admin，欢迎登录：");
        System.out.println("请输入密码：");
        String adminPassword = scanner.next();
        boolean loggedIn = admin.login("admin",adminPassword);
        if (loggedIn) {
            adminMenu(scanner);
        }
    }

    private static void adminMenu(Scanner scanner) {
        int adminChoice = 0;
        boolean adminFlag = true;
        while (adminFlag) {
            System.out.println("<二级目录----管理员界面>");
            System.out.println("请选择您的操作，0-返回，1-密码管理，2-客户管理，3-商品管理，4-退出登录");
            String str = scanner.next();
            adminChoice = Integer.valueOf(str);
            switch (adminChoice) {
                case 0:
                    System.out.println("返回上一目录");
                    adminFlag = false;

                    oneMenu(scanner);
                    break;
                case 1:
                    adminPasswordManagement(scanner, admin);
                    break;
                case 2:
                    customerManagement(scanner);
                    break;
                case 3:
                    productManagement(scanner);
                    break;
                case 4:
                    admin.logout();
                    adminFlag = false;
                    break;
                default:
                    System.out.println("无效的选择");
                    break;
            }
        }
    }

    private static void customerMenu(Scanner scanner) {
        Customer customer = new Customer();
        int customerChoice;
        boolean customerFlag = true;
        while (customerFlag) {
            System.out.println("<二级目录----客户界面>");
            System.out.println("请选择您的操作：0-返回上一目录，1-注册，2-登录，3-密码管理，4-购物，5-退出");
            String str = scanner.next();
            customerChoice = Integer.valueOf(str);
            switch (customerChoice) {
                case 0:
                    System.out.println("返回上一目录");
                    customerFlag = false;
                    break;
                case 1:
                    customer = new Customer();
                    customer.loadCustomers();
                    MyCustomerRegister register = new MyCustomerRegister(scanner,customer);
                    register.runRegister(customer.getCustomerList());
                    break;
                case 2:
                    customer = new Customer();
                    customer = customer.login(scanner);
                    if(customer !=null){
                        customer.shop(scanner);
                    }
                    break;
                case 3:
                    custPasswordManagement(scanner,customer);
                    break;
                case 4:
                    if(customer.getUsername() == null){
                        System.out.println("请先登录");
                    }else {
                        customer.shop(scanner);
                    }
                    break;
                case 5:
                    customer.logout();
                    customerFlag = false;
                    break;
                default:
                    System.out.println("无效的选择");
                    break;
            }
        }
    }

    private static void custPasswordManagement(Scanner scanner,Customer customer) {
        int operationChoice;
        System.out.println("请选择操作：");
        System.out.println("0-返回上一目录");
        System.out.println("1-修改密码");
        System.out.println("2-忘记密码");
        operationChoice = scanner.nextInt();
        switch (operationChoice) {
            case 0:
                System.out.println("返回上一目录");
                break;
            case 1:
                if(customer != null &&  customer.getUsername() != null){
                    System.out.println("请输入老密码：");
                    String oldPassword = scanner.next();
                    String newPassword ="";
                    do {
                        System.out.println("请输入新密码：");
                        newPassword = scanner.next();
                        if (MyCustomerRegister.validatePassword(newPassword)) {
                            break;
                        } else {
                            System.out.println("密码格式不正确，密码长度大于 8 个字符，必须是大小写字母、数字和标点符号的组合 ！");
                        }
                    } while (true);
                    customer.changePassword(oldPassword,newPassword);
                    // 保存密码
                    customer.saveCurrentCustomers();
                }else {
                    System.out.println("修改密码请先登录");
                }
                break;
            case 2:
                customer.forgotPassword(scanner);
                break;
            default:
                System.out.println("无效的选择");
                break;
        }
    }

    private static void passwordManagement(Scanner scanner) {
        System.out.println("请输入用户名：");
        String username = scanner.next();
        for (User user : userList) {
            if (user instanceof Customer && user.getUsername().equals(username)) {
                int operationChoice;
                do {
                    System.out.println("请选择操作：");
                    System.out.println("0-返回上一目录");
                    System.out.println("1-修改密码");
                    System.out.println("2-忘记密码");
                    operationChoice = scanner.nextInt();
                    switch (operationChoice) {
                        case 0:
                            System.out.println("返回上一目录");
                            break;
                        case 1:
                            System.out.println("请输入老密码：");
                            String oldPassword = scanner.nextLine();
                            System.out.println("请输入新密码：");
                            String newPassword = scanner.nextLine();
                            user.changePassword(oldPassword,newPassword);
                            break;
                        case 2:
                            System.out.println("已向管理员发送重置密码请求");
                            break;
                        default:
                            System.out.println("无效的选择");
                            break;
                    }
                } while (operationChoice != 0);
                return;
            }
        }

        System.out.println("用户不存在！");
    }
    private static void adminPasswordManagement(Scanner scanner, Admin admin) {
        int operationChoice;
        do {
            System.out.println("请选择操作：");
            System.out.println("0-返回上一目录");
            System.out.println("1-修改密码");
            System.out.println("2-重置密码");
            operationChoice = scanner.nextInt();
            switch (operationChoice) {
                case 0:
                    System.out.println("返回上一目录");
                    break;
                case 1:
                    System.out.println("请输入老密码：");
                    String oldPassword = scanner.nextLine();
                    System.out.println("请输入新密码：");
                    String newPassword = scanner.nextLine();
                    admin.changePassword(oldPassword,newPassword);
                    break;
                case 2:
                    admin.resetPassword(scanner);
                    break;
                default:
                    System.out.println("无效的选择");
                    break;
            }
        } while (operationChoice != 0);
    }

    private static void customerManagement(Scanner scanner) {
        int operationChoice;
        do {
            System.out.println("请选择操作：");
            System.out.println("0-返回上一目录");
            System.out.println("1-列出客户信息");
            System.out.println("2-删除客户信息");
            System.out.println("3-查询客户信息");
            operationChoice = scanner.nextInt();
            switch (operationChoice) {
                case 0:
                    System.out.println("返回上一目录");
                    adminMenu(scanner);
                    break;
                case 1:
                    admin.listCustomers();
                    break;
                case 2:
                    System.out.println("请输入要删除的客户名：");
                    admin.deleteCustomer();
                    break;
                case 3:
                    admin.searchCustomer();
                    break;
                default:
                    System.out.println("无效的选择");
                    break;
            }
        } while (operationChoice != 0);
    }

    private static void productManagement(Scanner scanner) {
        int operationChoice;
        do {
            System.out.println("请选择操作：");
            System.out.println("0-返回上一目录");
            System.out.println("1-列出商品信息");
            System.out.println("2-添加商品信息");
            System.out.println("3-修改商品信息");
            System.out.println("4-删除商品信息");
            System.out.println("5-搜索商品信息");
            operationChoice = scanner.nextInt();
            switch (operationChoice) {
                case 0:
                    System.out.println("返回上一目录");
                    adminMenu(scanner);
                    break;
                case 1:
                    admin.listProducts();
                    break;
                case 2:
                    admin.addProduct();
                    break;
                case 3:
                    admin.modifyProduct();
                    break;
                case 4:
                    admin.deleteProduct();
                    break;
                case 5:
                    admin.searchProduct();
                    break;
                default:
                    System.out.println("无效的选择");
                    break;
            }
        } while (operationChoice != 0);
    }

    private static void exit(){
        admin.saveCustomers();
    }

}
