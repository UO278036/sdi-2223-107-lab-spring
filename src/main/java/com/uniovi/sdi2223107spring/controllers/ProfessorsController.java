package com.uniovi.sdi2223107spring.controllers;

import com.uniovi.sdi2223107spring.entities.Professor;
import com.uniovi.sdi2223107spring.services.ProfessorsService;
import com.uniovi.sdi2223107spring.validators.ProfessorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {
    @Autowired
    private ProfessorValidator professorValidator;

    @Autowired
    private ProfessorsService professorsService;

    @RequestMapping(value="/professor/add" , method=RequestMethod.POST)
    public String setProfessor(@Validated Professor professor, BindingResult result, Model model){
        professorValidator.validate(professor, result);
        if (result.hasErrors()){
            model.addAttribute(professor);
            return "professor/add";
        }
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
    @RequestMapping(value = "/proffesor/edit/{id}", method = RequestMethod.GET)
    public String getEdit(Model model,@PathVariable Long id){
        model.addAttribute("prof",professorsService.getProfessor(id));
        return "proffesor/edit";
    }
    @RequestMapping(value="/proffesor/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@Validated Professor prof, @PathVariable Long id, BindingResult result)
    {
        professorValidator.validate(prof,result);
        if(result.hasErrors()){
            return "proffesor/edit";
        }
        professorsService.addProfessor(prof);
        return "redirect:/proffesor/details/"+id;
    }

    @RequestMapping("/proffesor/details/{id}")
    public String detailsProffesor(Model model,@PathVariable Long id){
        model.addAttribute("prof",professorsService.getProfessor(id));
        return "/proffesor/details";
    }

    @RequestMapping("/proffesor/delete/{id}")
    public String deleteProffesor(@PathVariable Long id){
        professorsService.deleteProfessor(id);
        return "redirect:/proffesor/list";
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.GET)
    public String getProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/add";
    }


}