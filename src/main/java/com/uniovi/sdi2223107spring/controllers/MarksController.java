package com.uniovi.sdi2223107spring.controllers;

import com.uniovi.sdi2223107spring.entities.Mark;

import com.uniovi.sdi2223107spring.entities.User;
import com.uniovi.sdi2223107spring.services.MarksService;
import com.uniovi.sdi2223107spring.services.UsersService;
import com.uniovi.sdi2223107spring.validators.MarkAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Controller
public class MarksController {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UsersService usersService;

    @Autowired
    private MarkAddValidator markAddValidator;

    @Autowired //Inyectar el servicio

    private MarksService marksService;
    @RequestMapping("/mark/list")
    public String getList(Model model, Pageable pageable, Principal principal,
                          @RequestParam(value="", required=false) String searchText) {
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
        if (searchText!=null && !searchText.isEmpty()) {
            marks = marksService.searchMarksByDescriptionAndNameForUser(pageable, searchText, user);
        }
        else {
            marks = marksService.getMarksForUser(pageable, user);
        }

        model.addAttribute("markList", marks.getContent());
        model.addAttribute("page", marks);
        model.addAttribute("page", marks);

        return "mark/list";
    }


    @RequestMapping(value="/mark/add")
    public String getMark(Model model){
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/add";
    }


    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result, Model model) {
        markAddValidator.validate(mark, result);
        if (result.hasErrors()){
            model.addAttribute("usersList", usersService.getUsers());
            return "mark/add";
        }
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id){
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal){
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, user);
        model.addAttribute("markList", marks.getContent() );
        return "mark/list :: tableMarks";
    }


    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    @RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@Validated Mark mark, @PathVariable Long id, BindingResult result)
    {
        markAddValidator.validate(mark,result);
        if(result.hasErrors()){
            return "mark/edit";
        }
        Mark originalMark = marksService.getMark(id);
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/"+id;
    }

    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResendTrue(@PathVariable Long id) {
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResendFalse(@PathVariable Long id) {
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
    }
}
