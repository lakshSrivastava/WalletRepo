package com.assessment.wallet.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Account {
	
	@Id
	private String phoneNum; //Phone Number will be always unique for each user

	private String name;

	@Transient
	private String password;
	
	private double walletBalance;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserPassword() {
		return password;
	}

	public void setUserPassword(String password) {
		this.password = password;
	}

	public double getWalletBalance() {
		return walletBalance;
	}
	
	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}	

	@Override
	public String toString() {
		return "User Profile -\n"
			   +"Phone Num - "+phoneNum+"\n"
			   +"Username - "+ name +"\n"
			   +"Wallet Balance - "+walletBalance;
	}
	
}
