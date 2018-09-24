import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleCsvParser {


    public SimpleCsvParser() {
    }


    public void readwrite(String sPath, CSVPrinter csvPrinter, String fileDate) {
        String fName;
        String lName;
        String stNumber;
        String street;
        String city;
        String province;
        String postalCode;
        String country;
        String phoneNo;
        String emailAddress;
        try {
            java.io.Reader in = new FileReader(sPath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            ArrayList<String[]> data = new ArrayList();
            for (CSVRecord record : records) {


                if (record.getRecordNumber()>1) {

                    try {
                        fName = record.get(0);
                        lName = record.get(1);
                        stNumber = record.get(2);
                        street = record.get(3);
                        city = record.get(4);
                        province = record.get(5);
                        postalCode = record.get(6);
                        country = record.get(7);
                        phoneNo = record.get(8);
                        emailAddress = record.get(9);
                        if ((fName != null) && (!"".equals(fName.trim())) && (lName != null) && (!"".equals(lName.trim())) &&
                                (stNumber != null) && (!"".equals(stNumber.trim())) &&
                                (street != null) && (!"".equals(street.trim())) &&
                                (city != null) && (!"".equals(city.trim())) &&
                                (province != null) && (!"".equals(province.trim())) &&
                                (postalCode != null) && (!"".equals(postalCode.trim())) &&
                                (country != null) && (!"".equals(country.trim())) &&
                                (phoneNo != null) && (!"".equals(phoneNo.trim())) &&
                                (emailAddress != null) && (!"".equals(emailAddress.trim()))) {
                            data.add(new String[]{fName, lName, stNumber, street, city, province, postalCode, country,
                                    phoneNo, emailAddress, fileDate});
                            Start.validRowCounter += 1;
                        } else {

                            throw new Exception("Counted as Skipped Row");
                        }
                    } catch (Exception e) {

                        Start.skippedRowCounter += 1;
                    }
                }

            }
            csvPrinter.printRecords(data);
        } catch (IOException localIOException) {
        } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
        }
    }

}
