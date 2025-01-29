package com.expensetracker.PersonalExpenceTrackerAPI.DAO;

import com.expensetracker.PersonalExpenceTrackerAPI.paymentmethod.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
//import lombok.*;

import java.time.LocalDate;

@Entity
public class Expense {
    public Expense(){

    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;
    private String title;
    private String categories;
    private String description;
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method is required.")
    private PaymentMethod paymentMethod;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
//    public Expense(int id, String title, String description, LocalDate dueDate) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.dueDate = dueDate;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
