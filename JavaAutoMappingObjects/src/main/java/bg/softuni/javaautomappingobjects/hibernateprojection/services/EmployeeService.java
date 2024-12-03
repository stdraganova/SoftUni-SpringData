package bg.softuni.javaautomappingobjects.hibernateprojection.services;

import bg.softuni.javaautomappingobjects.hibernateprojection.entities.Employee;
import bg.softuni.javaautomappingobjects.hibernateprojection.entities.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);
    List<EmployeeDTO> findeEmployeesBornBefore(int bornBefore);
}
