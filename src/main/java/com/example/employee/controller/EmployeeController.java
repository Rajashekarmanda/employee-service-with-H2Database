/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * import java.util.*;
 */

// Write your code here
package com.example.employee.controller;

import com.example.employee.model.*;
import com.example.employee.service.EmployeeH2Service;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
public class EmployeeController {

    @Autowired
    public EmployeeH2Service employeeService;

    @GetMapping("/employees")
    public ArrayList<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("employees/{employeeId}")
    public Employee getEmployeeDetails(@PathVariable("employeeId") int employeeId) {
        return employeeService.getEmployeeDetails(employeeId);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployeeDetails(@PathVariable("employeeId") int employeeId, @RequestBody Employee employee) {
        return employeeService.updateEmployeeDetails(employeeId, employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") int employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}