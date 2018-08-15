package com.example.demo.model;

import lombok.Builder;

import java.util.List;

@Builder
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private List<Skill> skills;
}
