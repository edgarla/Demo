package com.example.demo.dao;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DemoDBSqlStatements {

    private static final String TOKEN_SCHEMA_NAME = "schema_name";

    enum DemoDbQuery {

        GET_PERSONS("getPersons.sql"),
        GET_SKILLS("getSkills.sql"),
        GET_PERSON_SKILLS("getPersonSkills");

        final String script;

        DemoDbQuery(String script){
            this.script = script;
        }
    }

    private final Map<DemoDbQuery, String> sqlStatements = new HashMap<>(2);

    public DemoDBSqlStatements(@Value("${demo.db.datasource.schema}") final String schema){
        Arrays.stream(DemoDbQuery.values()).forEach(demoDbQuery -> {
            final String script = demoDbQuery.script;
            try {
                final String query = this.loadSQLContent(script);
                sqlStatements.put(demoDbQuery, query.replace(TOKEN_SCHEMA_NAME, schema));
            } catch (IOException e) {
                throw new IllegalStateException("Required script was not found in classpath " + script, e);
            }
        });
    }

    String getScript(final DemoDbQuery query) {
        return sqlStatements.get(query);
    }

    private String loadSQLContent(String scriptName) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(scriptName), StandardCharsets.UTF_8);
    }
}
