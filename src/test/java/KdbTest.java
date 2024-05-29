import ai.turintech.RunQ;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class KdbTest {
    private static c con = null;

    @BeforeAll
    public static void setUp() throws IOException, c.KException {
        Dotenv dotenv = Dotenv.configure().load();

        String kdbHost = dotenv.get("KDB_HOST");
        String kdbPort = dotenv.get("KDB_PORT");

        con = new c(kdbHost, Integer.parseInt(kdbPort));

        // Create init tables
        RunQ.createInitTable(con);
    }

    @Test
    public void testCode1() throws IOException, c.KException {
        int expectedColumnSize = 10;
        int expectedRowCount = 4444445;

        Path path = Paths.get("src/main/resources/q/query_1.q");
        String query = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        RunQ.executeScript(query, con);
        c.Flip table = (c.Flip) con.k("10#joinTable");
        for (int i = 0; i < c.n(table.y[0]); i++) {
            for (int j = 0; j < table.y.length; j++) {
                System.out.print(c.at(table.y[j], i) + " ");
            }
            System.out.println();
        }

        // Find the count of the joined table
        long count = (long) con.k("count joinTable");
        System.out.println("Count of joinTable: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);
    }

    @Test
    public void testCode2() throws IOException, c.KException {
        int expectedColumnSize = 9;
        int expectedRowCount = 8000000;

        Path path = Paths.get("src/main/resources/q/query_2.q");
        String query = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        RunQ.executeScript(query, con);
        c.Flip table = (c.Flip) con.k("10#joinTable2");
        for (int i = 0; i < c.n(table.y[0]); i++) {
            for (int j = 0; j < table.y.length; j++) {
                System.out.print(c.at(table.y[j], i) + " ");
            }
            System.out.println();
        }

        // Find the count of the joined table
        long count = (long) con.k("count joinTable2");
        System.out.println("Count of joinTable: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);
    }

    @Test
    public void testCode3() throws IOException, c.KException {
        int expectedColumnSize = 4;
        int expectedRowCount = 3;

        Path path = Paths.get("src/main/resources/q/query_3.q");
        String query = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        RunQ.executeScript(query, con);
        c.Flip table = (c.Flip) con.k("10#fbTrades");
        for (int i = 0; i < c.n(table.y[0]); i++) {
            for (int j = 0; j < table.y.length; j++) {
                System.out.print(c.at(table.y[j], i) + " ");
            }
            System.out.println();
        }

        // Find the count of the joined table
        long count = (long) con.k("count fbTrades");
        System.out.println("Count of fbTrades: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);
    }

    @AfterAll
    public static void tearDown() throws IOException, c.KException {
        // close connection
        if (con != null) {
            con.close();
        }
    }
}
