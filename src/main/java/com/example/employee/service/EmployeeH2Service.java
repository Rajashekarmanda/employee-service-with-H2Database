/*
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.*;
 * 
 */

// Write your code here
package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeRowMapper;

import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class EmployeeH2Service implements EmployeeRepository {

    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Employee> getEmployees() {
        List<Employee> employeeList = db.query("select * from EMPLOYEELIST;", new EmployeeRowMapper());
        ArrayList<Employee> employees = new ArrayList<>(employeeList);
        return employees;
    }

    @Override
    public Employee getEmployeeDetails(int employeeId) {
        try {
            Employee employeeFound = db.queryForObject("select * from EMPLOYEELIST where employeeId = ?",
                    new EmployeeRowMapper(), employeeId);
            return employeeFound;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (employee.getEmployeeName() != null && employee.getEmail() != null && employee.getDepartment() != null) {
            db.update("insert into EMPLOYEELIST(employeeName,email,department) values(?,?,?)",
                    employee.getEmployeeName(),
                    employee.getEmail(), employee.getDepartment());
            Employee createdEmployee = db.queryForObject(
                    "select * from EMPLOYEELIST where employeeName = ? and email = ? and department = ?",
                    new EmployeeRowMapper(), employee.getEmployeeName(), employee.getEmail(),
                    employee.getDepartment());
            return createdEmployee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Employee updateEmployeeDetails(int employeeId, Employee employee) {
        if (employee.getEmployeeName() != null) {
            db.update("update EMPLOYEELIST set employeeName = ? where employeeId = ?", employee.getEmployeeName(),
                    employeeId);
        }
        if (employee.getEmail() != null) {
            db.update("update EMPLOYEELIST set email = ? where employeeId = ?", employee.getEmail(), employeeId);
        }
        return getEmployeeDetails(employeeId);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        Employee employeeFound = db.queryForObject("select * from EMPLOYEELIST where employeeId = ?",
                new EmployeeRowMapper(), employeeId);
        if (employeeFound != null) {
            db.update("delete from EMPLOYEELIST where employeeId = ?", employeeId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}