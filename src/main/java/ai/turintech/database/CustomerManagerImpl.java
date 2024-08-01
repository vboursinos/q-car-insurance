package ai.turintech.database;

import ai.turintech.executor.ScriptExecutor;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CustomerManagerImpl implements CustomerManager{

    @Autowired
    private ScriptExecutor scriptExecutor;
    private static final Logger logger = Logger.getLogger(CustomerManagerImpl.class.getName());
    private static final Dotenv dotenv = Dotenv.load();
    private static final String kdbHost = dotenv.get("KDB_HOST");
    private static final String kdbPort = dotenv.get("KDB_PORT");
    private static final String SCRIPT_PATH = "q/createCustomerTable.q";


    public void createTable() {
        initializeDatabase();
        printAllCustomers();
    }

    public void initializeDatabase() {
        try {
            String qScript = new String(Files.readAllBytes(Paths.get(SCRIPT_PATH)));
            scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
            logger.info("Customer table created successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize the database", e);
        }
    }

    public void printAllCustomers() {
        c con = null;
        try {
            con = new c(kdbHost, Integer.parseInt(kdbPort));
            Object result = con.k("select from customers");

            if (result instanceof c.Flip) {
                c.Flip table = (c.Flip) result;
                String[] columnNames = table.x;
                logger.info("Printing customer data...");
                for (String columnName : columnNames) {
                    logger.info(columnName);
                }
                Object[] columns = table.y;

                // Handling the columns based on expected types
                long[] ids = (long[]) columns[0];
                Object[] names = (Object[]) columns[1];
                Object[] surnames = (Object[]) columns[2];
                Object[] countries = (Object[]) columns[3];
                long[] ages = (long[]) columns[4];
                Object[] localTimes = (Object[]) columns[5];
                Object[] telNums = (Object[]) columns[6];
                Object[] regions = (Object[]) columns[7];
                long[] productIds = (long[]) columns[8];

                for (int i = 0; i < ids.length; i++) {
                    int id = (int) ids[i];
                    String name = names[i].toString();
                    String surname = surnames[i].toString();
                    String country = countries[i].toString();
                    long age = ages[i];
                    String localTime = localTimes[i].toString();
                    String telNum = telNums[i].toString();
                    String region = regions[i].toString();
                    long productId = productIds[i];
                    if (telNum.equals("null")) {
                        telNum = "N/A";
                    }
                    if (region.startsWith("S")) {
                        logger.info(String.format("ID: %d, Name: %s, Surname: %s, Country: %s, Age: %d, Local Time: %s, Tel Num: %s, Region: %s, Product ID: %d",
                                id, name, surname, country, age, localTime, telNum, region, productId));
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to retrieve customer data", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to close kdb+ connection", e);
                }
            }
        }
    }
}