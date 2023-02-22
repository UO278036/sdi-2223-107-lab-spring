package com.uniovi.sdi2223107spring.services;

import com.uniovi.sdi2223107spring.entities.Professor;
import com.uniovi.sdi2223107spring.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorsService {

    private List<Professor> professors = new LinkedList<Professor>();

    @PostConstruct
    public void init() {
        professors.add(new Professor(1L, "1", "Pedro", "Garcia", "Ciencias"));
        professors.add(new Professor(2L, "2", "Carmen", "Perez", "Derecho"));
        professors.add(new Professor(3L, "3", "Julian", "Fernandez", "Matematicas"));
    }

    public void addProfessor(Professor professor) {
        if (professor.getId() == null) {
            professor.setId(professors.get(professors.size() - 1).getId() + 1);
        }
        professors.add(professor);
    }

    public List<Professor> getProfessors() {
        return this.professors;
    }

    public Professor getProfessor(Long id) {
        return professors.stream()
                .filter(professor -> professor.getId().equals(id)).findFirst().get();
    }

    public Professor getProfessorByDNI(String dni) {
        for (Professor p : professors)
            if (p.getDNI().equals(dni))
                return p;
        return null;
    }

    public void deleteProfessor(Long id) {
        professors.removeIf(professor -> professor.getId().equals(id));
    }
}