package org.example;
public class Product {
    private int productID;
    private String productName; // 名称
    private String manufacturer; // 产商
    private String productedDate;// 生产日期
    private String productModel; // 型号
    private double purchaseCost; // 进货价
    private double productPrice; // 零售价
    private int productCount; // 数量

    public Product(){
    }

    public Product(int productID, String productName, String manufacturer, String productedDate, String productModel, double purchaseCost, double productPrice, int productCount) {
        this.productID = productID;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.productedDate = productedDate;
        this.productModel = productModel;
        this.purchaseCost = purchaseCost;
        this.productPrice = productPrice;
        this.productCount = productCount;
    }

    public void displayProductInfo(Product product) {
        System.out.println("商品ID："+product.getProductID()+
                ", 商品名称：" + product.getProductName() +
                ", 生产产商：" + product.getManufacturer() +
                ", 生产日期：" + product.getProductedDate() +
                ", 型号：" + product.getProductModel() +
                ", 进货价：" + product.getPurchaseCost()+
                ", 零售价：" + product.getProductPrice()+
                ", 数量：" + product.getProductCount());
    }
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductedDate() {
        return productedDate;
    }

    public void setProductedDate(String productedDate) {
        this.productedDate = productedDate;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
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

    public void updateProductInfo(int productID, String productName, String manufacturer, String productedDate, String productModel, double purchaseCost, double productPrice, int productCount) {
        this.productID = productID;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.productedDate = productedDate;
        this.productModel = productModel;
        this.purchaseCost = purchaseCost;
        this.productPrice = productPrice;
        this.productCount = productCount;
    }
}

