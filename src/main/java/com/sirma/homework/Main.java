package com.sirma.homework;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sirma.homework.model.Employee;
import com.sirma.homework.model.EmployeesWorkingTogether;

import com.sirma.homework.files.ReadDataFromFile;

import com.sirma.homework.service.EmployeesService;

/**
 * Main class of the program.
 */
public class Main {

    public static void main(String[] args) {
        EmployeesService employeesService = new EmployeesService();

        Map<Integer, List<Employee>> mapProjectEmployees = ReadDataFromFile.getDataInMap();
        Set<EmployeesWorkingTogether> result = employeesService.getEmployeesWorkingTogether(mapProjectEmployees);

        System.out.println("The 2 employees, who have worked together the most are:");
        result.stream().sorted().limit(1).forEach(System.out::println);
    }
}
