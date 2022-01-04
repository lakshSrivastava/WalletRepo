package com.assessment.wallet.constants;

public class WalletConstants {

	public static final String NON_EXISTING_USER = "You are not in our records, please sign-up to create a new Account";
	
	public static final int PASSWORD_MIN_LEN = 3;
	
	public static final int PASSWORD_MAX_LEN = 10;
	
	public static final String TRANSACTION_TYPE_ADD_MONEY = "ADD";
	
	public static final String TRANSACTION_TYPE_TRANSFER_MONEY = "TRANS";
	
	public static final String ADD_MONEY_GREATER_THAN_ZERO = "Please add money greater than 0";
	
	public static final String PHONE_NUM_REGEX = "[1-9][0-9]{9}";

	public static final String PHONE_NUM_INVALID = "Phone num should be 10 digits and can't start with 0";

	public static final String USER_NAME_INVALID = "User Name Can't Be Blank";

	public static final String PASSWORD_BLANK = "Password Can't Be Blank";

	public static final String PASSWORD_SIZE_INVALID = "Password length should be greater than " + WalletConstants.PASSWORD_MIN_LEN +" and less than "+ WalletConstants.PASSWORD_MAX_LEN;

}
