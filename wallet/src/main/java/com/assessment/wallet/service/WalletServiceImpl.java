package com.assessment.wallet.service;

import java.util.Date;
import java.util.List;

import com.assessment.wallet.Model.TransferMoneyRequest;
import com.assessment.wallet.exception.AmountNotValidException;
import com.assessment.wallet.exception.TransferMoneyTransactionFailureException;
import com.assessment.wallet.exception.ValidationFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.wallet.constants.WalletConstants;
import com.assessment.wallet.dao.WalletTransactionDao;
import com.assessment.wallet.dao.AccountDao;
import com.assessment.wallet.Model.Account;
import com.assessment.wallet.Model.UserWalletTransaction;
import com.assessment.wallet.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

	Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
	
	@Autowired
	private AccountDao accountDao;
	 
	@Autowired 
	private WalletTransactionDao walletTransactionDao;
	 
	/**
	 *This method will add a non-existing user
	 */
	@Override
	public String addUser(Account account) {
		logger.trace("Entered addUser() method");
		String phoneNum = account.getPhoneNum();
		if(isExistingUser(phoneNum)) {
			logger.trace("User already have an account");
			throw new ValidationFailureException("You already have an account on Wallet");
		}
		accountDao.save(account);
		logger.trace("User Added Successfully");
		logger.trace("addUser() method execution completion....");
		return account.getName()+", Welcome To Wallet";
	}
	
	/**
	 *This method will login a user
	 */ 
	@Override
	public String loginUser(Account account) {
		logger.trace("Entered loginUser() method");
		String phoneNum = account.getPhoneNum();
		String userPassword = account.getUserPassword();
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(WalletConstants.NON_EXISTING_USER);
		}
		Account accountValidation = accountDao.getById(phoneNum);
		if(userPassword.equals(accountValidation.getUserPassword())) {
			logger.trace("Successful login");
			return "You have successfully logged-in";
		}
		logger.trace("loginUser() method execution completion....");
		return "Your password is incorrect";
	}

	/**
	 *This method will check if user is existing or not
	 */
	@Override
	public boolean isExistingUser(String userId) {
		logger.trace("Entered isExistingUser() method");
		if(accountDao.existsById(userId)) {
			logger.trace("Existing User");
			return true;
		}
		logger.trace("isExistingUser() method execution completion....");
		return false;
	}

	/**
	 *This method will view a user details
	 */
	@Override
	public String getUser(String userId) {
		logger.trace("Entered viewUser() method");
		if(!isExistingUser(userId)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(WalletConstants.NON_EXISTING_USER);
		}
		logger.trace("viewUser() method execution completion....");
		return accountDao.getById(userId).toString();
	}

	/**
	 *This method will update a user
	 */
	@Override
	public String updateUser(Account account) {
		logger.trace("Entered updateUser() method");
		String phoneNum = account.getPhoneNum();
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(WalletConstants.NON_EXISTING_USER);
		}
		accountDao.save(account);
		logger.trace("updateUser() method execution completion....");
		return "Updated Successfully";
	}

	/**
	 *This method will add money to user's wallet
	 */
	@Override
	public String addMoney(Account account, double amountToAdd) {
		logger.trace("Entered addMoney() method");
		String phoneNum = account.getPhoneNum();
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(WalletConstants.NON_EXISTING_USER);
		}
		if(amountToAdd<=0)
		{
			logger.trace(WalletConstants.ADD_MONEY_GREATER_THAN_ZERO);
			throw new AmountNotValidException(WalletConstants.ADD_MONEY_GREATER_THAN_ZERO);
		}
		Account accountDB = accountDao.getById(phoneNum);
		double currentBal = accountDB.getWalletBalance();
		currentBal += amountToAdd;
		accountDB.setWalletBalance(currentBal);
		accountDao.save(accountDB);
		addMoneyTransaction(account,amountToAdd);
		logger.trace("addMoney() method execution completion....");
		return amountToAdd+" added successfully. Your current wallet balance is: "+currentBal;
	}

	/**
	 *This method will transfer user's wallet money
	 */
	@Override
	public String transferMoney(TransferMoneyRequest transferMoneyRequest) {
		logger.trace("Entered transferMoney() method");
		String fromUserPhoneNum = transferMoneyRequest.getFromUser();
		String toUserPhoneNum = transferMoneyRequest.getToUser();
		double amountToTransfer = transferMoneyRequest.getAmountToBeTransferred();
		if(amountToTransfer<=0)
		{
			logger.trace(WalletConstants.ADD_MONEY_GREATER_THAN_ZERO);
			throw new AmountNotValidException(WalletConstants.ADD_MONEY_GREATER_THAN_ZERO);
		}
		if (fromUserPhoneNum.equals(toUserPhoneNum)) {
			logger.trace("User want to add money in own account");
			throw new TransferMoneyTransactionFailureException("Money Cant be Transfered to self account");
		}
		if (!isExistingUser(toUserPhoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException("You can't transfer money to non-registered user on Wallet");
		}
		Account fromUser = accountDao.getById(fromUserPhoneNum);
		Account toUser = accountDao.getById(toUserPhoneNum);
		double fromUserCurBal = fromUser.getWalletBalance();
		double toUserCurBal = toUser.getWalletBalance();
		if (amountToTransfer > fromUserCurBal) {
			throw  new TransferMoneyTransactionFailureException("Insufficient funds");
		}
		toUserCurBal += amountToTransfer;
		fromUserCurBal -= amountToTransfer;
		fromUser.setWalletBalance(fromUserCurBal);
		toUser.setWalletBalance(toUserCurBal);
		accountDao.save(fromUser);
		accountDao.save(toUser);
		transferMoneyTransaction(transferMoneyRequest);
		logger.trace("transferMoney() method execution completion....");
		return amountToTransfer + " transferred successfully to " + toUserPhoneNum + " .Your current balance is "
				+ fromUserCurBal;
	}
	
	/**
	 *This method will create UserWalletTransaction Object
	 */
	private UserWalletTransaction getUserWalletTransactionObj() {
		return new UserWalletTransaction();
	}
	
	/**
	 *This method will save add money transaction
	 */
	private void addMoneyTransaction(Account account, double amountToAdd) {
		logger.trace("Entered addMoneyTransaction() method");
		String phoneNum = account.getPhoneNum();
		UserWalletTransaction userWalletTransaction = getUserWalletTransactionObj();
		userWalletTransaction.setAmount(amountToAdd);
		userWalletTransaction.setFromUser(phoneNum);
		userWalletTransaction.setTransactionType(WalletConstants.TRANSACTION_TYPE_ADD_MONEY);
		userWalletTransaction.setTransactionDate(new Date());
		walletTransactionDao.save(userWalletTransaction);
		logger.trace("addMoneyTransaction() method execution completion....");
	}
	
	/**
	 *This method will save transfer money transaction
	 */
	private void transferMoneyTransaction(TransferMoneyRequest transferMoneyRequest) {
		logger.trace("Entered transferMoneyTransaction() method");
		String fromUserPhoneNum = transferMoneyRequest.getFromUser();
		String toUserPhoneNum = transferMoneyRequest.getToUser();
		double amountToTransfer = transferMoneyRequest.getAmountToBeTransferred();
		UserWalletTransaction userWalletTransaction = getUserWalletTransactionObj();
		userWalletTransaction.setAmount(amountToTransfer);
		userWalletTransaction.setFromUser(fromUserPhoneNum);
		userWalletTransaction.setToUser(toUserPhoneNum);
		userWalletTransaction.setTransactionType(WalletConstants.TRANSACTION_TYPE_TRANSFER_MONEY);
		userWalletTransaction.setTransactionDate(new Date());
		walletTransactionDao.save(userWalletTransaction);
		logger.trace("transferMoneyTransaction() method execution completion....");
	}

	/**
	 *This method will get user's all transactions
	 */
	@Override
	public List<UserWalletTransaction> getAllTransactionForUser(String userId) {
		logger.trace("Entered getAllTransactionForUser() method.....");
		List<UserWalletTransaction> list = walletTransactionDao.getAllTransactionForUser(userId);
		logger.trace("getAllTransactionForUser() method execution completion....");
		return list;
	}
}
