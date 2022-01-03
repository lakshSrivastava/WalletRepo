package com.assessment.wallet.Model;

public class TransferMoneyRequest {

    String fromUser;

    String toUser;

    double amountToBeTransferred;

    public TransferMoneyRequest(String fromUser, String toUser, double amountToBeTransferred) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amountToBeTransferred = amountToBeTransferred;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public double getAmountToBeTransferred() {
        return amountToBeTransferred;
    }

    public void setAmountToBeTransferred(double amountToBeTransferred) {
        this.amountToBeTransferred = amountToBeTransferred;
    }
}
