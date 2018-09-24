import org.apache.commons.csv.CSVPrinter;

import java.io.File;

public class DriveWalker {

    SimpleCsvParser scp = new SimpleCsvParser();

    public void walkDir(String path, CSVPrinter csvPrinter) {
        File root = new File(path);

        File[] list = root.listFiles();

        if (list == null)
            return;
        String fileDate = null;
        for (File f : list) {
            if (f.isDirectory()) {
                walkDir(f.getAbsolutePath(), csvPrinter);

            } else if (f.getAbsoluteFile().getAbsolutePath().endsWith(".csv")) {


                String year = f.getParentFile().getParentFile().getParentFile().getName();
                String month = f.getParentFile().getParentFile().getName();
                String day = f.getParentFile().getName();

                fileDate = year + "/" + month + "/" + day;


                scp.readwrite(f.getAbsoluteFile().getPath(), csvPrinter, fileDate);

            }
        }
    }

}
