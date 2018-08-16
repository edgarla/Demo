package com.example.demo.dao;

import com.example.demo.model.Person;
import com.example.demo.model.Skill;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DemoDBDao implements DemoDB {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private DemoDBSqlStatements demoDBSqlStatements;

    @Autowired
    public DemoDBDao(@Qualifier(value = "demodbJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate, DemoDBSqlStatements demoDBSqlStatements) {
        this.jdbcTemplate = jdbcTemplate;
        this.demoDBSqlStatements = demoDBSqlStatements;
    }

    @Override
    public List<Person> getPersons() {
        String script = this.demoDBSqlStatements.getScript(DemoDBSqlStatements.DemoDbQuery.GET_PERSONS);
        List<Map<String, Object>> resultSet = this.jdbcTemplate.queryForList(script, Collections.emptyMap());
        return this.parseResultSetToPersonsList(resultSet);
    }

    @Override
    public List<Skill> getSkills() {
        String script = this.demoDBSqlStatements.getScript(DemoDBSqlStatements.DemoDbQuery.GET_SKILLS);
        List<Map<String, Object>> resultSet = this.jdbcTemplate.queryForList(script, Collections.emptyMap());
        return this.parseResultSetToSkillsList(resultSet);
    }

    @Override
    public List<Skill> getPersonSkills(Integer personId) {
        String script = this.demoDBSqlStatements.getScript(DemoDBSqlStatements.DemoDbQuery.GET_PERSON_SKILLS);
        Map<String, Object> params = ImmutableMap.<String, Object>builder().put("personId", personId).build();
        List<Map<String, Object>> resultSet = this.jdbcTemplate.queryForList(script, params);
        return this.parseResultSetToSkillsList(resultSet);
    }

    private List<Person> parseResultSetToPersonsList(List<Map<String, Object>> resultSet) {
        List<Person> persons = new ArrayList<>();
        for(Map map : resultSet){
            Integer personId = Integer.valueOf(map.get("personId").toString());
            List<Skill> personSkills = this.getPersonSkills(personId);
            Person person = Person.builder().id(personId).firstName(map.get("firstName").toString()).lastName(map.get("lastName").toString()).skills(personSkills).build();
            persons.add(person);
        }
        return persons;
    }

    private List<Skill> parseResultSetToSkillsList(List<Map<String, Object>> resultSet) {
        List<Skill> skills = new ArrayList<>();
        for(Map map : resultSet){
            Skill skill = Skill.builder().id(Integer.valueOf(map.get("skillId").toString())).skillName(map.get("skillName").toString()).build();
            skills.add(skill);
        }
        return skills;
    }
}
