package rs.raf.student.jul_2022.model;

import java.util.ArrayList;
import java.util.List;

public enum Status {

    READY("Spreman", "Spremni paketi"),
    SENT("Poslat", "Poslati paketi"),
    RECEIVED("Primljen", "Primljeni paketi"),
    RETURNED("Vracen", "Vraceni paketi");

    private String string;
    private String saveString;

    Status(String string, String saveString) {
        this.string = string;
        this.saveString = saveString;
    }

    public static Status getValueOf(String value) {
        if (value.equalsIgnoreCase("Spreman"))
            return READY;

        if (value.equalsIgnoreCase("Poslat"))
            return SENT;

        if (value.equalsIgnoreCase("Primljen"))
            return RECEIVED;

        return RETURNED;
    }

    public static List<String> toStringList() {
        List<String> list = new ArrayList<>();

        for (Status status : Status.values())
            list.add(status.toString());

        return list;
    }

    @Override
    public String toString() {
        return string;
    }

    public String toSaveLabel() {
        return saveString;
    }

}
