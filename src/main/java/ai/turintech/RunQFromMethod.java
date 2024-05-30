package ai.turintech;

import com.kx.c;

import java.io.IOException;

public class RunQFromMethod {
    public static void joinCityCountry(c con) throws IOException {
        String query = "joinTable: aj[`id;\n" +
                       "    select from\n" +
                       "        update popUpdated: pop + 100 from\n" +
                       "        update pop: pop + 100 from\n" +
                       "            select from\n" +
                       "                update pop: pop + 100 from\n" +
                       "                    select from city\n" +
                       "                where pop > 4000000 - 200;\n" +
                       "`id xkey country]";
        con.ks(query);
    }

    public static void joinPersonSale(c con) throws IOException {
        String query = "joinTable2: aj[`id;\n" +
                       "    select from\n" +
                       "        update id: id, sales: sales + 0, dummy: sales * age from \n" +
                       "            (select from\n" +
                       "                update age: age + 0 from \n" +
                       "                    (select from\n" +
                       "                        (aj[`id;\n" +
                       "                            select from person where sales > 502,\n" +
                       "                            age > 52; `id xkey (sale)\n" +
                       "                        ])\n" +
                       "                    ) where sales > 500, age > 50\n" +
                       "            );\n" +
                       "    `id xkey (sale)]";
        con.ks(query);
    }

    public static void joinTradeStock(c con) throws IOException {
        String query = "joinTable3: aj[`sym;\n" +
                       "    select from\n" +
                       "        update sym: sym, price: price + 0, dummy: price * size from \n" +
                       "            (select from\n" +
                       "                update size: size + 0 from\n" +
                       "                    (select from\n" +
                       "                        (aj[`sym; \n" +
                       "                            select from trade where price > 1000,\n" +
                       "                            size > 100; `sym xkey (stock)\n" +
                       "                        ])\n" +
                       "                    ) where price > 100, size > 10\n" +
                       "            );\n" +
                       "    `sym xkey (stock)]";
        con.ks(query);
    }

    public static void joinCityCountry2(c con) throws IOException {
        String query = "joinTable4:aj[`id;select from (city) where pop > 4000000; `id xkey (country)]";
        con.ks(query);
    }
}
