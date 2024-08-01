package ai.turintech.reports;

import ai.turintech.executor.ScriptExecutor;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsPerCustomerImpl implements ProductDetailsPerCustomer {

  @Autowired private ScriptExecutor scriptExecutor;
  private static final Logger logger =
      Logger.getLogger(ProductDetailsPerCustomerImpl.class.getName());
  private static final Dotenv dotenv = Dotenv.load();
  private static final String kdbHost = dotenv.get("KDB_HOST");
  private static final String kdbPort = dotenv.get("KDB_PORT");
  private static final String SCRIPT_PATH = "q/productDetailsPerCustomer.q";

  public void createReport() {
    try {
      String qScript = new String(Files.readAllBytes(Paths.get(SCRIPT_PATH)));
      scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
      logger.info("Customer Product join table created successfully.");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to initialize the database", e);
    }
  }
}
