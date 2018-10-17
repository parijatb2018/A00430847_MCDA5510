
/**
 * Original source code from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
**/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Scanner;

public class MySQLAccess {

	static SimpleLogging logger = new SimpleLogging();

	public Connection setupConnection() throws Exception {

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment2?"

					+ "user=root&password=Johnty@2016" + "&useSSL=false"
					+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		} catch (Exception e) {
			throw e;
		} finally {

		}

		logger.writetoLogFile("Connection Sucessful");
		return connection;
	}

	// -----------------------------------create---------------------------------------//

	public boolean createTransaction(Connection connection) throws SQLException {

		int count = 0;
		String CardType = "";
		Statement statement = null;
		ResultSet resultSet = null;
		String expiryDate = null;

		try {
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			String username = System.getProperty("user.name");
			Scanner in = new Scanner(System.in);
			String cardNumber = null;
			String nameOnCard;
			System.out.println("Enter your ID: ");
			int id = in.nextInt();
			while (resultSet.next()) {
				int id_chk = resultSet.getInt("id");
				if (id == id_chk) {
					count++;

				}
			}
			if (count == 0) {

				System.out.println("Enter your name on card: ");
				nameOnCard = in.next();
				if (nameOnCard.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid name, special character not allowed");
					logger.writetoLogFile("Invalid name entered");
					return false;
				}

				System.out.println("Enter Card Number: ");// card no input and validation

				while (in.hasNext()) {

					cardNumber = in.next();

					int cardchk = 0;

					if (cardNumber.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("enter correct card no, special characters not allowed:");
						logger.writetoLogFile("Invalid card number entered");
						cardchk = 1;
					}

					else if ((cardNumber.length() > 16) || (cardNumber.length() < 15)) {

						System.out.println("enter correct card no, card lenght is either 15 or 16:");
						logger.writetoLogFile("Invalid card number entered");
						cardchk = 1;

					}

					if (cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53")
							|| cardNumber.startsWith("54") || cardNumber.startsWith("55") || cardNumber.startsWith("4")
							|| cardNumber.startsWith("34") || cardNumber.startsWith("37")) {

						cardchk = 0;
					} else {

						System.out.println("Enter a Visa / Master/ American Express Card");
						logger.writetoLogFile("Invalid card number entered");
						cardchk = 1;
					}

					if (cardchk == 0)
						if (cardNumber.matches("^[5][1-5].*") && cardNumber.length() == 16) {
							CardType = "MasterCard";
							cardchk = 2;
						} else if (cardNumber.matches("^[4].*") && cardNumber.length() == 16) {
							CardType = "Visa";
							cardchk = 2;
						} else if (cardNumber.matches("^[3][4|7].*") && cardNumber.length() == 15) {
							CardType = "American Express";
							cardchk = 2;
						}

					if (cardchk == 2)
						break;
				}

				System.out.println("UnitPrice: ");
				float price = in.nextFloat();
				System.out.println("Quantity: ");
				int quantity = in.nextInt();

				boolean flag = true; //expiry date mm/yyyy  input and validation

				while (flag) {

					System.out.println("ExpiryDate(mm/yyyy):");
					expiryDate = in.next();

					if (7 != expiryDate.length()) {

						System.out.println("Invalid expiryDate entered, date out of bound");
						logger.writetoLogFile("Invalid expiryDate entered");

					}

					else {

						String s1 = expiryDate.substring(0, 2);
						String s2 = expiryDate.substring(3, 7);

						System.out.println(s1);
						System.out.println(s2);

						if (expiryDate.matches(".*[;:!@#$%^*+?<>].*") || 2 != s1.length() || 4 != s2.length()
								|| Integer.parseInt(s1) < 1 || Integer.parseInt(s1) > 12 || Integer.parseInt(s2) < 2016
								|| Integer.parseInt(s2) > 2031) {

							System.out.println("Invalid expiryDate entered, date out of bound");
							logger.writetoLogFile("Invalid expiryDate entered");

						}

						else {

							flag = false;

						}

					}
				}

				float totalPrice = price * quantity;

				PreparedStatement preparedStatement = connection.prepareStatement(
						"insert into  transaction values (?, ?, ?, ? , ?, ?,?,now(),'" + username + "',?)");

				// Parameters start with 1
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, nameOnCard);
				preparedStatement.setString(3, cardNumber);
				preparedStatement.setFloat(4, price);
				preparedStatement.setInt(5, quantity);
				preparedStatement.setFloat(6, totalPrice);
				preparedStatement.setString(7, expiryDate);
				preparedStatement.setString(8, CardType);
				preparedStatement.executeUpdate();
				System.out.println("A Transaction record Created Successfully");

			} else {
				System.out.println("The id already exist,Press (y) to update the row with this id");
				char option = in.next().charAt(0);
				if (option == 'y') {
					updateTransaction(id, connection);
				}
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}
			}

		} catch (

		SQLException e) {
			logger.writetoLogFile(e.toString());
			e.printStackTrace();
		} finally {
			statement = null;
			resultSet = null;
		}
		logger.writetoLogFile("Create Transaction Successful");
		return true;
	}

	// ----------------------get--------------------------//

	public Transaction getTransaction(int id, Connection connection) throws SQLException {
		Transaction trxn = new Transaction();

		int count = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = null;
		Scanner in = new Scanner(System.in);

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			while (resultSet.next()) {
				int idmatch = resultSet.getInt(1);
				if (id == idmatch) {
					statement = connection.createStatement();
					trxn.setId(resultSet.getInt("id"));
					trxn.setNameOnCard(resultSet.getString("NameOnCard"));
					trxn.setCardNumber(resultSet.getString("CardNumber"));
					trxn.setPrice(resultSet.getFloat("UnitPrice"));
					trxn.setQuantity(resultSet.getInt("Quantity"));
					trxn.setTotalPrice(resultSet.getFloat("totalprice"));
					trxn.setExpiryDate(resultSet.getString("ExpiryDate"));
					trxn.setCreatedBy(resultSet.getString("CreatedBy"));
					trxn.setCardNumber(resultSet.getString("CardNumber"));

				}

			}
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			logger.writetoLogFile(e.toString());
			e.printStackTrace();
		}
		logger.writetoLogFile("Transaction Successfully obtained");
		return trxn;

	}

	// --------------------------update--------------------------------//

	public boolean updateTransaction(int id, Connection connection) throws SQLException {
		int count = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;

		Scanner in = new Scanner(System.in);

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			while (resultSet.next()) {
				int idmatch = resultSet.getInt(1);
				if (id == idmatch) {
					count++;

				}
			}
			if (count == 0) {
				System.out.println("The id doesnt exist,Press (y) to create a new row with this id");
				char option = in.next().charAt(0);
				if (option == 'y') {
					createTransaction(connection);
				}
			} else {
				System.out.println(
						"Select the option \n 1.New ID \n 2.New Name on Card \n 3.New Card Number \n 4.New Price \n 5.New Quantity \n 6.New Expiry Date");
				System.out.println("What field you want to update (1-6)");
				int entry = in.nextInt();
				if (entry == 1) {

					System.out.println("Enter new ID:");
					int user_id = in.nextInt();
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set ID=? WHERE ID=?");
					preparestatement.setInt(1, user_id);
					preparestatement.setInt(2, id);
					preparestatement.execute();
				} else if (entry == 2) {

					System.out.println("Enter new Name on card");
					String user_value = in.next();
					if (user_value.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid name");
						return false;
					}
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set NameOnCard=? WHERE ID=?");
					preparestatement.setString(1, user_value);
					preparestatement.setInt(2, id);
					preparestatement.execute();

				} else if (entry == 3) {

					String CardType = null;
					String cardNumber;

					System.out.println("Enter New Card Number: ");

					while (in.hasNext()) {

						cardNumber = in.next();

						int cardchk = 0;

						if (cardNumber.matches(".*[;:!@#$%^*+?<>].*")) {
							System.out.println("enter correct card no, special characters not allowed:");
							logger.writetoLogFile("Invalid card number entered");
							cardchk = 1;
						}

						else if ((cardNumber.length() > 16) || (cardNumber.length() < 15)) {

							System.out.println("enter correct card no, card lenght is either 15 or 16:");
							logger.writetoLogFile("Invalid card number entered");
							cardchk = 1;

						}

						if (cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53")
								|| cardNumber.startsWith("54") || cardNumber.startsWith("55")
								|| cardNumber.startsWith("4") || cardNumber.startsWith("34")
								|| cardNumber.startsWith("37")) {

							cardchk = 0;
						} else {

							System.out.println("Enter a Visa / Master/ American Express Card");
							logger.writetoLogFile("Invalid card number entered");
							cardchk = 1;
						}

						if (cardchk == 0)
							if (cardNumber.matches("^[5][1-5].*") && cardNumber.length() == 16) {
								CardType = "MasterCard";
								cardchk = 2;
							} else if (cardNumber.matches("^[4].*") && cardNumber.length() == 16) {
								CardType = "Visa";
								cardchk = 2;
							} else if (cardNumber.matches("^[3][4|7].*") && cardNumber.length() == 15) {
								CardType = "American Express";
								cardchk = 2;
							}

						if (cardchk == 2)
							break;
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set CardNumber=?, CardType=? WHERE ID=?");
						preparestatement.setString(1, cardNumber);
						preparestatement.setString(2, CardType);
						preparestatement.setInt(3, id);
						preparestatement.execute();
					}
				}

				else if (entry == 4) {
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + id);
					while (resultSet1.next()) {
						System.out.println("Enter new Price");
						float user_value = in.nextFloat();

						float total_price = resultSet1.getFloat(5) * user_value;

						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set UnitPrice=?, TotalPrice=? WHERE ID=?");

						preparestatement.setFloat(1, user_value);
						preparestatement.setFloat(2, total_price);
						preparestatement.setInt(3, id);
						preparestatement.execute();
					}

				} else if (entry == 5) {
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + id);
					while (resultSet1.next()) {
						System.out.println("Enter new Quantity");
						int user_value = in.nextInt();
						float total_price = resultSet1.getFloat(4) * user_value;
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set Quantity=?, TotalPrice=? WHERE ID=?");
						preparestatement.setInt(1, user_value);
						preparestatement.setFloat(2, total_price);
						preparestatement.setInt(3, id);
						preparestatement.execute();
					}
				} else if (entry == 6) {

					boolean flag = true;
					String expiryDate = null;

					while (flag) {

						System.out.println("ExpiryDate(mm/yyyy):");
						expiryDate = in.next();

						if (7 != expiryDate.length()) {

							System.out.println("Invalid expiryDate entered, date out of bound");
							logger.writetoLogFile("Invalid expiryDate entered");							

						}

						else {

							String s1 = expiryDate.substring(0, 2);
							String s2 = expiryDate.substring(3, 7);

							System.out.println(s1);
							System.out.println(s2);

							if (expiryDate.matches(".*[;:!@#$%^*+?<>].*") || 2 != s1.length() || 4 != s2.length()
									|| Integer.parseInt(s1) < 1 || Integer.parseInt(s1) > 12
									|| Integer.parseInt(s2) < 2016 || Integer.parseInt(s2) > 2031) {

								System.out.println("Invalid expiryDate entered, date out of bound");
								logger.writetoLogFile("Invalid expiryDate entered");

							}

							else {

								flag = false;

							}

						}
					}

					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set ExpiryDate=? WHERE ID=?");
					preparestatement.setString(1, expiryDate);

					preparestatement.setInt(2, id);
					preparestatement.execute();
					System.out.println("Updated Successfully");
				}

			}

		} catch (SQLException e) {
			logger.writetoLogFile(e.toString());
			e.printStackTrace();
		}
		logger.writetoLogFile("record successfully updated");
		return true;
	}

	// --------------------remove--------------------------//

	public boolean removeTransaction(int id, Connection connection) throws SQLException {
		try {
			Scanner in = new Scanner(System.in);
			Statement statement = null;
			ResultSet resultSet = null;
			int count = 0;
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			while (resultSet.next()) {
				int idmatch = resultSet.getInt(1);
				if (id == idmatch) {
					count++;

				}
			}
			if (count == 0) {

				System.out.println("wrong ID");

			} else {

				PreparedStatement preparestatement = connection.prepareStatement("Delete from transaction where ID=?");
				preparestatement.setInt(1, id);
				preparestatement.execute();
				System.out.println("Delete Exectuted Successfully");
			}

		} catch (SQLException e) {
			logger.writetoLogFile(e.toString());
			e.printStackTrace();
		}
		logger.writetoLogFile("record successfully removed");
		return true;
	}

}
