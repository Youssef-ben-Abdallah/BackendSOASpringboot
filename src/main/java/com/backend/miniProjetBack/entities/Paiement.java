package com.backend.miniProjetBack.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Paiement{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    private String cardHolderName;

    private String creditCardNumber;

    private String expirationDate;

    private String cvv;



    @ManyToOne
    private User client;

    @OneToMany(mappedBy = "payment")
    @JsonManagedReference
    private List<Facture> invoices;

    public Paiement() {
        super();
    }

    public Paiement(Long paymentId, double amount, Date paymentDate, String cardHolderName, String creditCardNumber, String expirationDate, String cvv, List<Facture> invoices,User client) {
        super();
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.cardHolderName = cardHolderName;
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.invoices = invoices;
        this.client = client;
    }

    public Paiement(double amount, Date paymentDate, String cardHolderName, String creditCardNumber, String expirationDate, String cvv, List<Facture> invoices,User client) {
        super();
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.cardHolderName = cardHolderName;
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.invoices = invoices;
        this.client = client;
    }


    // Getters and Setters

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
    public List<Facture> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Facture> invoices) {
        this.invoices = invoices;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", invoices=" + invoices +
                '}';
    }

    public Facture getFacture() {
        if (invoices != null && !invoices.isEmpty()) {
            return invoices.get(0);
        }
        return null;
    }


}