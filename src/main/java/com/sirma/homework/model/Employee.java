package com.sirma.homework.model;

import java.time.chrono.ChronoLocalDate;

/**
 * Model of employee.
 */
public class Employee {
    private int employeeId;
    private int projectId;
    private ChronoLocalDate dateFrom;
    private ChronoLocalDate dateTo;

    /**
     * Employee Constructor.
     * @param employeeId id of the employee.
     * @param projectId id of the project, that employee has worked.
     * @param dateFrom from which date.
     * @param dateTo when he finish worked on that project. (NULL if it is still working)
     */
    public Employee(int employeeId, int projectId, ChronoLocalDate dateFrom, ChronoLocalDate dateTo) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public ChronoLocalDate getDateFrom() {
        return dateFrom;
    }

    public ChronoLocalDate getDateTo() {
        return dateTo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", projectId=" + projectId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (employeeId != employee.employeeId) return false;
        if (projectId != employee.projectId) return false;
        if (!dateFrom.equals(employee.dateFrom)) return false;
        return dateTo != null ? dateTo.equals(employee.dateTo) : employee.dateTo == null;

    }

    @Override
    public int hashCode() {
        int result = employeeId;
        result = 31 * result + projectId;
        result = 31 * result + dateFrom.hashCode();
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }
}
