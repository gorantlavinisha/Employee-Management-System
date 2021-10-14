package net.javaguides.springboot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.mindtree.employeemanagerapp.EmployeeManagementApplication;
import com.mindtree.employeemanagerapp.model.Employee;
import com.mindtree.employeemanagerapp.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = EmployeeManagementApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringbootBackendApplicationTests {

	@Autowired
    private EmployeeRepository employeeRepository;

	@Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest(){
        Employee employee = new Employee();
        employee.setEid(103);
        employee.setFirstName("Srihari");
        employee.setLastName("Danjayan");
        employee.setEmailId("Srihari23@gmail.com");
        employee.setAddress("Gujarat");
        employee.setSalary(30000.00);
        employeeRepository.save(employee);
        Assertions.assertThat(employee.getEid()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getEmployeeTest(){
        Employee employee = employeeRepository.findByEid(100).get();
        Assertions.assertThat(employee.getEid()).isEqualTo(100);
    }

    @Test
    @Order(3)
    public void getListOfEmployeesTest(){
        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest(){
        Employee employee = employeeRepository.findByEid(104).get();
        employee.setEmailId("Srihari123@gmail.com");
        Employee employeeUpdated =  employeeRepository.save(employee);
        Assertions.assertThat(employeeUpdated.getEmailId()).isEqualTo("Srihari123@gmail.com");
    }
    
    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest(){
        Employee employee = employeeRepository.findByEid(103).get();
        employeeRepository.delete(employee);
        Employee employee1 = null;
        Optional<Employee> optionalEmployee = employeeRepository.findByEmailId("Srihar123@gmail.com");
        if(optionalEmployee.isPresent()){
            employee1 = optionalEmployee.get();
        }
        Assertions.assertThat(employee1).isNull();
    }
}
