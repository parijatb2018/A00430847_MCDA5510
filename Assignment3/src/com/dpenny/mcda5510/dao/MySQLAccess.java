package com.dpenny.mcda5510.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import com.dpenny.mcda5510.entity.Transaction;
import com.dpenny.mcda5510.log.SimpleLogging;

public class MySQLAccess {
	static SimpleLogging logger = new SimpleLogging();
	
	
	//-------create transaction-------------------//

	public boolean createTransaction(Transaction transaction, Connection connection) throws Exception {
		boolean flag = false;
		try {
			if (!existingtranx(transaction, connection)) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("insert into  transaction values (?, ?, ?, ?, ? , ?, ?,now() , ?, ?)");
				preparedStatement.setInt(1, transaction.getId());
				preparedStatement.setString(2, transaction.getNameOnCard());
				preparedStatement.setString(3, transaction.getCardNumber());
				preparedStatement.setDouble(4, transaction.getUnitPrice());
				preparedStatement.setInt(5, transaction.getQuantity());
				preparedStatement.setDouble(6, transaction.getTotalPrice());
				preparedStatement.setString(7, transaction.getExpDate());
				preparedStatement.setString(8, transaction.getCreatedBy());
				preparedStatement.setString(9, transaction.getCardType());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				flag = true;
				System.out.println("Card details has been added successfully!");
			} else {
				logger.writetoLogFile("card already exists! update details");
				
				return flag;
			}
		} catch (SQLIntegrityConstraintViolationException ex) {
			logger.writetoLogFile(ex.toString());
			
		} catch (Exception ex) {
			
			logger.writetoLogFile(ex.toString());
		}
		return flag;
	}
	
	//-------------update transaction-----------//

	public boolean updateTransaction(Transaction transaction, Connection connection) throws Exception {
		boolean flag = false;
		try {
			if (existingtranx(transaction, connection)) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"update transaction Set NameOnCard = ?, CardNumber = ?, UnitPrice = ?, Quantity = ?, TotalPrice = ?, ExpDate = ? where ID = ?");
				preparedStatement.setString(1, transaction.getNameOnCard());
				preparedStatement.setString(2, transaction.getCardNumber());
				preparedStatement.setDouble(3, transaction.getUnitPrice());
				preparedStatement.setInt(4, transaction.getQuantity());
				preparedStatement.setDouble(5, transaction.getTotalPrice());
				preparedStatement.setString(6, transaction.getExpDate());
				preparedStatement.setInt(7, transaction.getId());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				System.out.println("Card details has been updated successfully!");
				flag = true;
			} else {
				throw new Exception("card does not exist. add it frst!");
			}
		} catch (Exception ex) {
			
			logger.writetoLogFile(ex.toString());
			return flag;
		}
		return flag;
	}
	
	

	
	//------------------------get transaction-----------------//

	public Transaction getTransaction(int ID, Transaction transaction, Connection connection) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where ID = ?");
			preparedStatement.setInt(1, ID);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				transaction.setId(Integer.parseInt(result.getString("ID")));
				transaction.setNameOnCard(result.getString("NameOnCard"));
				transaction.setCardNumber(result.getString("CardNumber"));
				transaction.setUnitPrice(Double.parseDouble(result.getString("UnitPrice")));
				transaction.setQuantity(Integer.parseInt(result.getString("Quantity")));
				transaction.setTotalPrice(Double.parseDouble(result.getString("TotalPrice")));
				transaction.setExpDate(result.getString("ExpDate"));
				transaction.setCreatedOn(result.getString("CreatedOn"));
				transaction.setCreatedBy(result.getString("CreatedBy"));
				transaction.setCardType(result.getString("CardType"));

				System.out.println("Your Record is\nID: " + transaction.getId() + "\nName On Card: "
						+ transaction.getNameOnCard() + "\nCard Number: " + transaction.getCardNumber()
						+ "\nUnit Price : " + transaction.getUnitPrice() + "\nQuantity : " + transaction.getQuantity()
						+ "\nTotal Price : " + transaction.getTotalPrice() + "\nExpiry Date : "
						+ transaction.getExpDate() + "\nCreated On : " + transaction.getCreatedOn() + "\nCreated By : "
						+ transaction.getCreatedBy() + "\nCard Type: " + transaction.getCardType() + "\n\n");
			}
			if (null == transaction.getCreatedOn())
				System.out.println("No card found for thid ID!");
			preparedStatement.close();
		} catch (Exception ex) {
			
			logger.writetoLogFile(ex.toString());
		}
		return transaction;
	}
	
	



	//----------------remove transaction----------------//
	
	public boolean removeTransaction(int trxnID, Connection connection) throws Exception {
		boolean flag = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from transaction where ID = ?");
			preparedStatement.setInt(1, trxnID);
			preparedStatement.executeUpdate();
			flag = true;
			System.out.println("Card removed Succesfully");
			preparedStatement.close();
		} catch (Exception ex) {
			
			logger.writetoLogFile(ex.toString());
		}
		return flag;
	}
	
	//-----------------checking transaction validity------------------//
	
	private boolean existingtranx(Transaction transaction, Connection connection) throws Exception {
		boolean flag = false;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select count(*) as idcheck  from transaction where ID = ?");
			preparedStatement.setInt(1, transaction.getId());
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				if (1 == result.getInt("idcheck")) {
					flag = true;
				}
			}
		} catch (Exception ex) {
			
			logger.writetoLogFile(ex.toString());
		}
		return flag;
	}

}