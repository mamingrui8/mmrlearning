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
    private int age;
    private double salary;
}