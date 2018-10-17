import java.sql.Connection;
import java.util.Scanner;

public class Assignment2 {

	static SimpleLogging logger = new SimpleLogging();

	static MySQLAccess dao = new MySQLAccess();

	public static void main(String[] args) throws Exception {
		try {

			Connection connection = dao.setupConnection();



			while (true) {

				System.out.println(
						" \n Select 1 for creating a record \n Select2 for viewing a record \n Select 3 for deleting a record \n Select 4 for updating a record \n "
								+ "Select 5 to exit ");
				System.out.println("\n Select your option: ");
				Scanner input = new Scanner(System.in);
				int digit = input.nextInt();

				if (digit == 1) {

					logger.writetoLogFile("Testing Create Transaction Function");

					dao.createTransaction(connection);

				}

				else if (digit == 2) {

					logger.writetoLogFile("Testing Get Transaction Function");

					System.out.println("Enter ID: ");
					int id = input.nextInt();
					System.out.println("Enter ID: ");
					Transaction res = dao.getTransaction(id, connection);
					System.out.println(res.toString());
				}

				else if (digit == 3) {
					do {

						logger.writetoLogFile("Testing Remove Transaction Function");
						System.out.println("Enter ID: ");
						int id = input.nextInt();
						dao.removeTransaction(id, connection);
						System.out.println("Press (y) to delete another row");

					} while (input.next().equalsIgnoreCase("y"));

				}

				else if (digit == 4) {

					logger.writetoLogFile("Testing Update Transaction Function");
					System.out.println("Enter your ID: ");
					int id = input.nextInt();
					dao.updateTransaction(id, connection);
				}

				else if (digit == 5) {

					logger.writetoLogFile("Exiting the program");
					System.out.println("Bye bye have a good day");

					break;
				}

			}

		} catch (Exception e) {

			logger.writetoLogFile(e.toString());
			e.printStackTrace();
		}

	}

}