import ai.turintech.executor.ScriptExecutor;
import ai.turintech.reports.CitroenCustomer;
import ai.turintech.reports.ReportSummary;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
public class ReportSummaryTest {
  private static c con = null;
  private static String kdbHost;
  private static String kdbPort;

  @Autowired private ReportSummary reportSummary;

  @Autowired private ScriptExecutor scriptExecutor;

  @BeforeAll
  public static void setUp() throws IOException, c.KException {
    Dotenv dotenv = Dotenv.configure().load();

    kdbHost = dotenv.get("KDB_HOST");
    kdbPort = dotenv.get("KDB_PORT");

    con = new c(kdbHost, Integer.parseInt(kdbPort));
  }

  @Test
  public void testCreateReportSummary() {
    reportSummary.createReportSummary();
    String greekCustomerQuery = "select from greek_customers";
    Object greekResult = scriptExecutor.executeQScriptWithReturn(greekCustomerQuery, kdbHost, kdbPort);

    Assertions.assertNotNull(greekResult);
    Assertions.assertTrue(greekResult instanceof c.Flip);

    c.Flip greekCustomerTable = (c.Flip) greekResult;
    String[] columnNames = greekCustomerTable.x;
    Assertions.assertEquals(9, columnNames.length);

    String ukCustomerQuery = "select from uk_customers";
    Object ukResult = scriptExecutor.executeQScriptWithReturn(ukCustomerQuery, kdbHost, kdbPort);

    Assertions.assertNotNull(ukResult);
    Assertions.assertTrue(ukResult instanceof c.Flip);

    c.Flip ukCustomerTable = (c.Flip) ukResult;
    columnNames = ukCustomerTable.x;
    Assertions.assertEquals(9, columnNames.length);
  }

  @AfterAll
  public static void tearDown() throws IOException {
    // close connection
    if (con != null) {
      con.close();
    }
  }
}
