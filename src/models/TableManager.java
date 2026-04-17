package models;

import java.util.ArrayList;

public class TableManager {

    public static ArrayList<TableInfo> tables = new ArrayList<>();

    // helper
    public static TableInfo getTable(String name) {
        for (TableInfo t : tables) {
            if (t.getTableName().equals(name)) {
                return t;
            }
        }
        return null;
    }
}
