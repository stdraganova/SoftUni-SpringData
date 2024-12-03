package bg.softuni.javaautomappingobjects.hibernateprojection;

import bg.softuni.javaautomappingobjects.hibernateprojection.entities.Employee;
import bg.softuni.javaautomappingobjects.hibernateprojection.entities.EmployeeDTO;
import bg.softuni.javaautomappingobjects.hibernateprojection.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private EmployeeService employeeService;

    public Main(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        createEmployees();

        int bornBefore = Integer.parseInt(scanner.nextLine());

        List<EmployeeDTO> result = employeeService.findeEmployeesBornBefore(bornBefore);

        System.out.println(result);

        //if we had -> employeeService.create() - > we should create CreateEmployeeDTO
    }

    private void createEmployees() {
        Employee manager = new Employee("Pesho", "Goshov", BigDecimal.valueOf(1000),LocalDate.now());
        employeeService.save(manager);

        Employee firstEmployee = new Employee("Ivan", "Ivanov", BigDecimal.valueOf(2000),LocalDate.now(), manager);
        employeeService.save(firstEmployee);

        Employee secondEmployee = new Employee("Atanas", "Atanasov", BigDecimal.valueOf(3000),LocalDate.now(), manager);
        employeeService.save(secondEmployee);

        Employee thirdEmployee = new Employee("Sasho", "Sashov", BigDecimal.valueOf(4000),LocalDate.now(), manager);
        employeeService.save(thirdEmployee);
    }
}
