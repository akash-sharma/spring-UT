package com.akash.dao.master;

import com.akash.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonDao extends CrudRepository<Person, Long> {}
