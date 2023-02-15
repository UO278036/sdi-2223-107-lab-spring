package com.uniovi.sdi2223107spring.controllers;

import com.uniovi.sdi2223107spring.entities.Teacher;
import com.uniovi.sdi2223107spring.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeachersController {

    @Autowired // Inyectarel servicio
    private TeachersService teachersService;

    @RequestMapping("/professor/list")
    public String getList() {
        return teachersService.getTeachers().toString();
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teachersService.addTeacher(teacher);
        return "Added teacher";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return "Teacher details: " + teachersService.getTeacher(id).toString();
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teachersService.deleteTeacher(id);
        return "Deleted teacher with id: " + id;
    }

    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "Edit teacher...";
    }

    @RequestMapping(value = "/professor/edit/{id}", method = RequestMethod.POST)
    public String setEdit(Model model, @PathVariable Long id,
                          @ModelAttribute Teacher teacher) {
        teacher.setId(id);
        teachersService.addTeacher(teacher);
        return "redirect:/professor/details/" + id;
    }
}

