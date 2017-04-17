package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by JamesHartanto on 4/13/17.
 */
@Component
public class PeopleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> listPeople(String search){
        return jdbcTemplate.query("SELECT * from person " +
                "WHERE lower(firstname) like lower(?) " +
                "OR lower(lastname) like lower(?) LIMIT 200",
                new Object[]{"%" + search + "%","%" + search + "%"},
        (resultSet,row) -> new Person(
                resultSet.getInt("personid"),
                resultSet.getString("title"),
                resultSet.getString("firstname"),
                resultSet.getString("middlename"),
                resultSet.getString("lastname"),
                resultSet.getString("suffix")));
    }

    public Person findPerson(Integer personId) {
        return jdbcTemplate.queryForObject("SELECT * from person WHERE personid = ?",
                new Object[]{personId},
                (resultSet,row) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")));
    }

    public void savePerson(Person person) {
        // creating person
        if (person.getPersonId() == null){
            jdbcTemplate.update("INSERT INTO person(title,firstname,middlename,lastname,suffix) " +
                    "VALUES (?,?,?,?,?)",
                    person.getTitle(),person.getFirstName(),person.getMiddleName(),person.getLastName(),person.getSuffix());
        } else {
            // updating person
            jdbcTemplate.update("UPDATE person " +
                    "SET title=?,firstname=?,middlename=?,lastname=?,suffix=? " +
                    "WHERE personid = ?",
                    person.getTitle(),person.getFirstName(),person.getMiddleName(),person.getLastName(),person.getSuffix(),person.getPersonId());
        }
    }
}
