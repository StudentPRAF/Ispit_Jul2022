package rs.raf.student.jul_2022.database;

import rs.raf.student.jul_2022.model.Package;
import rs.raf.student.jul_2022.model.Status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class DbPackages {

    private static final String fullFilePath = "resources/paketi.txt";
    private static final String outFilePath = "resources/";

    private static final List<Package> packages = new ArrayList<>();

    //region Load Packages

    static void load() {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(fullFilePath));

            while (scanner.hasNext()) {
                String[] data = scanner.nextLine().split(",|-|;");
                packages.add(new Package(data[0], data[1], data[2], Status.getValueOf(data[3])));
                Database.Cities.add(data[1], data[2]);
            }

            Database.Cities.sort();
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        finally {
            if (scanner != null)
                scanner.close();
        }
    }

    //endregion

    //region Getters

    public static List<Package> get() {
        return packages;
    }

    //endregion

    //region Filter Packages

    public static List<Package> filter(String text, String status) {
        List<Package> filteredList = new ArrayList<>();

        for (Package package1 : packages)
            if (hasFilterText(package1, text.toLowerCase()) && hasFilterStatus(package1, status))
                filteredList.add(package1);

        return filteredList;
    }

    public static boolean hasFilterText(Package package1, String text) {
        return text.isEmpty() || package1.getId().toLowerCase().startsWith(text) || package1.getFromCity().toLowerCase().startsWith(text) ||
               package1.getToCity().toLowerCase().startsWith(text);
    }

    public static boolean hasFilterStatus(Package package1, String status) {
        return status.equalsIgnoreCase("Svi statusi") || package1.getStatus().equals(Status.getValueOf(status));
    }

    //endregion

    //region Change Package Status

    public static String setStatusToSent(Package package1) {
        if (!package1.getStatus().equals(Status.READY))
            return "Samo paketi sa statusom \"Spreman\" mogu biti poslati!";

        package1.setStatus(Status.SENT);

        return "";
    }

    public static String setStatusToReceived(Package package1) {
        if (!package1.getStatus().equals(Status.SENT))
            return "Samo paketi sa statusom \"Poslat\" mogu biti primljeni!";

        package1.setStatus(Status.RECEIVED);

        return "";
    }

    public static String setStatusToReturned(Package package1) {
        if (!package1.getStatus().equals(Status.SENT))
            return "Samo paketi sa statusom \"Poslat\" mogu biti vraceni!";

        package1.setStatus(Status.RETURNED);

        return "";
    }

    //endregion

    //region Save Packages

    public static void save(String city) {
        String outFileName = "izlaz" + (city.equals("Svi gradovi") ? "" : ("-" + city)) + ".txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath + outFileName));

            List<Package> sortedPackages = sortAndFilterByCity(city);

            for (Status status : Status.values()) {
                writer.write(status.toSaveLabel());

                List<Package> sortedPackagesForStatus = filterByStatus(sortedPackages, status);
                writer.write(" (" + sortedPackagesForStatus.size() + ")\n");

                for (Package package1 : sortedPackagesForStatus)
                    writer.write(package1 + "\n");

                if (!status.equals(Status.RETURNED))
                    writer.write('\n');
            }

            writer.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static List<Package> sortAndFilterByCity(String city) {
        List<Package> sortedPackages = new ArrayList<>();

        for (Package package1 : packages)
            if (package1.getFromCity().equals(city) || package1.getToCity().equals(city) || city.equals("Svi gradovi"))
                sortedPackages.add(package1);

        Collections.sort(sortedPackages);

        return sortedPackages;
    }

    private static List<Package> filterByStatus(List<Package> sortedPackages, Status status) {
        List<Package> sortedPackagesByStatus = new ArrayList<>();

        Iterator<Package> iterator = sortedPackages.iterator();
        Package currentPackage;

        while(iterator.hasNext() && (currentPackage = iterator.next()).getStatus().equals(status)) {
            sortedPackagesByStatus.add(currentPackage);
            iterator.remove();
        }

        return sortedPackagesByStatus;
    }

    //endregion

}
