package com.kt.edu.thirdproject.employee.query.service;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//@DataJdbcTest
@DataR2dbcTest()
class EmployeeServiceTest {

    @Autowired
    private  EmployeeRepository employeeRepository;

    @Test
    //public Flux<EmployeeEntity> getEmployeeList() {
    public void getEmployeeListTest() {

        Flux<EmployeeEntity>  employee =  employeeRepository.findAll();
       // employee.subscribe(System.out::println);
        //employee.log();
        //list.stream().map(String::toUpperCase).forEach(s -> System.out.println(s));

        Flux<String> mapStream = employee.map(emp -> emp.getEmpName());
        mapStream.subscribe(System.out::println);

        //System.out.println(employee.map(emp -> emp.getEmpName())
        //        .collect(Collectors.toList()));
                //.forEach(s -> System.out.println(s));
        //list.stream().map(String::toUpperCase).forEach(s -> System.out.println(s));

        //employee.forEach(s -> System.out.println(s));


    }

}