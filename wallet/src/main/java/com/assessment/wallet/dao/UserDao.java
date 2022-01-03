package com.assessment.wallet.dao;

import com.assessment.wallet.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao  extends JpaRepository<User,String> {
}
