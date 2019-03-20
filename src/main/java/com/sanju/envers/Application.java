package com.sanju.envers;

import com.sanju.envers.config.AppConfig;
import com.sanju.envers.entity.Department;
import com.sanju.envers.entity.Employee;
import com.sanju.envers.service.DepartmentService;
import com.sanju.envers.service.EmployeeService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {

        System.out.println("Transaction Open?.... " + TransactionSynchronizationManager.isActualTransactionActive());
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        EmployeeService employeeService = configApplicationContext.getBean(EmployeeService.class);
        DepartmentService departmentService = configApplicationContext.getBean(DepartmentService.class);


        /*Insert data to test*/
        dataInsertion(employeeService, departmentService);


        employeeService.getEmployees().forEach(employe -> {
            System.out.println("********************************************************");
            System.out.println("Employee info : ");
            System.out.println("Name          = " + employe.getName());
            System.out.println("Email         = " + employe.getEmail());
            System.out.println("Address       = " + employe.getAddress());
            System.out.println("Department    = " + employe.getDepartment().getName());
            System.out.println("Responsibilty = " + employe.getDepartment().getResponsibility());
            System.out.println("Version       = " + employe.getVersion());
        });

        /*Update Data for audit*/
        System.out.println("Updating data......");
        Employee employeeToUpdate = employeeService.getEmployeeById(1);
        long updateTime = System.currentTimeMillis();
        employeeToUpdate.setName(updateTime + "sanju-updated");
        employeeService.update(employeeToUpdate.getId(), employeeToUpdate);
        System.out.println("Updating completed.");

        /*Delete auditing test*/
        System.out.println("Deleting data......");
        List<Employee> employees = employeeService.getEmployees();
        if(employees.size()>2) {
            Employee employeeToDelete = employees.get(2);
            employeeService.delete(employeeToDelete);
            System.out.println("Data deleted.");
        }
        /*Get all revisions of employee entity*/
        System.out.println("\n\n\nEmployee Audit data Reading......................................");
        auditDataRead(Employee.class, configApplicationContext);

        System.out.println("\n\n\nDepartment Audit data Reading......................................");
        auditDataRead(Department.class, configApplicationContext);
    }

    private static void dataInsertion(EmployeeService employeeService, DepartmentService departmentService) {
        long currentTime = System.currentTimeMillis();
        Department department = new Department();
        department.setName("Development");
        department.setResponsibility(currentTime + "R&D");
        Department savedDepartment = departmentService.save(department);
        Employee employee = new Employee();
        employee.setEmail(currentTime + "@sanju.com");
        employee.setName("EnversTEST " + currentTime);
        employee.setAddress("EnversAddress " + currentTime);
        employee.setDepartment(savedDepartment);
        employeeService.save(employee);
    }

    private static <T> void auditDataRead(Class<T> tClass, AnnotationConfigApplicationContext configApplicationContext) {
        System.out.println("Reading auditing data...");
        EntityManagerFactory emf = configApplicationContext.getBean(EntityManagerFactory.class);
        AuditReader auditReader = AuditReaderFactory.get(emf.createEntityManager());

        AuditQuery query = auditReader.createQuery()
                .forRevisionsOfEntity(tClass, false, true);

        List<Object[]> resultList = query.getResultList();

        resultList.forEach(objects -> {
            System.out.println("------------------------------------------------------------------------\n");
            if (Employee.class.equals(tClass)) {
                Employee employeeRev = (Employee) objects[0];
                System.out.println("Employee info  : ");
                System.out.println("Id             : " + employeeRev.getId());
                System.out.println("Name           : " + employeeRev.getName());
                System.out.println("Email          : " + employeeRev.getEmail());
                System.out.println("Address        : " + employeeRev.getAddress());
            } else if (Department.class.equals(tClass)) {
                System.out.println("Department info: ");
                Department department = (Department) objects[0];
                System.out.println("Id             : "+department.getId());
                System.out.println("Responsibility : "+department.getResponsibility());
                System.out.println("Name           : "+department.getName());
            }
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) objects[1];
            System.out.println("Revision       : " + revisionEntity.getId());
            System.out.println("Date           : " + revisionEntity.getRevisionDate());

            RevisionType revisionType = (RevisionType) objects[2];
            System.out.println("Operation      : " + revisionType.name());
        });
    }
}
