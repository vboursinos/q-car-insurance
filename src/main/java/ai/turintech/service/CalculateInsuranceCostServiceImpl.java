package ai.turintech.service;

import ai.turintech.executor.ScriptExecutor;
import ai.turintech.reports.ReportSummaryImpl;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculateInsuranceCostServiceImpl implements CalculateInsuranceCostService {

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
  }
}
