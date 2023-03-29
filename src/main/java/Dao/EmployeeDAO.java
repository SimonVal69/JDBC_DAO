package Dao;

import UserClasses.Employee;
import java.util.List;

public interface EmployeeDAO {

    List<Employee> getAllEmployees();
    List<Employee> getEmployeeById();
    void addEmployee();
    void updateEmployee();
    void deleteEmployee();
}
