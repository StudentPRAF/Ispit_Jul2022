package rs.raf.student.jul_2022.model;

public class Package implements Comparable<Package> {

    private String id;
    private String fromCity;
    private String toCity;
    private Status status;

    public Package(String id, String fromCity, String toCity, Status status) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.status = status;
    }

    //region Getters and Setters

    public String getId() {
        return id;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    //endregion

    //region Comparable Interface

    @Override
    public int compareTo(Package package1) {
        return 8 * compareStatus(package1) + 4 * compareFromCity(package1) + 2 * compareToCity(package1) + compareId(package1);
    }

    private int compareStatus(Package package1) {
        return Integer.signum(status.ordinal() - package1.getStatus().ordinal());
    }

    private int compareFromCity(Package package1) {
        return Integer.signum(fromCity.compareTo(package1.getFromCity()));
    }

    private int compareToCity(Package package1) {
        return Integer.signum(toCity.compareTo(package1.getToCity()));
    }

    private int compareId(Package package1) {
        return Integer.signum(id.compareTo(package1.getId()));
    }

    //endregion

    //region Object Methods

    @Override
    public String toString() {
        return id + "," + fromCity + "," + toCity;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Package))
            return false;

        Package package1 = (Package) obj;
        return this.id.equals(package1.getId());
    }

    //endregion

}
