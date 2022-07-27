package rs.raf.student.jul_2022.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbCities {

    private static final List<String> cities = new ArrayList<>();

    static void add(String... cities) {
        for (String city : cities)
            add(city);
    }

    static void add(String city) {
        if (!cities.contains(city))
            cities.add(city);
    }

    static void sort() {
        Collections.sort(cities);
    }

    public static List<String> get() {
        return cities;
    }

}
