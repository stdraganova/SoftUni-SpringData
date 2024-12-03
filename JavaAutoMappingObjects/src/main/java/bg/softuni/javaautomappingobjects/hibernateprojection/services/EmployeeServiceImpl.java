package bg.softuni.javaautomappingobjects.hibernateprojection.services;

import bg.softuni.javaautomappingobjects.hibernateprojection.entities.Employee;
import bg.softuni.javaautomappingobjects.hibernateprojection.entities.EmployeeDTO;
import bg.softuni.javaautomappingobjects.hibernateprojection.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findeEmployeesBornBefore(int bornBefore) {
        LocalDate year = LocalDate.of(bornBefore, 12, 31);

        List<Employee> employees = employeeRepository
                .findByBirthDateBeforeOrderBySalaryDesc(year);

        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
    }
}
