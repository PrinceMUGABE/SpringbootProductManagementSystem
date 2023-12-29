package com.auca.modal;


import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date saleDate;
    private double amount;

    private String firstname;
    private String lastname;
    private String paymentMethod;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_id")
    private Long productId;


    private String mobileMoneyPhoneNumber;
    private String creditCardNumber;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    public Sale() {
    }

    public Sale(Long id) {
        this.id = id;
    }

    public Sale(Long id, Date saleDate, double amount, String firstname, String lastname, String paymentMethod, Integer quantity, Long productId, String mobileMoneyPhoneNumber, String creditCardNumber, Product product) {
        this.id = id;
        this.saleDate = saleDate;
        this.amount = amount;
        this.firstname = firstname;
        this.lastname = lastname;
        this.paymentMethod = paymentMethod;
        this.quantity = quantity;
        this.productId = productId;
        this.mobileMoneyPhoneNumber = mobileMoneyPhoneNumber;
        this.creditCardNumber = creditCardNumber;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getMobileMoneyPhoneNumber() {
        return mobileMoneyPhoneNumber;
    }

    public void setMobileMoneyPhoneNumber(String mobileMoneyPhoneNumber) {
        this.mobileMoneyPhoneNumber = mobileMoneyPhoneNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

