package bg.softuni.javaautomappingobjects.modelmapping;

import bg.softuni.javaautomappingobjects.modelmapping.employees.Employee;
import bg.softuni.javaautomappingobjects.modelmapping.employees.ManagerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class Main implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        ModelMapper modelMapper = new ModelMapper();

        //transferDataFromEmployeeToEmployeeDTO(modelMapper);
        advancedMapping(modelMapper);

    }

    private void advancedMapping(ModelMapper modelMapper) {
        Employee first = new Employee("First", "Last", 1200);
        Employee second = new Employee("Second", "Last", 2200);
        Employee third = new Employee("Third", "Last", 3200);
        Employee forth = new Employee("Forth", "Last", 4200);

        Employee managerOne = new Employee("ManagerOne", "One", 4200, List.of(first, second, third));
        Employee managerTwo = new Employee("ManagerTwo", "Two", 4200, List.of(forth));

        //Mapping Employee to Manager and the employees from the manager list
        //were automatic mapped to EmployeeDto
        ManagerDTO oneDTO = modelMapper.map(managerOne, ManagerDTO.class);
        System.out.println(oneDTO);
    }

    private void transferDataFromEmployeeToEmployeeDTO(ModelMapper modelMapper) {

//        Employee employee = new Employee("Pesho", "Goshev", 1500.0);
//        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
//
//        System.out.println(employeeDto);
    }
}
