package com.backend.miniProjetBack.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties("paiement")
public class Facture  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    private double amount;
    @Temporal(TemporalType.DATE)
    private Date dateFacture;
    private Boolean payer=false;
    @ManyToMany
    private List<Produit> produit;
    @ElementCollection
    private List<Integer> qte;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private Paiement payment;



    private int totalHT ;

    private int tva=19 ;
    public User getClient() {
        return client;
    }

    public List<Integer> getQte() {
        return qte;
    }

    public void setQte(List<Integer> qte) {
        this.qte = qte;
    }

    public void setClient(User client) {
        this.client = client;
    }

    // Many invoices can be associated with one payment


    public Facture(double amount, Date dateFacture, Boolean payer, List<Produit> produit, List<Integer> qte, User client, Paiement payment , int tva , int totalHT) {
        this.amount = amount;
        this.dateFacture = dateFacture;
        this.payer = payer;
        this.produit = produit;
        this.qte = qte;
        this.client = client;
        this.payment = payment;
        this.totalHT=totalHT ;
        this.tva=tva ;
    }

    public Facture() {
        super();
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public Boolean getPayer() {
        return payer;
    }

    public void setPayer(Boolean payer) {
        this.payer = payer;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public List<Produit> getProduit() {
        return produit;
    }

    public void setProduit(List<Produit> produit) {
        this.produit = produit;
    }



    public Paiement getPayment() {
        return payment;
    }

    public void setPayment(Paiement payment) {
        this.payment = payment;
    }

    public int getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(int totalHT) {
        this.totalHT = totalHT;
    }

    public int getTva() {
        return tva;
    }

    public void setTva(int tva) {
        this.tva = tva;
    }


    // Constructors, getters, and setters
}