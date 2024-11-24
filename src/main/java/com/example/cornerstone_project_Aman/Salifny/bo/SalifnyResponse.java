package com.example.cornerstone_project_Aman.Salifny.bo;

import java.util.Date;

public class SalifnyResponse {
    private String aldaynUser;
    private String almadyonUser;
    private Date startDate;
    private Date endDate;
    private Double amount;
    private int numberOfInstallments;
    private Double installmentAmount;


    public String getAldaynUser() {
        return aldaynUser;
    }

    public void setAldaynUser(String aldaynUser) {
        this.aldaynUser = aldaynUser;
    }

    public String getAlmadyonUser() {
        return almadyonUser;
    }

    public void setAlmadyonUser(String almadyonUser) {
        this.almadyonUser = almadyonUser;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }
}
