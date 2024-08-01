import ai.turintech.database.CustomerManager;
import ai.turintech.database.ProductManager;
import ai.turintech.executor.ScriptExecutor;
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
public class ProductTest {
    private static c con = null;
    private static String kdbHost;
    private static String kdbPort;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private ScriptExecutor scriptExecutor;

    @BeforeAll
    public static void setUp() throws IOException, c.KException {
        Dotenv dotenv = Dotenv.configure().load();

        kdbHost = dotenv.get("KDB_HOST");
        kdbPort = dotenv.get("KDB_PORT");

        con = new c(kdbHost, Integer.parseInt(kdbPort));
    }

    @Test
    public void testCreateCustomersTable() {
        productManager.createTable();
        String customerQuery = "select from product";
        Object result = scriptExecutor.executeQScriptWithReturn(customerQuery, kdbHost, kdbPort);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof c.Flip);

        c.Flip table = (c.Flip) result;
        String[] columnNames = table.x;
        Assertions.assertEquals(4, columnNames.length);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        // close connection
        if (con != null) {
            con.close();
        }
    }
}
