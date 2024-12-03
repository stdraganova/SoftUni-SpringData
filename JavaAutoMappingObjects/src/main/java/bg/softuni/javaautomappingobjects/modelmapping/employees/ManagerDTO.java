package bg.softuni.javaautomappingobjects.modelmapping.employees;

import java.util.ArrayList;
import java.util.List;

public class ManagerDTO {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;

    public ManagerDTO() {
        this.employees = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return String.format("%s %s | Employees: %d%n %s", firstName, lastName, employees.size(), employees.toString().replaceAll("[\\[\\],]", ""));
    }
}
