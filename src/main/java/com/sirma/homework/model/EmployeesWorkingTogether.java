package com.sirma.homework.model;

/**
 * Model of employees, who have worked together.
 */
public class EmployeesWorkingTogether implements Comparable<EmployeesWorkingTogether> {

    private int employeeId;
    private int otherEmployeeId;
    private int daysWorkingTogether;

    /**
     * Employees who worked together.
     * @param employeeId id of the employee.
     * @param otherEmployeeId id of other employee.
     * @param daysWorkingTogether how long they worked together.
     */
    public EmployeesWorkingTogether(int employeeId, int otherEmployeeId, int daysWorkingTogether) {
        this.employeeId = employeeId;
        this.otherEmployeeId = otherEmployeeId;
        this.daysWorkingTogether = daysWorkingTogether;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getOtherEmployeeId() {
        return otherEmployeeId;
    }

    public int getDaysWorkingTogether() {
        return daysWorkingTogether;
    }

    public void setDaysWorkingTogether(int daysWorkingTogether) {
        this.daysWorkingTogether = daysWorkingTogether;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeesWorkingTogether that = (EmployeesWorkingTogether) o;

        if (employeeId != that.employeeId) return false;
        if (otherEmployeeId != that.otherEmployeeId) return false;
        return daysWorkingTogether == that.daysWorkingTogether;

    }

    @Override
    public int hashCode() {
        int result = employeeId;
        result = 31 * result + otherEmployeeId;
        result = 31 * result + (daysWorkingTogether ^ (daysWorkingTogether >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "EmployeesWorkingTogether{" +
                "employeeId=" + employeeId +
                ", otherEmployeeId=" + otherEmployeeId +
                ", daysWorkingTogether=" + daysWorkingTogether +
                '}';
    }

    @Override
    public int compareTo(EmployeesWorkingTogether o) {
        return -(this.getDaysWorkingTogether() - o.getDaysWorkingTogether());
    }
}
