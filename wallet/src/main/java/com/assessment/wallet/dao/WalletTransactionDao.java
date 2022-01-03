package com.assessment.wallet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.assessment.wallet.Model.UserWalletTransaction;

public interface WalletTransactionDao extends JpaRepository<UserWalletTransaction, Long>{
	
	@Query("select trans from user_wallet_transaction trans where uwt.fromUser = ?1 order by trans.transactionDate desc")
	public List<UserWalletTransaction> getAllTransactionForUser(String fromUser);

}
