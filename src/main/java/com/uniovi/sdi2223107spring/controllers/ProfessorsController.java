package com.uniovi.sdi2223107spring.controllers;

import com.uniovi.sdi2223107spring.entities.Professor;
import com.uniovi.sdi2223107spring.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    @Autowired
    private ProfessorsService professorsService;

    @RequestMapping(value="/professor/add" , method=RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor){
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }

    @RequestMapping("/professor/add")
    public String getProfessor(){
        return "professor/add";
    }

    @RequestMapping("/professor/list")
    public String getList(Model model){
        model.addAttribute("professorList", professorsService.getProfessors());
        return "professor/list";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id){
        professorsService.deleteProfessor(id);
        return "redirect:/professor/list";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id){
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/details";
    }


}