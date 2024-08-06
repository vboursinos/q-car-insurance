import ai.turintech.executor.ScriptExecutor;
import ai.turintech.reports.costructorReports.CitroenCustomer;
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
public class CitroenCustomerTest {
  private static c con = null;
  private static String kdbHost;
  private static String kdbPort;

  @Autowired private CitroenCustomer citroenCustomer;

  @Autowired private ScriptExecutor scriptExecutor;

  @BeforeAll
  public static void setUp() throws IOException, c.KException {
    Dotenv dotenv = Dotenv.configure().load();

    kdbHost = dotenv.get("KDB_HOST");
    kdbPort = dotenv.get("KDB_PORT");

    con = new c(kdbHost, Integer.parseInt(kdbPort));
  }

  @Test
  public void testCreateModelReport() {
    citroenCustomer.createModelReport();
    String customerQuery = "select from citroen_customers";
    Object result = scriptExecutor.executeQScriptWithReturn(customerQuery, kdbHost, kdbPort);

    Assertions.assertNotNull(result);
    Assertions.assertTrue(result instanceof c.Flip);

    c.Flip table = (c.Flip) result;
    String[] columnNames = table.x;
    Assertions.assertEquals(19, columnNames.length);
  }

  @Test
  public void testCreateModelYoungCustomerReport() {
    citroenCustomer.createModelYoungCustomerReport();
    String customerQuery = "select from young_citroen_customers";
    Object result = scriptExecutor.executeQScriptWithReturn(customerQuery, kdbHost, kdbPort);

    Assertions.assertNotNull(result);
    Assertions.assertTrue(result instanceof c.Flip);

    c.Flip table = (c.Flip) result;
    String[] columnNames = table.x;
    Assertions.assertEquals(19, columnNames.length);
  }

  @Test
  public void testCreateModelAveragePricePerCountryReport() {
    citroenCustomer.createModelAvgPriceReport();
    String customerQuery = "select from complicated_avg_price_by_country";
    Object result = scriptExecutor.executeQScriptWithReturn(customerQuery, kdbHost, kdbPort);

    Assertions.assertNotNull(result);
    Assertions.assertTrue(result instanceof c.Flip);

    c.Flip table = (c.Flip) result;
    String[] columnNames = table.x;
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
