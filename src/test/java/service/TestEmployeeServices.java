package service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sirma.homework.model.Employee;
import com.sirma.homework.model.EmployeesWorkingTogether;
import com.sirma.homework.service.EmployeesService;

public class TestEmployeeServices {

    private final int PROJECT_ID = 10;
    private final int OTHER_PROJECT_ID = 20;

    private final int EMPLOYEE_ID = 1;
    private final int OTHER_EMPLOYEE_ID = 2;

    private Employee employee;
    private Employee otherEmployee;
    private EmployeesService employeesService;
    private Map<Integer, List<Employee>> projectIdEmployeesMap;

    @Before
    public void setUp() {
        employeesService = new EmployeesService();
        projectIdEmployeesMap = new HashMap<>();

        employee      = new Employee(EMPLOYEE_ID      , PROJECT_ID, LocalDate.of(2017, 1, 1), LocalDate.of(2018, 1, 1));
        otherEmployee = new Employee(OTHER_EMPLOYEE_ID, PROJECT_ID, LocalDate.of(2017, 1, 1), LocalDate.of(2018, 1, 1));
    }

    @Test
    public void testFullPeriodOfWorkingOnTheSameProject() {
        projectIdEmployeesMap.put(PROJECT_ID,  Arrays.asList(employee, otherEmployee));

        EmployeesWorkingTogether employeesWorkingTogether = new EmployeesWorkingTogether(EMPLOYEE_ID, OTHER_EMPLOYEE_ID, 365);

        Assert.assertEquals(Collections.singleton(employeesWorkingTogether),
                                                employeesService.getEmployeesWorkingTogether(projectIdEmployeesMap));
    }

    @Test
    public void testEmployeesInSameProjectDifferentTime() {
        employee = new Employee(EMPLOYEE_ID, PROJECT_ID, LocalDate.of(2015, 1, 1), LocalDate.of(2016, 1, 1));
        otherEmployee = new Employee(OTHER_EMPLOYEE_ID, PROJECT_ID, LocalDate.of(2017, 1, 1), LocalDate.of(2018, 1, 1));

        Employee employee1 = new Employee(EMPLOYEE_ID, PROJECT_ID, LocalDate.of(2013, 1, 1), LocalDate.of(2014, 1, 1));
        Employee employee2 = new Employee(OTHER_EMPLOYEE_ID, PROJECT_ID, LocalDate.of(2011, 2, 1), LocalDate.of(2012, 8, 1));

        projectIdEmployeesMap.put(PROJECT_ID, Arrays.asList(employee, otherEmployee, employee1, employee2));

        Assert.assertEquals(0, employeesService.getEmployeesWorkingTogether(projectIdEmployeesMap).size());
    }

    @Test
    public void testEmployeesWorkingTogetherOnDifferentProjects() {
        Employee employee1 = new Employee(EMPLOYEE_ID, OTHER_PROJECT_ID, LocalDate.of(2014, 1, 1), LocalDate.of(2015, 1, 1));
        Employee employee2 = new Employee(OTHER_EMPLOYEE_ID, OTHER_PROJECT_ID, LocalDate.of(2013, 1, 1), LocalDate.of(2016, 1, 1));

        projectIdEmployeesMap.put(PROJECT_ID, Arrays.asList(employee, otherEmployee, employee1, employee2));

        EmployeesWorkingTogether employeesWorkingTogether = new EmployeesWorkingTogether(EMPLOYEE_ID, OTHER_EMPLOYEE_ID, 730);

        Assert.assertEquals(Collections.singleton(employeesWorkingTogether),
                                            employeesService.getEmployeesWorkingTogether(projectIdEmployeesMap));
    }

    @Test
    public void testEmployeesWorkingOnDifferentProjects() {
        projectIdEmployeesMap.put(PROJECT_ID, Collections.singletonList(employee));
        projectIdEmployeesMap.put(OTHER_PROJECT_ID, Collections.singletonList(otherEmployee));

        Assert.assertEquals(0, employeesService.getEmployeesWorkingTogether(projectIdEmployeesMap).size());
    }
}
