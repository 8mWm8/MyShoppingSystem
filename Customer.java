package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.*;

import java.text.SimpleDateFormat;

public class Customer extends User {
    private static int idCounter = 1;
    private List<Customer> customerList = new ArrayList<>();
    private static User[] users = new User[100]; // 假设最多能保存100个用户
    private static int userCount = 0; // 记录已注册用户数量
    private int customerID;
    private String level;
    private Date registrationDate;
    private double totalAmountSpent;
    private String phoneNumber;
    private String email;
    private Map<String,Integer> errorNum = new HashMap<>();
    public Customer(){
        super(null, null);
    }

    public Customer(String username, String password, String level, Date registrationDate, double totalAmountSpent, String phoneNumber, String email) {
        super(username, password);
        this.customerID = idCounter++;
        this.level = level;
        this.registrationDate = registrationDate;
        this.totalAmountSpent = totalAmountSpent;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public double getTotalAmountSpent(){
        return totalAmountSpent;
    }
    public void setTotalAmountSpent(double totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
    }

    public void loadCustomers(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (FileReader reader = new FileReader("src/customers.txt");
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            customerList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] customers = line.split(",");
                Customer customer = new Customer(customers[0],customers[1],customers[2],
                        sdf.parse(customers[3]),Double.parseDouble(customers[4]),
                        customers[5],customers[6]);
                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(Customer customer1) {
        loadCustomers();
        customerList.add(customer1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (FileWriter writer = new FileWriter("src/customers.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (Customer customer : customerList) {
                StringBuilder sb = new StringBuilder();
                sb.append(customer.getUsername()).append(",");
                sb.append(customer.getPassword()).append(",");
                sb.append(customer.getLevel()).append(",");
                sb.append(sdf.format(customer.getRegistrationDate())).append(",");
                sb.append(customer.getTotalAmountSpent()).append(",");
                sb.append(customer.getPhoneNumber()).append(",");
                sb.append(customer.getEmail());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("用户注册成功");
    }

    public Customer login(Scanner scanner) {
        loadCustomers();
        System.out.println("请输入用户名:");
        String username = scanner.next();
        System.out.println("请输入密码:");
        String passwordStr = scanner.next();
        String password =DigestUtils.md5Hex(passwordStr);
        // 遍历数组查找用户
        for (Customer customer: customerList) {
            while (customer.getUsername().equals(username)) {
                Integer errorCount = errorNum.get(username) == null ? 0 : errorNum.get(username);
                if(errorCount != null && errorCount>3){
                    System.out.println("改用户密码错误超过5次，已被锁定");
                    return null;
                }
                if(customer.getPassword().equals(password)){
                    System.out.println("登录成功");
                    return customer;
                }else {
                    errorNum.put(username,errorCount+1);
                    System.out.println("密码错误，请重新输入密码：");
                    passwordStr = scanner.next();
                    password =DigestUtils.md5Hex(passwordStr);
                }
            }
        }
        System.out.println("未找到该用户");
        return null;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void shop(Scanner scanner) {
        System.out.println("<三级目录----购物车界面>");
        Product product = null;
        int count = 1;
        ShoppingCar car = new ShoppingCar(this, null);
        boolean shopFlag = true;
        int shopSelect = 0;
        while (shopFlag) {
            System.out.println("请输入您的操作：0-返回，1-加购商品，2-删除商品，3-修改商品数量，4-付款，5-查看购物历史");
            shopSelect = Integer.valueOf(scanner.next());
            switch (shopSelect){
                case 0:
                    shopFlag = false;
                    break;
                case 1:
                    car.addProduct(scanner);
                    break;
                case 2:
                    car.removeProduct(scanner);
                    break;
                case 3:
                    car.modifyProductQuantity(scanner);
                    break;
                case 4:
                    car.checkout();
                    break;
                case 5:
                    car.viewShoppingHistory();
                    break;
                default:
                    System.out.println("请输入正确的数字!");
                    break;
                }
            }
    }

    public void logout() {
        this.saveCurrentCustomers();
        System.out.println("登出成功！");
    }

    @Override
    public void setPassword(String password) {super.setPassword(password); }

    public void forgotPassword(Scanner scanner){
        System.out.println("请输入用户名：");
        String username = scanner.next();
        System.out.println("请输入注册邮箱：");
        String email = scanner.next();
        loadCustomers();
        for (Customer customer : customerList) {
            if(customer.getUsername().equals(username)){
                String password = "Abc1234!";
                customer.setPassword(DigestUtils.md5Hex(password));
                System.out.println("新密码为已发送到" +email + " 邮箱，请查收");
                System.out.println("新密码为：" +password + ", 请使用此密码登录系统");
                return;
            }
        }
        System.out.println("没有找到该用户！");
    }

    public void resetPassword(Scanner scanner){
        System.out.println("请输入用户名：");
        String username = scanner.next();
        loadCustomers();
        for (Customer customer : customerList) {
            if(customer.getUsername().equals(username)){

                String password = "Abc1234!";
                customer.setPassword(DigestUtils.md5Hex(password));

                System.out.println("用户"+username+"密码已重置，新密码为：" +password + ".");
                return;
            }
        }
        System.out.println("没有找到该用户！");
    }

    public void saveCurrentCustomers(){
        loadCustomers();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (FileWriter writer = new FileWriter("src/customers.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (Customer customer : customerList) {
                if(customer.getUsername().equals(this.getUsername())){
                    customer.setTotalAmountSpent(this.getTotalAmountSpent());
                    customer.setPassword(this.getPassword());
                }
                StringBuilder sb = new StringBuilder();
                sb.append(customer.getUsername()).append(",");
                sb.append(customer.getPassword()).append(",");
                sb.append(customer.getLevel()).append(",");
                sb.append(sdf.format(customer.getRegistrationDate())).append(",");
                sb.append(customer.getTotalAmountSpent()).append(",");
                sb.append(customer.getPhoneNumber()).append(",");
                sb.append(customer.getEmail());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
