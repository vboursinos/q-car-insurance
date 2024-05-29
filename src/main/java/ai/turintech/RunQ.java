package ai.turintech;

import com.kx.c;

import java.io.IOException;

public class RunQ {
    public static void createInitTable(c con) throws IOException {
        // Added localTime, continent, region, and avgTemperature columns
        String cityTable = ""
                                   + "city:([] id:1+til 10000000; city:10000000#`Istanbul`Moscow`London`StPetersburg`Berlin`Madrid`Rome`Athens`Lisbon; "
                                   + "country:10000000#`Turkey`Russia`UK`Russia`Germany`Spain`Italy`Greece`Portugal; pop:10000000#15067724 12615279 9126366 5383890 3750000 3256000 2800000 3100000 300000; "
                                   + "localTime:10000000#07:00 12:00 07:00 12:00 09:00 10:00 10:00 11:00 11:00; "
                                   + "continent:10000000#`Asia`Europe`Europe`Europe`Europe`Europe`Europe`Europe`Europe; "
                                   + "region:10000000#`Marmara`Central`Southeast`Northwest`Northeast`Central`South`Central`LisbonRegion; "
                                   + "avgTemperature:10000000#15.0 20.0 11.0 2.0 1.5 16.0 15.5 18.0 17.0)";
        con.ks(cityTable);

        // create the second table named largeTable2 with aligned data
        String countryTable = "country:([] id:1+til 10000000; capital:10000000#`Istanbul`Moscow`London`StPetersburg`Berlin`Madrid`Rome`Athens`Lisbon; "
                                   + "currency:10000000#`Lira`Ruble`Pound`Ruble`Euro`Euro`Euro`Euro`Euro)";
        con.ks(countryTable);

        String personTable = "person:([] id:1+til 20000000; firstName:20000000#`John`Bill`Karen`Jill`Frank`Laura`Henry`Liam`Emma`Noah; "
                             + "lastName:20000000#`Smith`Johnson`Williams`Jones`Brown`Davis`Miller`Wilson`Moore`Taylor; "
                             + "sales:20000000#300.25 400.75 250.50 600.0 870.99 450.0 560.35 789.0 650.85 900.50; "
                             + "saleId:20000000#1 2 3 4 5 6 7 8 9 10; "
                             + "age:20000000#30 35 40 45 50 55 60 65 70 75)";
        con.ks(personTable);

        String saleTable = "sale:([] id:1+til 10;"
                           + "saleDate:10#2021.01.01 2021.01.02 2021.01.03 2021.01.04 2021.01.05 2021.01.06 2021.01.07 2021.01.08 2021.01.09 2021.01.10; "
                           + "saleAmount:10#300.25 400.75 250.50 600.0 870.99 450.0 560.35 789.0 650.85 900.50)";
        con.ks(saleTable);
    }

    public static void executeScript(String query, c con) throws IOException {
        con.ks(query);
    }
}
