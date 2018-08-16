package com.example.demo.dao;

import com.example.demo.model.Person;
import com.example.demo.model.Skill;

import java.util.List;

public interface DemoDB {

    List<Person> getPersons();

    List<Skill> getSkills();

    List<Skill> getPersonSkills(Integer personId);
}
