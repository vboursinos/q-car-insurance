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
public class ProductManagerImpl implements ProductManager{

    @Autowired
    private ScriptExecutor scriptExecutor;
    private static final Logger logger = Logger.getLogger(ProductManagerImpl.class.getName());
    private static final Dotenv dotenv = Dotenv.load();
    private static final String kdbHost = dotenv.get("KDB_HOST");
    private static final String kdbPort = dotenv.get("KDB_PORT");
    private static final String SCRIPT_PATH = "src/main/resources/scripts/createProductTable.q";


    public void createTable() {
        initializeDatabase();
        printAllProducts();
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

    public void printAllProducts() {
        c con = null;
        try {
            con = new c(kdbHost, Integer.parseInt(kdbPort));
            Object result = con.k("select from product");

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
                Object[] constructors = (Object[]) columns[1];
                long[] prices = (long[]) columns[2];
                double[] engineSizes = (double[]) columns[3];

                for (int i = 0; i < ids.length; i++) {
                    int id = (int) ids[i];
                    String constructor = constructors[i].toString();
                    long price = prices[i];
                    double engineSize = engineSizes[i];
                    logger.info(String.format("ID: %d, Constructor: %s, Price: %d, Engine Size: %f",
                            id, constructor, price, engineSize));
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