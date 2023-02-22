package com.uniovi.sdi2223107spring.validators;


import com.uniovi.sdi2223107spring.entities.Professor;
import com.uniovi.sdi2223107spring.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfessorValidator implements Validator {
    @Autowired
    private ProfessorsService professorService;
    @Override
    public boolean supports(Class<?> aClass) {
        return Professor.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "DNI", "Error.empty");
        if (professor.getDNI().length() != 9) {
            errors.rejectValue("DNI", "Error.professor.dni.length");}

        if (professor.getDNI().length()>8 && !Character.isLetter(professor.getDNI().charAt(8)))
            errors.rejectValue("DNI", "Error.professor.dni.letter");

        if (professorService.getProfessorByDNI(professor.getDNI()) != null) {
            errors.rejectValue("DNI", "Error.professor.dni.duplicate");}

    }
}
