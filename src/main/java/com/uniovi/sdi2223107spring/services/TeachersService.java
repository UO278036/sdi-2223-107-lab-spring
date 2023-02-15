package com.uniovi.sdi2223107spring.services;

import com.uniovi.sdi2223107spring.entities.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service // servicio
public class TeachersService {
    // el servicio se instancia una unica vez
    private List<Teacher> teachersList = new LinkedList<Teacher>();

    @PostConstruct
    public void init() {
        // lista de profesores por defecto en memoria
        teachersList
                .add(new Teacher(1L, "11111111z", "Raul", "Fernández", "España"));
        teachersList
                .add(new Teacher(2L, "00000000C", "Kevin", "Alvarez", "Fernández"));

    }

    public List<Teacher> getTeachers() {
        return teachersList;
    }

    public Teacher getTeacher(Long id) {
        return teachersList.stream().filter(t -> t.getId().equals(id))
                .findFirst().get();
    }

    public void addTeacher(Teacher teacher) {
        // si en id es null le asignamos el ultimo + 1 de la lista
        if (teacher.getId() == null) {
            teacher.setId(
                    teachersList.get(teachersList.size() - 1).getId() + 1);
        }
    }

    public void deleteTeacher(Long id) {
        teachersList.removeIf(t -> t.getId().equals(id));
    }
}
