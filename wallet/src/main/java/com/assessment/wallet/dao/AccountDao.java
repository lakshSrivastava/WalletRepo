package com.assessment.wallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.wallet.Model.Account;

public interface AccountDao extends JpaRepository<Account, String>{
	

}
