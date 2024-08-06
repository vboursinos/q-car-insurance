package ai.turintech.reports.costructorReports;

import ai.turintech.executor.ScriptExecutor;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitroenCustomerImpl implements CitroenCustomer {

  @Autowired private ScriptExecutor scriptExecutor;
  private static final Logger logger = Logger.getLogger(CitroenCustomerImpl.class.getName());
  private static final Dotenv dotenv = Dotenv.load();
  private static final String kdbHost = dotenv.get("KDB_HOST");
  private static final String kdbPort = dotenv.get("KDB_PORT");
  private static final String CUSTOMERS_SCRIPT_PATH = "q/citroenCustomers.q";
  private static final String YOUNG_CUSTOMERS_SCRIPT_PATH = "q/youngCitroenCustomers.q";
  private static final String AVG_PRICE_SCRIPT_PATH = "q/averagePricePerCountryCitroen.q";

  public void createModelReport() {
    try {
      String qScript = new String(Files.readAllBytes(Paths.get(CUSTOMERS_SCRIPT_PATH)));
      scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
      Object result =
          scriptExecutor.executeQScriptWithReturn(
              "select from citroen_customers", kdbHost, kdbPort);
      if (result instanceof c.Flip) {
        c.Flip table = (c.Flip) result;
        String[] columnNames = table.x;
        logger.info("Printing citroen data...");
        for (String columnName : columnNames) {
          logger.info(columnName);
        }
        Object[] columns = table.y;
        long[] ids = (long[]) columns[0];
        Object[] names = (Object[]) columns[1];
        Object[] surnames = (Object[]) columns[2];
        for (int i = 0; i < 100; i++) {
          int id = (int) ids[i];
          String name = names[i].toString();
          String surname = surnames[i].toString();
          if (surname.startsWith("B")) {
            logger.info(String.format("ID: %d, Name: %s", id, name));
          }
        }
      }
      logger.info("Citroen Customer table created successfully.");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to initialize the database", e);
    }
  }

  public void createModelYoungCustomerReport() {
    try {
      String qScript = new String(Files.readAllBytes(Paths.get(YOUNG_CUSTOMERS_SCRIPT_PATH)));
      scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
      Object result =
          scriptExecutor.executeQScriptWithReturn(
              "select from young_citroen_customers", kdbHost, kdbPort);
      if (result instanceof c.Flip) {
        c.Flip table = (c.Flip) result;
        String[] columnNames = table.x;
        logger.info("Printing young customer citroen data...");
        for (String columnName : columnNames) {
          logger.info(columnName);
        }
        Object[] columns = table.y;
        long[] ids = (long[]) columns[0];
        Object[] names = (Object[]) columns[1];
        Object[] surnames = (Object[]) columns[2];
        long[] ages = (long[]) columns[4];
        for (int i = 0; i < ids.length / 10; i++) {
          int id = (int) ids[i];
          String name = names[i].toString();
          String surname = surnames[i].toString();
          long age = ages[i];
          logger.info(
              String.format("ID: %d, Name: %s, Surname: %s, Age: %d", id, name, surname, age));
        }
      }
      logger.info("Young Citroen Customer table created successfully.");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to initialize the database", e);
    }
  }

  public void createModelAvgPriceReport() {
    logger.info("Creating average price report for Citroen customers...");
    try {
      String qScript = new String(Files.readAllBytes(Paths.get(AVG_PRICE_SCRIPT_PATH)));
      scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
      Object result =
          scriptExecutor.executeQScriptWithReturn(
              "select from complicated_avg_price_by_country", kdbHost, kdbPort);
      if (result instanceof c.Flip) {
        c.Flip table = (c.Flip) result;
        String[] columnNames = table.x;
        logger.info("Printing average price data...");
        for (String columnName : columnNames) {
          logger.info(columnName);
        }
      }
      logger.info("Average price report for Citroen customers created successfully.");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to initialize the database", e);
    }
  }
}
