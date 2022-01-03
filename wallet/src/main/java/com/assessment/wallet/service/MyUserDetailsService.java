package com.assessment.wallet.service;

import com.assessment.wallet.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao ;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.assessment.wallet.Model.User user = userDao.getById(userName);
        if(user == null) throw new UsernameNotFoundException("UserName Not Found");
        return new User(user.getUserName(), user.getUserPassword(),
                new ArrayList<>());
    }
}