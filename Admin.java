package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "ynuinfo#777";
    private  List<Customer> customerList = new ArrayList<>();
    private  List<Product> productList = new ArrayList<>();
    public Admin() {
        super(ADMIN_USERNAME, ADMIN_PASSWORD);
        // 加载客户信息
        loadCustomers();
        loadProducts();
    }

    public boolean login(String username, String password) {
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("登录成功！");
            return true;
        }else {
            System.out.println("用户名或密码出错，登录失败");
            return false;
        }
    }

    public void saveCustomers(){
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
    }

    public void listCustomers() {
        // 列出客户信息
        System.out.println("-------------------Listing customers-------------------");
        // 遍历customerList并打印每个客户的用户名
        for (Customer customer : customerList) {
            System.out.println("客户ID："+customer.getCustomerID()+
                    ", 客户名：" + customer.getUsername() +
                    ", 客户级别：" + customer.getLevel() +
                    ", 注册时间：" + customer.getRegistrationDate() +
                    ", 消费总额：" + customer.getTotalAmountSpent() +
                    ", 手机号：" + customer.getPhoneNumber()+
                    ", 邮箱：" + customer.getEmail());
            //System.out.println("Customer: " + customer.getUsername());
        }
        System.out.println("------------------------end---------------------------");
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

    public void deleteCustomer() {
        // 删除客户信息
        Scanner scanner = new Scanner(System.in);
        String customerName = scanner.nextLine();
        // 遍历customerList查找匹配的客户
        for (Customer customer : customerList) {
            if (customer.getUsername().equalsIgnoreCase(customerName)) {
                System.out.print("是否确认删除该客户？(y/n): ");
                String input = scanner.next();
                if(input.equals("y")){
                    // 在此处可以进一步处理删除客户的逻辑
                    customerList.remove(customer);
                    System.out.println("该客户已删除: " + customer.getUsername());
                }
                return;
            }
        }
        // 如果没有找到匹配的客户
        System.out.println("Customer not found.");
    }

    public void searchCustomer() {
        System.out.println("请输入要查询的客户名：");
        Scanner scanner = new Scanner(System.in);
        String customerName = scanner.nextLine();
        // 查询客户信息
        System.out.println("-------------------Listing customers-------------------");
        // 遍历customerList查找匹配的客户
        for (Customer customer : customerList) {
            if (customer.getUsername().equalsIgnoreCase(customerName) || customerName.equals(customer.getCustomerID()+"")) {
                System.out.println("客户ID："+customer.getCustomerID()+
                        ", 客户名：" + customer.getUsername() +
                        ", 客户级别：" + customer.getLevel() +
                        ", 注册时间：" + customer.getRegistrationDate() +
                        ", 注册时间：" + customer.getTotalAmountSpent() +
                        ", 手机号：" + customer.getPhoneNumber()+
                        ", 邮箱：" + customer.getEmail());
                System.out.println("------------------------end-----------------------------");
                // 在此处可以进一步处理找到的客户信息
                return;
            }
        }
        // 如果没有找到匹配的客户
        System.out.println("未找到该客户");
    }

    public void loadProducts() {
        try (FileReader reader = new FileReader("src/products.txt");
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            productList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] products = line.split(",");
                Product product = new Product(Integer.parseInt(products[0]),products[1],products[2],
                        products[3], products[4],
                        Double.parseDouble(products[5]),
                        Double.parseDouble(products[6]), Integer.parseInt(products[7]));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listProducts() {
        System.out.println("------------------------商品列表---------------------------");
        for (Product product : productList) {
            product.displayProductInfo(product);
        }
        System.out.println("------------------------end---------------------------");
    }

    public void addProduct() {
        // 添加商品信息
        Product product = new Product();
        Scanner s = new Scanner(System.in);
        do {
            System.out.println("请输入商品ID：");
            product.setProductID(Integer.parseInt(s.next()));
            if (checkProductId(product)) {
                break;
            } else {
                System.out.println("商品Id重复，请重新输入！");
            }
        } while (true);
        System.out.println("请输入商品名称：");
        product.setProductName(s.next());
        System.out.println("请输入生产厂商：");
        product.setManufacturer(s.next());
        System.out.println("请输入生产日期：");
        product.setProductedDate(s.next());
        System.out.println("请输入型号：");
        product.setProductModel(s.next());
        System.out.println("请输入进货价：");
        product.setPurchaseCost(Double.parseDouble(s.next()));
        System.out.println("请输入零售价：");
        product.setProductPrice(Double.parseDouble(s.next()));
        System.out.println("请输入商品数量：");
        product.setProductCount(Integer.parseInt(s.next()));
        // 如果商品ID不存在，将商品添加到列表中
        productList.add(product);
        System.out.println("Product added: " + product.getProductName());
    }

    private boolean checkProductId(Product product){
        // 检查商品ID是否已存在，以避免重复添加相同的商品
        for (Product p : productList) {
            if (p.getProductID() == product.getProductID()) {
                return false;
            }else {
                return true;
            }
        }
        return true;
    }

    public void deleteProduct() {
        System.out.println("请输入要删除的商品ID：");
        Scanner scanner = new Scanner(System.in);
        String productId = scanner.nextLine();
        // 遍历商品列表
        for (Product product : productList) {
            if (productId.equalsIgnoreCase(product.getProductID()+"")) {
                // 在此处可以进一步处理删除商品的逻辑
                System.out.print("是否确认删除该商品？(y/n): ");
                String input = scanner.next();
                if(input.equals("y")){
                    productList.remove(product);
                    System.out.println("成功删除商品: " + product.getProductName());
                }
                return;
            }
        }
        // 如果没有找到匹配的商品
        System.out.println("未找到该商品");
    }

    public void modifyProduct() {
        System.out.println("请输入要修改的商品ID");
        Scanner s = new Scanner(System.in);
        String productId = s.nextLine();
        // 遍历商品列表
        for (Product product : productList) {
            if (productId.equalsIgnoreCase(product.getProductID() + "")) {
                product.displayProductInfo(product);
                productList.remove(product);
                System.out.println("请输入商品名称：");
                product.setProductName(s.next());
                System.out.println("请输入生产厂商：");
                product.setManufacturer(s.next());
                System.out.println("请输入生产日期：");
                product.setProductedDate(s.next());
                System.out.println("请输入型号：");
                product.setProductModel(s.next());
                System.out.println("请输入进货价：");
                product.setPurchaseCost(Double.parseDouble(s.next()));
                System.out.println("请输入零售价：");
                product.setProductPrice(Double.parseDouble(s.next()));
                System.out.println("请输入商品数量：");
                product.setProductCount(Integer.parseInt(s.next()));
                // 重新添加到商品列表
                productList.add(product);
                return;
            }
        }
        System.out.println("没有找到该商品");
    }

    public void resetPassword(Scanner scanner) {
        Customer customer = new Customer();
        customer.resetPassword(scanner);
    }

    public void searchProduct() {
        System.out.println("请输入要查询的商品名称或商品生产厂家或零售价：");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        // 查询商品信息
        System.out.println("-------------------商品列表-------------------");

        for (Product  product : productList) {
            if (product.getProductName().equalsIgnoreCase(str) ||
                    str.equals(product.getManufacturer()+"")||
                    str.equals(product.getPurchaseCost()+"")) {
                product.displayProductInfo(product);
            }
        }
        System.out.println("------------------------end-----------------------------");
    }

    public void saveProducts(){
        try (FileWriter writer = new FileWriter("src/products.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (Product product : productList) {
                StringBuilder sb = new StringBuilder();
                sb.append(product.getProductID()).append(",");
                sb.append(product.getProductName()).append(",");
                sb.append(product.getManufacturer()).append(",");
                sb.append(product.getProductedDate()).append(",");
                sb.append(product.getProductModel()).append(",");
                sb.append(product.getPurchaseCost()).append(",");
                sb.append(product.getProductPrice()).append(",");
                sb.append(product.getProductCount());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        this.saveCustomers();
        this.saveProducts();
        System.out.println("登出成功！");
    }
}

