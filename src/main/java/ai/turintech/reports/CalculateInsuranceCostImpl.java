package ai.turintech.reports;

import ai.turintech.executor.ScriptExecutor;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculateInsuranceCostImpl implements CalculateInsuranceCost {

  @Autowired private ScriptExecutor scriptExecutor;

  private static final Logger logger = Logger.getLogger(ReportSummaryImpl.class.getName());
  private static final Dotenv dotenv = Dotenv.load();
  private static final String kdbHost = dotenv.get("KDB_HOST");
  private static final String kdbPort = dotenv.get("KDB_PORT");
  private static final String SHOW_CUSTOMER_INSURANCE_COST_SCRIPT_PATH = "q/insurance_cost.q";

  public void getInsuranceCostPerCustomer() throws IOException {
    String qScript =
        new String(Files.readAllBytes(Paths.get(SHOW_CUSTOMER_INSURANCE_COST_SCRIPT_PATH)));
    scriptExecutor.executeQScript(qScript, kdbHost, kdbPort);
    Object insuranceCost =
        scriptExecutor.executeQScriptWithReturn(
            "select from insurance_cost_per_customer", kdbHost, kdbPort);
    if (insuranceCost instanceof c.Flip) {
      c.Flip table = (c.Flip) insuranceCost;
      String[] columnNames = table.x;
      logger.info("Printing kia data...");
      for (String columnName : columnNames) {
        logger.info(columnName);
      }
      Object[] columns = table.y;
      long[] ids = (long[]) columns[0];
      double[] costs = (double[]) columns[1];
      for (int i = 0; i < 10; i++) {
        int id = (int) ids[i];
        double cost = costs[i];
        logger.info(String.format("ID: %d, insurance cost: %s", id, cost));
      }
    }
  }
}
