package com.dpenny.mcda5510.service;

import com.dpenny.mcda5510.connect.ConnectionFactory;
import com.dpenny.mcda5510.dao.*;
import com.dpenny.mcda5510.entity.*;

public class TrxnWebService {

	ConnectionFactory conFactory = new ConnectionFactory("mySQLJDBC");
	MySQLAccess dao = new MySQLAccess();

//-------------call to create transaction---------//

	public String createTransaction(int ID, String Name, String cardNumber, double unitPrice, int qty,
			String expiryDate) throws Exception {

		String message = "";

		// ---------validation--------------//

		String cardType = null;

		if (Name.matches(".*[;:!@#$%^*+?<>].*")) {

			message = ("invalid name, special character not allowed");
			return message;

		}

		if (cardNumber.matches(".*[;:!@#$%^*+?<>].*")) {

			message = ("enter correct card no, special characters not allowed:");
			return message;

		}

		if ((cardNumber.length() > 16) || (cardNumber.length() < 15)) {

			message = ("enter correct card no, card lenght is either 15 or 16:");
			return message;

		}

		if (cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53")
				|| cardNumber.startsWith("54") || cardNumber.startsWith("55") || cardNumber.startsWith("4")
				|| cardNumber.startsWith("34") || cardNumber.startsWith("37")) {

		} else {

			message = ("Enter a Visa / Master/ American Express Card");
			return message;
		}

		if (cardNumber.matches("^[5][1-5].*") && cardNumber.length() == 16) {
			cardType = "MasterCard";

		} else if (cardNumber.matches("^[4].*") && cardNumber.length() == 16) {
			cardType = "Visa";

		} else if (cardNumber.matches("^[3][4|7].*") && cardNumber.length() == 15) {
			cardType = "American Express";

		}

		if (7 != expiryDate.length()) {

			message = ("Invalid expiryDate entered, date out of bound");
			return message;

		}

		else {

			String s1 = expiryDate.substring(0, 2);
			String s2 = expiryDate.substring(3, 7);

			System.out.println(s1);
			System.out.println(s2);

			if (expiryDate.matches(".*[;:!@#$%^*+?<>].*") || 2 != s1.length() || 4 != s2.length()
					|| Integer.parseInt(s1) < 1 || Integer.parseInt(s1) > 12 || Integer.parseInt(s2) < 2016
					|| Integer.parseInt(s2) > 2031) {

				message = ("Invalid expiryDate entered, date out of bound");
				return message;

			}

			double totalPrice = unitPrice * qty;

			Transaction transaction = new Transaction();

			transaction.setId(ID);
			transaction.setNameOnCard(Name);
			transaction.setCardNumber(cardNumber);
			transaction.setUnitPrice(unitPrice);
			transaction.setQuantity(qty);
			transaction.setTotalPrice(totalPrice);
			transaction.setExpDate(expiryDate);
			transaction.setCardType(cardType);

			try {
				if (dao.createTransaction(transaction, conFactory.getConnection()))
					message = "Card details added successfully!!";
				else
					message = "Card already exists, Please try update!";

			} catch (Exception ex) {
				throw ex;
			}

		}
		return message;
	}

	// -----call update Transaction---------------//

	public String updateTransaction(int ID, String Name, String cardNumber, double unitPrice, int qty,
			String expiryDate) throws Exception {

		String message = "";

		// ---------validation--------------//

		String cardType = null;

		if (Name.matches(".*[;:!@#$%^*+?<>].*")) {

			return ("invalid name, special character not allowed");

		}

		if (cardNumber.matches(".*[;:!@#$%^*+?<>].*")) {

			return ("enter correct card no, special characters not allowed:");

		}

		if ((cardNumber.length() > 16) || (cardNumber.length() < 15)) {

			return ("enter correct card no, card lenght is either 15 or 16:");

		}

		if (cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53")
				|| cardNumber.startsWith("54") || cardNumber.startsWith("55") || cardNumber.startsWith("4")
				|| cardNumber.startsWith("34") || cardNumber.startsWith("37")) {

		} else {

			return ("Enter a Visa / Master/ American Express Card");
		}

		if (cardNumber.matches("^[5][1-5].*") && cardNumber.length() == 16) {
			cardType = "MasterCard";

		} else if (cardNumber.matches("^[4].*") && cardNumber.length() == 16) {
			cardType = "Visa";

		} else if (cardNumber.matches("^[3][4|7].*") && cardNumber.length() == 15) {
			cardType = "American Express";

		}

		if (7 != expiryDate.length()) {

			return ("Invalid expiryDate entered, date out of bound");

		}

		else {

			String s1 = expiryDate.substring(0, 2);
			String s2 = expiryDate.substring(3, 7);

			System.out.println(s1);
			System.out.println(s2);

			if (expiryDate.matches(".*[;:!@#$%^*+?<>].*") || 2 != s1.length() || 4 != s2.length()
					|| Integer.parseInt(s1) < 1 || Integer.parseInt(s1) > 12 || Integer.parseInt(s2) < 2016
					|| Integer.parseInt(s2) > 2031) {

				return ("Invalid expiryDate entered, date out of bound");

			}
		}

		double totalPrice = unitPrice * qty;

		Transaction transaction = new Transaction();
		transaction.setId(ID);
		transaction.setNameOnCard(Name);
		transaction.setCardNumber(cardNumber);
		transaction.setUnitPrice(unitPrice);
		transaction.setQuantity(qty);
		transaction.setTotalPrice(totalPrice);
		transaction.setExpDate(expiryDate);

		try {
			if (dao.updateTransaction(transaction, conFactory.getConnection()))
				message = "card details updated successfully";
			else
				message = "card details does not exist, add it before updating!";
		} catch (Exception ex) {
			throw ex;
		}

		return message;
	}

	// ------------------get transaction---------------//

	public Transaction getTransaction(int ID) throws Exception {
		return dao.getTransaction(ID, (new Transaction()), conFactory.getConnection());
	}

	// -------------------call to remove transaction-----------//

	public boolean removeTransaction(int ID) throws Exception {
		return dao.removeTransaction(ID, conFactory.getConnection());
	}
}