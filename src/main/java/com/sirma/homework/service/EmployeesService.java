package com.sirma.homework.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

import com.sirma.homework.model.Employee;
import com.sirma.homework.model.EmployeesWorkingTogether;

/**
 * Employees Service.
 */
public class EmployeesService {

    /**
     * Get Employees who have worked together.
     * @param projectIdEmployeesMap map projectid and employees who worked on that project.
     * @return employees who have worked together and how long.
     */
    public Set<EmployeesWorkingTogether> getEmployeesWorkingTogether(Map<Integer, List<Employee>> projectIdEmployeesMap) {
        Set<EmployeesWorkingTogether> result = new HashSet<>();

        for (Map.Entry<Integer, List<Employee>> e : projectIdEmployeesMap.entrySet()) {
            calculateDaysOfEmployeesInOneProject(e.getValue(), result);
        }

        return result;
    }

    private void calculateDaysOfEmployeesInOneProject(List<Employee> employees, Set<EmployeesWorkingTogether> result) {
        for (int index = 0; index < employees.size(); index++) {
            Employee employee = employees.get(index);

            for (int next = index + 1; next < employees.size(); next++) {
                Employee otherEmployee = employees.get(next);

                int days = calculateDaysOfTwoEmployees(employee, otherEmployee);
                saveDays(employee, otherEmployee, days, result);
            }
        }
    }

    private void saveDays(Employee employee, Employee otherEmployee, int days, Set<EmployeesWorkingTogether> result) {
        if (days <= 0) return;

        for (EmployeesWorkingTogether employeesWorkingTogether : result) {
            if (AreTheyHaveWorkedTogetherBefore(employee, otherEmployee, employeesWorkingTogether)) {
                employeesWorkingTogether.setDaysWorkingTogether(days + employeesWorkingTogether.getDaysWorkingTogether());
                return;
            }
        }

        EmployeesWorkingTogether employeesWorkingTogether =
                    new EmployeesWorkingTogether(employee.getEmployeeId(), otherEmployee.getEmployeeId(), days);

        result.add(employeesWorkingTogether);
    }

    private boolean AreTheyHaveWorkedTogetherBefore(Employee employee, Employee otherEmployee, EmployeesWorkingTogether theyWorkTogether) {
        return theyWorkTogether.getEmployeeId() == employee.getEmployeeId() && theyWorkTogether.getOtherEmployeeId() == otherEmployee.getEmployeeId();
    }

    private int calculateDaysOfTwoEmployees(Employee employee, Employee otherEmployee) {
        ChronoLocalDate empStartDate = employee.getDateFrom();
        ChronoLocalDate empFinishDate = employee.getDateTo();

        ChronoLocalDate othEmpStartDate = otherEmployee.getDateFrom();
        ChronoLocalDate othEmpFinishDate = otherEmployee.getDateTo();

        if (!areTheyWorkingTogether(empStartDate, empFinishDate, othEmpStartDate, othEmpFinishDate)) return 0;

        return (int) ChronoUnit.DAYS.between(getStartingDate(empStartDate, othEmpStartDate),
                getLastDate(empFinishDate, othEmpFinishDate));
    }

    private boolean areTheyWorkingTogether(ChronoLocalDate empStartsDate, ChronoLocalDate empFinishDate,
                                           ChronoLocalDate othEmpStartsDate, ChronoLocalDate othEmpFinishDate) {

        return !((empStartsDate.isBefore(othEmpFinishDate) && empFinishDate.isBefore(othEmpStartsDate))
                || (othEmpStartsDate.isBefore(empFinishDate) && othEmpFinishDate.isBefore(empStartsDate)));
    }

    private ChronoLocalDate getStartingDate(ChronoLocalDate empStartsDate, ChronoLocalDate othEmpStartsDate) {
        return empStartsDate.isBefore(othEmpStartsDate) ? othEmpStartsDate : empStartsDate;
    }

    private ChronoLocalDate getLastDate(ChronoLocalDate empFinishDate, ChronoLocalDate othEmpFinishDate) {
        return empFinishDate.isAfter(othEmpFinishDate) ? othEmpFinishDate : empFinishDate;
    }
}
