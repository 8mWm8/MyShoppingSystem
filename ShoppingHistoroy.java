package org.example;

import java.util.Date;

public class ShoppingHistoroy {
    private String customerName; // 客户名称
    private String productName; // 商品名称
    private Date createDate;   // 购买时间
    private double productPrice; // 零售价
    private int productCount; // 数量
    private String payType; // 支付方式

    public ShoppingHistoroy(){
    }

    public ShoppingHistoroy(String customerName,String productName,Date createDate,double productPrice,int productCount,String payType) {
        this.customerName = customerName;
        this.productName = productName;
        this.createDate = createDate;
        this.productPrice = productPrice;
        this.productCount = productCount;
        this.payType = payType;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}

