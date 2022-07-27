package rs.raf.student.jul_2022.database;

public class Database {

    public static void load() {
        Database.Packages.load();
    }

    public static class Packages extends DbPackages { }

    public static class Cities extends DbCities { }

}
