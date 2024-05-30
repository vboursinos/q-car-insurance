import ai.turintech.RunQ;
import ai.turintech.RunQFromMethod;
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
import java.util.Arrays;
import java.util.List;


public class KdbTest {
    private static c con = null;

    @BeforeAll
    public static void setUp() throws IOException, c.KException {
        Dotenv dotenv = Dotenv.configure().load();

        String kdbHost = dotenv.get("KDB_HOST");
        String kdbPort = dotenv.get("KDB_PORT");

        con = new c(kdbHost, Integer.parseInt(kdbPort));

        RunQ.createInitTable(con);
    }

    @Test
    public void testCode1() throws IOException, c.KException {
        int expectedColumnSize = 11;
        int expectedRowCount = 4444445;
        List<String> columnNames = Arrays.asList("id", "city", "country", "pop", "localTime", "continent", "region", "avgTemperature", "capital", "currency", "popUpdated");

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

        long count = (long) con.k("count joinTable");
        System.out.println("Count of joinTable: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }

    }

    @Test
    public void testCode2() throws IOException, c.KException {
        int expectedColumnSize = 9;
        int expectedRowCount = 8000000;
        List<String> columnNames = Arrays.asList("id", "firstName", "lastName", "sales", "saleId", "age", "saleDate", "saleAmount", "dummy");

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

        long count = (long) con.k("count joinTable2");
        System.out.println("Count of joinTable: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @Test
    public void testCode3() throws IOException, c.KException {
        int expectedColumnSize = 4;
        int expectedRowCount = 3;
        List<String> columnNames = Arrays.asList("dt", "sym", "book", "size");

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

        long count = (long) con.k("count fbTrades");
        System.out.println("Count of fbTrades: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @Test
    public void testCode4() throws IOException, c.KException {
        int expectedColumnSize = 11;
        int expectedRowCount = 4444445;
        List<String> columnNames = Arrays.asList("id", "city", "country", "pop", "localTime", "continent", "region", "avgTemperature", "capital", "currency", "popUpdated");

        Path path = Paths.get("src/main/resources/q/query_4.q");
        String query = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        RunQ.executeScript(query, con);
        c.Flip table = (c.Flip) con.k("10#joinTable");
        for (int i = 0; i < c.n(table.y[0]); i++) {
            for (int j = 0; j < table.y.length; j++) {
                System.out.print(c.at(table.y[j], i) + " ");
            }
            System.out.println();
        }

        long count = (long) con.k("count joinTable");
        System.out.println("Count of joinTable: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @Test
    public void testCode1FromMethod() throws c.KException, IOException {
        int expectedColumnSize = 11;
        int expectedRowCount = 4444445;
        List<String> columnNames = Arrays.asList("id", "city", "country", "pop", "localTime", "continent", "region", "avgTemperature", "capital", "currency", "popUpdated");

        try {
            RunQFromMethod.joinCityCountry(con);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.Flip table;

        table = (c.Flip) con.k("10#joinTable");

        long count = (long) con.k("count joinTable");
        System.out.println("Count of joinTable: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @Test
    public void testCode2FromMethod() throws c.KException, IOException {
        int expectedColumnSize = 9;
        int expectedRowCount = 8000000;
        List<String> columnNames = Arrays.asList("id", "firstName", "lastName", "sales", "saleId", "age", "saleDate", "saleAmount", "dummy");

        try {
            RunQFromMethod.joinPersonSale(con);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.Flip table;

        table = (c.Flip) con.k("10#joinTable2");

        long count = (long) con.k("count joinTable2");
        System.out.println("Count of joinTable2: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @Test
    public void testCode3FromMethod() throws c.KException, IOException {
        int expectedColumnSize = 7;
        int expectedRowCount = 1;
        List<String> columnNames = Arrays.asList("dt", "sym", "dummy", "size", "price", "sector", "employees");

        try {
            RunQFromMethod.joinTradeStock(con);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.Flip table;

        table = (c.Flip) con.k("10#joinTable3");

        long count = (long) con.k("count joinTable3");
        System.out.println("Count of joinTable3: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @Test
    public void testCode4FromMethod() throws c.KException, IOException {
        int expectedColumnSize = 10;
        int expectedRowCount = 4444445;
        List<String> columnNames = Arrays.asList("id", "city", "country", "pop", "localTime", "continent", "region", "avgTemperature", "capital", "currency");


        try {
            RunQFromMethod.joinCityCountry2(con);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.Flip table;

        table = (c.Flip) con.k("10#joinTable4");
        for (int i = 0; i < c.n(table.y[0]); i++) {
            for (int j = 0; j < table.y.length; j++) {
                System.out.print(c.at(table.y[j], i) + " ");
            }
            System.out.println();
        }

        long count = (long) con.k("count joinTable4");
        System.out.println("Count of joinTable4: " + count);

        Assertions.assertTrue(count == expectedRowCount);

        int columnSize = table.y.length;
        System.out.println("Column size: " + columnSize);
        Assertions.assertTrue(columnSize == expectedColumnSize);

        for (Object column : table.x) {
            System.out.println(column);
            String columnName = (String) column;
            Assertions.assertTrue(columnNames.contains(columnName));
        }
    }

    @AfterAll
    public static void tearDown() throws IOException, c.KException {
        // close connection
        if (con != null) {
            con.close();
        }
    }
}
