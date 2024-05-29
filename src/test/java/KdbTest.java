import ai.turintech.RunQ;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;


public class KdbTest {
    private static c con = null;
    private static StringWriter consoleOutput;
    private static String resultFilePath = "src/main/resources/results/";

    @BeforeAll
    public static void setUp() throws SQLException, IOException, c.KException {
        Dotenv dotenv = Dotenv.configure().load();

        String kdbHost = dotenv.get("KDB_HOST");
        String kdbPort = dotenv.get("KDB_PORT");

        con = new c(kdbHost, Integer.parseInt(kdbPort));

        // Create init tables
        RunQ.createInitTable(con);
    }

    @Test
    public void testCode1() throws SQLException, IOException, c.KException {
        long startTime = System.currentTimeMillis();
        Path path = Paths.get("src/main/resources/q/query_1.q");
        String query = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        RunQ.executeScript(query, con);
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        System.out.println("Duration: " + duration + "s");
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

        Assertions.assertTrue(count == 4444445);
    }

    @Test
    public void testCode2() throws SQLException, IOException, c.KException {
        long startTime = System.currentTimeMillis();
        Path path = Paths.get("src/main/resources/q/query_2.q");
        String query = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        RunQ.executeScript(query, con);
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        System.out.println("Duration: " + duration + "s");
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

        Assertions.assertTrue(count == 12000000);
    }
}
