import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

public class Start {

    private static final String[] FIELDS = {"First Name", "Last Name", "Street Number", "Street", "City", "Province",
            "Postal Code", "Country", "Phone Number", "email Address", "Date"};
    private static final String dirPath = "/Users/parijatbandyopadhyay/Documents/GitHub/A00430847_MCDA5510/Sample Data";
    static SimpleLogging logger = new SimpleLogging();
    public static int skippedRowCounter = 0;
    public static int validRowCounter = 0;

    public Start() {
    }


    public static void main(String[] args)
            throws IOException {
        BufferedWriter writer;
        CSVPrinter csvPrinter = null;
        DriveWalker dw = new DriveWalker();
        try {
            writer = Files.newBufferedWriter(Paths.get("./Output/ConsolidatedData.csv", new String[0]), new OpenOption[0]);
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(FIELDS));
            long startTime = System.currentTimeMillis();
            dw.walkDir(dirPath, csvPrinter);
            long endTime = System.currentTimeMillis();
            logger.writetoLogFile("Total execution time: " + (endTime - startTime) + " ms");
            logger.writetoLogFile("Total number of valid rows: " + validRowCounter);
            logger.writetoLogFile("Total number of skipped rows: " + skippedRowCounter);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            csvPrinter.flush();
        }
    }


}
