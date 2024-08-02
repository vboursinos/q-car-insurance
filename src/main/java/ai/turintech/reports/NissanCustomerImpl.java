package ai.turintech.reports;

import ai.turintech.executor.ScriptExecutor;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NissanCustomerImpl implements NissanCustomer {

  @Autowired private ScriptExecutor scriptExecutor;
  private static final Logger logger = Logger.getLogger(NissanCustomerImpl.class.getName());
  private static final Dotenv dotenv = Dotenv.load();
  private static final String kdbHost = dotenv.get("KDB_HOST");
  private static final String kdbPort = dotenv.get("KDB_PORT");

  private String getNissanCustomers() {
    return "nissan_customers: select from (customers lj `product_id xkey product) where constructor = `Nissan";
  }
  //  renault_customers: select from (customers lj `product_id xkey product) where constructor =
  // `Renault
  private String getYoungNissanCustomers() {
    return "young_nissan_customers: select from (customers lj `product_id xkey product) where constructor = `Nissan, age < 30";
  }

  public void createModelReport() {
    try {
      String qScript = getNissanCustomers();
      scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
      Object result =
          scriptExecutor.executeQScriptWithReturn("select from nissan_customers", kdbHost, kdbPort);
      if (result instanceof c.Flip) {
        c.Flip table = (c.Flip) result;
        String[] columnNames = table.x;
        logger.info("Printing nissan data...");
        for (String columnName : columnNames) {
          logger.info(columnName);
        }
        Object[] columns = table.y;
        long[] ids = (long[]) columns[0];
        Object[] names = (Object[]) columns[1];
        Object[] surnames = (Object[]) columns[2];
        for (int i = 0; i < 10; i++) {
          int id = (int) ids[i];
          String name = names[i].toString();
          String surname = surnames[i].toString();
          if (surname.startsWith("B")) {
            logger.info(String.format("ID: %d, Name: %s", id, name));
          }
        }
      }
      logger.info("Nissan Customer table created successfully.");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to initialize the database", e);
    }
  }

  public void createModelYoungCustomerReport() {
    try {
      String qScript = getYoungNissanCustomers();
      scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
      Object result =
          scriptExecutor.executeQScriptWithReturn(
              "select from young_nissan_customers", kdbHost, kdbPort);
      if (result instanceof c.Flip) {
        c.Flip table = (c.Flip) result;
        String[] columnNames = table.x;
        logger.info("Printing young customer Nissan data...");
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
      logger.info("Young Nissan Customer table created successfully.");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to initialize the database", e);
    }
  }

  public void createModelAvgPriceReport() {
    logger.info("Not implemented...");
  }
}
