package com.mmr.learn.jdk8.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
public class Employee{

    public Employee(Integer age){
        this.age = age;
    }
    private String name;
    private Integer age;
    private Double salary;
    private Status status;

    public Employee(String name, Integer age, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}