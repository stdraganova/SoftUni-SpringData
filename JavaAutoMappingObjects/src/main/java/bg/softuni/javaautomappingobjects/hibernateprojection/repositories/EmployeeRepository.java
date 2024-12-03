package bg.softuni.javaautomappingobjects.hibernateprojection.repositories;

import bg.softuni.javaautomappingobjects.hibernateprojection.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByBirthDateBeforeOrderBySalaryDesc(LocalDate before);
}
