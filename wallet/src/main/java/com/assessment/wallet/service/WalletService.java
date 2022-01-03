package com.assessment.wallet.service;

import java.util.List;

import com.assessment.wallet.Model.Account;
import com.assessment.wallet.Model.TransferMoneyRequest;
import com.assessment.wallet.Model.UserWalletTransaction;

public interface WalletService {
	
	String addUser(Account account);
	
	String loginUser(Account account);
	
	boolean isExistingUser(String phoneNum);
	
	String getUser(String phoneNum);
	
	String updateUser(Account account);
	
	String addMoney(Account account, double amountToAdd);
	
	String transferMoney(TransferMoneyRequest transferMoneyRequest);
	
	List<UserWalletTransaction> getAllTransactionForUser(String userId);
	
}
