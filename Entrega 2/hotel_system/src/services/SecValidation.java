package services;

import hotel_system.utils.Utils;

public class SecValidation {
	private static final String REGEX_USER="^[a-zA-Z][-\\w!]{3,11}$"; 
	private static final String REGEX_PASSWORD="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+,\\-./:;<=>?@\\[\\]^_`{|}~])[A-Za-z\\d!@#$%^&*()_+,\\-./:;<=>?@\\[\\]^_`{|}~]{6,14}$"; 
	private static final String REGEX_DATE="^\\d{4}[/-]\\d{1,2}[/-]\\d{1,2}$"; 

	public static boolean checkUser(String user){
		return user.matches(REGEX_USER);
	}
	public static boolean checkPassword(String password){
		return password.matches(REGEX_PASSWORD);
	}
	public static boolean checkDate(String date) {
		date = new String(date.replace("-", "/"));
		return date.matches(REGEX_DATE) && Utils.stringToDate(date).compareTo(Utils.nowDate()) >= 0 ;
	}
	
}
