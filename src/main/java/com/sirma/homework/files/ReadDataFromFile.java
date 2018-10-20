package com.sirma.homework.files;

import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.sirma.homework.model.Employee;

/**
 * Read data from file and mapping it to model.
 */
public class ReadDataFromFile {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String READER_DELIMITER = ", |\\r\\n";
    private static final String VALUE_FOR_TODAY = "NULL";
    private static final String FILE_NAME = "exampledata.txt";

    /**
     * Read data from file, mapping in to Project and Employees.
     * Get Default file, if file is not present.
     * @return map with key projectId and value list of employees who works on that project.
     */
    public static Map<Integer, List<Employee>> getDataInMap() {
        return readData(getFile());
    }

    /**
     * Read data from file, mapping in to Project and Employees.
     * @param file from where we read the data.
     * @return map with key projectId and value list of employees who works on that project.
     */
    public static Map<Integer, List<Employee>> getDataInMap(final File file) {
        return readData(file);
    }

    private static Map<Integer, List<Employee>> readData(File file) {
        Map<Integer, List<Employee>> mapProjectIdEmployees = new HashMap<>();
        try (Scanner input = new Scanner(file)) {
            input.useDelimiter(READER_DELIMITER);
            while (input.hasNext()) {
                Employee employee = createEmployeeOfData(input);
                mapProjectIdEmployees.putIfAbsent(employee.getProjectId(), new ArrayList<>());
                mapProjectIdEmployees.get(employee.getProjectId()).add(employee);
            }
        } catch (FileNotFoundException e) {
            //TODO : Log this exception.
            e.printStackTrace();
        }

        return mapProjectIdEmployees;
    }

    private static Employee createEmployeeOfData(final Scanner input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        Integer emplId = input.nextInt();
        Integer projectId = input.nextInt();
        ChronoLocalDate dateFrom = LocalDate.parse(input.next(), formatter);
        ChronoLocalDate dateTo = parseDateTo(input.next(), formatter);

        return new Employee(emplId, projectId, dateFrom, dateTo);
    }

    private static ChronoLocalDate parseDateTo(final String dateToString, final DateTimeFormatter formatter) {
        return dateToString.equals(VALUE_FOR_TODAY) ? LocalDate.now() : LocalDate.parse(dateToString, formatter);
    }

    private static File getFile() {
        ClassLoader  classLoader = ReadDataFromFile.class.getClassLoader();
        return new File(classLoader.getResource(FILE_NAME).getFile());
    }
}
