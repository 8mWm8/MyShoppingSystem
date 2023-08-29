package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShoppingCar {
    private Customer customer;
    // 当前购物车列表
    private List<Product> cartProdList;
    // 所有商品列表
    private List<Product> productList = new ArrayList<>();

    private List<ShoppingHistoroy> shoppingList =new ArrayList<>();

    public ShoppingCar(Customer customer, List<Product> productList) {
        this.customer = customer;
        this.cartProdList = new ArrayList<>();
        loadShoppingHistory();
        loadProducts();
    }

    public void addProduct(Scanner scanner) {
        System.out.println("请输入商品编号:");
        Integer prodId = scanner.nextInt();
        System.out.println("请输入商品数量:");
        Integer prodNum = scanner.nextInt();
        Product product = new Product();
        product.setProductID(prodId);
        product.setProductCount(prodNum);
        for(Product p : productList){
            if(p.getProductID() == prodId){
                product.setProductPrice(p.getProductPrice());
                product.setProductName(p.getProductName());
            }
        }
        cartProdList.add(product);
        System.out.println("商品 " + product.getProductID() + " 已添加到购物车");
    }

    public void removeProduct(Scanner scanner) {
        System.out.println("请输入要移除购物车的商品ID：");
        Integer prodId = scanner.nextInt();
        System.out.print("是否确认从购物车移除该商品？(y/n): ");
        String input = scanner.next();
        if(input.equals("y")){
            for(Product product : cartProdList){
                if(product.getProductID() == prodId){
                    cartProdList.remove(product);
                    System.out.println("成功从购物车移除商品: " + product.getProductName());
                }
            }
        }
    }

    public void modifyProductQuantity(Scanner scanner) {
        System.out.println("请输入商品编号:");
        Integer prodId = scanner.nextInt();
        System.out.println("请输入商品数量:");
        Integer prodNum = scanner.nextInt();
        for (Product p : cartProdList) {
            if (p.getProductID() == prodId) {
                if(prodNum<=0){
                    cartProdList.remove(p);
                    System.out.println("商品 " + p.getProductName() + " 已移除 ");
                }else {
                    p.setProductCount(prodNum);
                    System.out.println("商品 " + p.getProductName() + " 的数量已修改为 " + prodNum);
                }
            }
        }
    }

    public Customer checkout() {
        double totalAmount = 0.0;
        for (Product product : cartProdList) {
            totalAmount += product.getProductPrice() * product.getProductCount();
        }
        System.out.println("订单总金额为：" + totalAmount);
        System.out.println("请选择支付方式！");
        System.out.println("0-支付宝");
        System.out.println("1-微信");
        System.out.println("2-银行卡");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        // 将购物历时保存到文件中
        addShoppingHistory(str);
        // 更新客户消费总金额
        customer.setTotalAmountSpent(customer.getTotalAmountSpent() + totalAmount);
        System.out.println("购物车结算完成，总金额为：" + totalAmount);
        System.out.println("客户 " + customer.getUsername() + " 的累计消费金额为：" + customer.getTotalAmountSpent());
        //更新消费金额
        customer.saveCurrentCustomers();
        return customer;
    }

    public void viewShoppingHistory() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("客户 " + customer.getUsername() + " 的购物历史：");
        for (ShoppingHistoroy historoy : shoppingList) {
            if(customer.getUsername().equals(historoy.getCustomerName())){
                System.out.println("商品名称："+historoy.getProductName()+
                        ", 商品价格：" + historoy.getProductPrice() +
                        ", 购买数量：" + historoy.getProductCount() +
                        ", 购买时间：" +  sdf.format(historoy.getCreateDate()) +
                        ", 支付方式：" + historoy.getPayType());
            }
        }
        System.out.println("--------------------end---------------------");
    }

    public void addShoppingHistory(String payType){
        loadShoppingHistory();
        if(payType.equals("0")){
            payType = "支付宝";
        }else if(payType.equals("1")){
            payType = "微信";
        }else if(payType.equals("2")){
            payType = "银行卡";
        }
        Iterator<Product> iterator = cartProdList.iterator();
        while (iterator.hasNext()){
            Product product = iterator.next();
            ShoppingHistoroy shoppingHistoroy = new ShoppingHistoroy();
            shoppingHistoroy.setCustomerName(customer.getUsername());
            shoppingHistoroy.setProductName(product.getProductName());
            shoppingHistoroy.setProductPrice(product.getProductPrice());
            shoppingHistoroy.setPayType(payType);
            shoppingHistoroy.setCreateDate(new Date());
            shoppingHistoroy.setProductCount(product.getProductCount());
            iterator.remove();
            shoppingList.add(shoppingHistoroy);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (FileWriter writer = new FileWriter("src/shoppingHistory.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (ShoppingHistoroy historoy : shoppingList) {
                StringBuilder sb = new StringBuilder();
                sb.append(historoy.getCustomerName()).append(",");
                sb.append(historoy.getProductName()).append(",");
                sb.append(sdf.format(historoy.getCreateDate())).append(",");
                sb.append(historoy.getProductPrice()).append(",");
                sb.append(historoy.getProductCount()).append(",");
                sb.append(historoy.getPayType());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadShoppingHistory(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (FileReader reader = new FileReader("src/shoppingHistory.txt");
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            shoppingList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] customers = line.split(",");
                ShoppingHistoroy shoppingHistoroy = new ShoppingHistoroy(customers[0],customers[1],
                        sdf.parse(customers[2]),Double.parseDouble(customers[3]),
                        Integer.parseInt(customers[4]),customers[5]);
                shoppingList.add(shoppingHistoroy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
