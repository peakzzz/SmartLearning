package edu.sjsu.assess.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.sjsu.assess.model.User;

public class Utility {

	public static boolean isValidUserObject(User usr) {

		if (usr != null && !StringUtils.isBlank(usr.getFname())
				&& !StringUtils.isBlank(usr.getLogin())
				&& !StringUtils.isBlank(usr.getPassword())) {
			return true;
		}
/*&& !StringUtils.isBlank(usr.getRole())*/
		return false;
	}

	public static String getLoggedInUserName() {
		String userName = null;
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();

			if(auth != null){
				org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth
						.getPrincipal();
	
				userName = user.getUsername();
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: log error
		}

		//return loggedInUser != null ? loggedInUser.getId() : null;
		return userName;
	}

	public static boolean isValidString(String str) {
		return (str!=null && !str.equals(""));
	}

	public static boolean isValidInteger(String n) {
		boolean isValid = (n!=null && !n.equals(""));
		try {
			int number = Integer.parseInt(n);
			return isValid && true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public static long getDateLongFromStr(String dateStr) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		long birthDateLong = 0;
		Date date;
		try {
			date = dateFormat.parse(dateStr);
			birthDateLong = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return birthDateLong;
	}
	
	public static String getDateStrFromLong(long dateLong) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date(dateLong);
		return dateFormat.format(date);
	}
	
//	public static Date getDateObjFromLong(long dateLong){
//		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//		Date date = new Date(dateLong);
//		String dateStr = dateFormat.format(date);
//		Date toReturn = null;
//		try {
//			toReturn = dateFormat.parse(dateStr);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return toReturn;
//	}

	public static String convertToCommaSeparatedString(List<Integer> list){
		
		if(list != null && list.size() > 0){
			return StringUtils.join(list, ',');
		}
		
		return "";
	}
	
	public static List<Integer> convertToListOfIntegers(String commaSeparatedStr){
		
		List<Integer> toReturn;
		if(commaSeparatedStr != null && commaSeparatedStr != ""){
			toReturn = new ArrayList<Integer>();
			List<String> items = Arrays.asList(commaSeparatedStr.split(","));
			for(String item : items){
				toReturn.add(Integer.parseInt(item));
			}
			
			return toReturn;
		}
		
		return null;
	}

}
