package com.akash.dao.slave;

import com.akash.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonSlaveDao extends CrudRepository<Person, Long> {}
