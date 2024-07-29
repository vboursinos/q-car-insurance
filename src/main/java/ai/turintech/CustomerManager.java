package ai.turintech;

import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerManager {

    private static final Logger logger = Logger.getLogger(CustomerManager.class.getName());
    private static final Dotenv dotenv = Dotenv.load();
    private static final String JDBC_URL = dotenv.get("JDBC_URL");
    private static final String kdbHost = dotenv.get("KDB_HOST");
    private static final String kdbPort = dotenv.get("KDB_PORT");
    private static final String SCRIPT_PATH = "src/main/resources/scripts/createCustomerTable.q";


    public static void main(String[] args) {
        CustomerManager manager = new CustomerManager();
        manager.initializeDatabase();
        manager.printAllCustomers();
    }

    private void initializeDatabase() {
        try {
            String qScript = new String(Files.readAllBytes(Paths.get(SCRIPT_PATH)));
            executeQScript(qScript);
            logger.info("Customer table created successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize the database", e);
        }
    }

    private void printAllCustomers() {
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
                long[] pops = (long[]) columns[4];
                Object[] localTimes = (Object[]) columns[5];
                Object[] telNums = (Object[]) columns[6];
                Object[] regions = (Object[]) columns[7];
//                int[] ages = (int[]) columns[8];

                for (int i = 0; i < ids.length; i++) {
                    int id = (int) ids[i];
                    String name = names[i].toString();
                    String surname = surnames[i].toString();
                    String country = countries[i].toString();
                    long pop = pops[i];
                    String localTime = localTimes[i].toString();
                    String telNum = telNums[i].toString();
                    String region = regions[i].toString();
//                    int age = ages[i];
                    if (telNum.equals("null")) {
                        telNum = "N/A";
                    }
                    if (region.startsWith("S")) {
                        logger.info(String.format("ID: %d, Name: %s, Surname: %s, Country: %s, Population: %d, Local Time: %s, Tel Num: %s, Region: %s",
                                id, name, surname, country, pop, localTime, telNum, region));
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

    private void executeQScript(String qScript) {
        c con = null;
        try {
            con = new c(kdbHost, Integer.parseInt(kdbPort));
            logger.info("Executing Q script...");
            logger.info(qScript);
            con.k(qScript);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to execute Q script", e);
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