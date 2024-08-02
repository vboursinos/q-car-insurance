package ai.turintech.reports;

import ai.turintech.executor.ScriptExecutor;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ReportSummaryImpl implements ReportSummary {
    @Autowired
    private ScriptExecutor scriptExecutor;

    private static final Logger logger = Logger.getLogger(ReportSummaryImpl.class.getName());
    private static final Dotenv dotenv = Dotenv.load();
    private static final String kdbHost = dotenv.get("KDB_HOST");
    private static final String kdbPort = dotenv.get("KDB_PORT");
    private static final String SHOW_CUSTOMER_DETAILS_SCRIPT_PATH = "q/showCustomerDetails.q";


    public void createReportSummary() {
        List<Object> results = new ArrayList<>();
        try {
            String qScript = new String(Files.readAllBytes(Paths.get(SHOW_CUSTOMER_DETAILS_SCRIPT_PATH)));
            scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
            Object greek_customers =
                    scriptExecutor.executeQScriptWithReturn(
                            "select from greek_customers", kdbHost, kdbPort);
            results.add(greek_customers);
            Object uk_customers =
                    scriptExecutor.executeQScriptWithReturn(
                            "select from uk_customers", kdbHost, kdbPort);
            results.add(uk_customers);
            results.forEach(this::printTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize the database", e);
        }
    }

    private void printTable(Object result) {
        if (result instanceof c.Flip) {
            c.Flip table = (c.Flip) result;
            String[] columnNames = table.x;
            logger.info("Printing greek customers data...");
            for (String columnName : columnNames) {
                logger.info(columnName);
            }
            Object[] columns = table.y;
            long[] ids = (long[]) columns[0];
            Object[] names = (Object[]) columns[1];
            Object[] surnames = (Object[]) columns[2];
            Object[] countries = (Object[]) columns[3];
            for (int i = 0; i < 100; i++) {
                int id = (int) ids[i];
                String name = names[i].toString();
                String surname = surnames[i].toString();
                String country = countries[i].toString();
                if (surname.startsWith("B")) {
                    logger.info(String.format("ID: %d, Name: %s, Country: %s", id, name, country));
                }
            }
        }
        logger.info("Greek Customer table created successfully.");
    }

}
