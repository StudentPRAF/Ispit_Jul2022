package rs.raf.student.jul_2022;

import rs.raf.student.jul_2022.database.Database;
import rs.raf.student.jul_2022.view.Application;

public class Main {

    public static void main(String[] args) {
        Database.load();
        Application.run(args);
    }

}
