package bg.softuni.javaautomappingobjects.modelmapping.employees;

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private double salary;

    public EmployeeDto() {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("  - %s %s %.2f%n", firstName, lastName, salary);
    }
}
