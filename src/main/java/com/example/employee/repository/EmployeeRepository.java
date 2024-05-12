// Write your code here
package com.example.employee.repository;

import java.util.*;
import com.example.employee.model.Employee;

public interface EmployeeRepository {
    ArrayList<Employee> getEmployees();

    Employee getEmployeeDetails(int employeeId);

    Employee addEmployee(Employee employee);

    Employee updateEmployeeDetails(int employeeId, Employee employee);

    void deleteEmployee(int employeeId);
}