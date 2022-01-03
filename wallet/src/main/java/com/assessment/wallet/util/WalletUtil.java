package com.assessment.wallet.util;

import com.assessment.wallet.constants.WalletConstants;
import com.assessment.wallet.Model.Account;
import com.assessment.wallet.exception.ValidationFailureException;

public class WalletUtil {

    public static boolean ValidateAccountDetails(Account account) {
        String phoneNum = account.getPhoneNum();
        String userName = account.getName();
        String userPassword = account.getUserPassword();
        return validatePhoneNum(phoneNum) && validateUserName(userName)
				&& validateUserPassword(userPassword);
    }

    public static boolean validatePhoneNum(String phoneNum) {
        if (!phoneNum.matches(WalletConstants.PHONE_NUM_REGEX)) {
            throw new ValidationFailureException(WalletConstants.PHONE_NUM_INVALID);
        }
        return true;
    }

    public static boolean validateUserName(String userName) {
        if (null == userName || userName.isEmpty()) {
            throw new ValidationFailureException(WalletConstants.USER_NAME_INVALID);
        }
        return true;
    }

    public static boolean validateUserPassword(String password) {
        if (null == password || password.isEmpty()) {
            throw new ValidationFailureException(WalletConstants.PASSWORD_BLANK);
        }
        if (password.length() < WalletConstants.PASSWORD_MIN_LEN || password.length() > WalletConstants.PASSWORD_MAX_LEN) {
            throw new ValidationFailureException(WalletConstants.PASSWORD_SIZE_INVALID);
        }
        return true;
    }

}
