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


    String cityTable = ""
                       + "city2:([] id:1+til 10000000; city:10000000#`Istanbul`Moscow`London`StPetersburg`Berlin`Madrid`Rome`Athens`Lisbon; "
                       + "country:10000000#`Turkey`Russia`UK`Russia`Germany`Spain`Italy`Greece`Portugal; pop:10000000#15067724 12615279 9126366 5383890 3750000 3256000 2800000 3100000 300000; "
                       + "localTime:10000000#07:00 12:00 07:00 12:00 09:00 10:00 10:00 11:00 11:00; "
                       + "continent:10000000#`Asia`Europe`Europe`Europe`Europe`Europe`Europe`Europe`Europe; "
                       + "region:10000000#`Marmara`Central`Southeast`Northwest`Northeast`Central`South`Central`LisbonRegion; "
                       + "avgTemperature:10000000#15.0 20.0 11.0 2.0 1.5 16.0 15.5 18.0 17.0)";

    public static void main(String[] args) {
        CustomerManager manager = new CustomerManager();
        manager.initializeDatabase();
//        manager.printAllCustomers();
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
                Object[] columns = table.y;

                // Assuming columns are of type Object[] where each object is a primitive array
                long[] ids = (long[]) columns[0];
                Object[] names = (Object[]) columns[1];
                long[] ages = (long[]) columns[2];
                Object[] emails = (Object[]) columns[3];

                for (int i = 0; i < ids.length; i++) {
                    int id = (int) ids[i]; // cast long to int
                    String name = names[i].toString(); // symbols are converted to String
                    int age = (int) ages[i]; // cast long to int
                    String email = emails[i].toString(); // symbols are converted to String

                    logger.info(String.format("ID: %d, Name: %s, Age: %d, Email: %s", id, name, age, email));
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