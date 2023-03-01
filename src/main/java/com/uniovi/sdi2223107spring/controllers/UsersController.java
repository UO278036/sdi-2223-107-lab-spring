package com.uniovi.sdi2223107spring.controllers;

import com.uniovi.sdi2223107spring.entities.Mark;
import com.uniovi.sdi2223107spring.entities.User;
import com.uniovi.sdi2223107spring.services.MarksService;
import com.uniovi.sdi2223107spring.services.RolesService;
import com.uniovi.sdi2223107spring.services.SecurityService;
import com.uniovi.sdi2223107spring.services.UsersService;
import com.uniovi.sdi2223107spring.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;

@Controller
public class UsersController {
    @Autowired //Inyectar el servicio

    private MarksService marksService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private SignUpFormValidator signUpFormValidator;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;
    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }
    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        User originalUser = usersService.getUser(user.getId());
        originalUser.setDni(user.getDni());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        //originalUser.setPassword(user.getPassword());
        usersService.editUser(originalUser);
        return "redirect:/user/details/" + id;
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("usersList", usersService.getUsers() );
        return "user/list :: tableUsers";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable, Principal principal,
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
        return "home";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }



}

