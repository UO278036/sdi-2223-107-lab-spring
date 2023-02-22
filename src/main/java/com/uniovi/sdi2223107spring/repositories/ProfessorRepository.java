package com.uniovi.sdi2223107spring.repositories;

import com.uniovi.sdi2223107spring.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface  ProfessorRepository extends CrudRepository<Professor, Long> {
    public Professor getProfessorByDNI(String DNI);
}

