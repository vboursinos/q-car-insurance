import ai.turintech.executor.ScriptExecutor;
import ai.turintech.service.CalculateInsuranceCostService;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
public class CalculateInsuranceCostTest {
  private static c con = null;
  private static String kdbHost;
  private static String kdbPort;

  @Autowired private CalculateInsuranceCostService calculateInsuranceCostService;

  @Autowired private ScriptExecutor scriptExecutor;

  @BeforeAll
  public static void setUp() throws IOException, c.KException {
    Dotenv dotenv = Dotenv.configure().load();

    kdbHost = dotenv.get("KDB_HOST");
    kdbPort = dotenv.get("KDB_PORT");

    con = new c(kdbHost, Integer.parseInt(kdbPort));
  }

  @Test
  public void testGetInsuranceCostPerCustomer() throws IOException {
    calculateInsuranceCostService.getInsuranceCostPerCustomer();
    String greekCustomerQuery = "select from insurance_cost_per_customer";
    Object greekResult =
        scriptExecutor.executeQScriptWithReturn(greekCustomerQuery, kdbHost, kdbPort);

    Assertions.assertNotNull(greekResult);
    Assertions.assertTrue(greekResult instanceof c.Flip);

    c.Flip greekCustomerTable = (c.Flip) greekResult;
    String[] columnNames = greekCustomerTable.x;
    Assertions.assertEquals(2, columnNames.length);
  }

  @AfterAll
  public static void tearDown() throws IOException {
    // close connection
    if (con != null) {
      con.close();
    }
  }
}
