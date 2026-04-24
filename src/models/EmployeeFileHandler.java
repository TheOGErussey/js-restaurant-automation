package models;

import java.io.*;
import java.util.ArrayList;

public class EmployeeFileHandler {

    private static final String FILE_NAME = "employees.txt";

    public static void saveEmployees(ArrayList<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Employee e : employees) {
                writer.println(
                        e.getName() + "," +
                        e.getRole() + "," +
                        e.getId() + "," +
                        e.getAssignedTables() + "," +
                        e.isAuthorized() + "," +
                        e.getPassword()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Employee> loadEmployees() {
        ArrayList<Employee> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                list.add(new Employee(
                        parts[0], // name
                        parts[2], // username
                        parts[1], // role
                        parts[3], // tables
                        Boolean.parseBoolean(parts[4]),
                        parts.length > 5 ? parts[5] : "123"
                ));

            }

        } catch (Exception _) {

        }

        return list;
    }
}
