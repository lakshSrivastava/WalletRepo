package com.assessment.wallet.controller;

import java.util.List;

import com.assessment.wallet.Model.*;
import com.assessment.wallet.service.MyUserDetailsService;
import com.assessment.wallet.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.assessment.wallet.service.WalletService;
import com.assessment.wallet.util.WalletUtil;

@RestController
public class WalletController {

    Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    //API for creating JWT token
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/account/signup")
    public String addUserWallet(@RequestBody Account account) {
        logger.trace("Entered addUserWallet() method.....");
        WalletUtil.ValidateAccountDetails(account);
        String response = walletService.addUser(account);
        logger.trace("addUserWallet() method execution completion....");
        return response;
    }

    @GetMapping("/account/login")
    public String loginUser(@RequestBody Account account) {
        logger.trace("Entered loginUser() method.....");
        WalletUtil.ValidateAccountDetails(account);
        String response = walletService.loginUser(account);
        logger.trace("loginUser() method execution completion....");
        return response;
    }

    @GetMapping("/account/user/{phoneNum}")
    public String getUser(@PathVariable String phoneNum) {
        logger.trace("Entered getUser() method.....");
        String response = walletService.getUser(phoneNum);
        logger.trace("getUser() method execution completion....");
        return response;
    }

    @PutMapping("/account/user")
    public String updateUser(@RequestBody Account account) {
        logger.trace("Entered updateUser() method.....");
        String response = walletService.updateUser(account);
        logger.trace("updateUser() method execution completion....");
        return response;
    }

    @PutMapping("/wallet/addmoney")
    public String addMoney(@RequestBody Account account, double amountToAdd) {
        logger.trace("Entered addMoney() method.....");
        String response = walletService.addMoney(account, amountToAdd);
        logger.trace("addMoney() method execution completion....");
        return response;
    }

    @PutMapping("/wallet/transfermoney")
    public String transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) {
        logger.trace("Entered transferMoney() method.....");
        String response = walletService.transferMoney(transferMoneyRequest);
        logger.trace("transferMoney() method execution completion....");
        return response;
    }

    @GetMapping("/wallet/transactions/{userId}")
    public List<UserWalletTransaction> getAllTransactionForUser(@PathVariable String userId) {
        logger.trace("Entered getUserTransactions() method.....");
        List<UserWalletTransaction> list = walletService.getAllTransactionForUser(userId);
        logger.trace("getUserTransactions() method execution completion....");
        return list;
    }

}
