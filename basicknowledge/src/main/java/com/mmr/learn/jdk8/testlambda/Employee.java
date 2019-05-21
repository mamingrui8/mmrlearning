package com.mmr.learn.jdk8.testlambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NotBlank
class Employee{
    private String name;
    private Integer age;
    private double salary;
}