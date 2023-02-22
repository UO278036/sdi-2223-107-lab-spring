package com.uniovi.sdi2223107spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Professor {

    @Id
    @GeneratedValue
    private String DNI;
    private String name;
    private String surnames;
    private String category;

    private Long id;


    public Professor(){

    }

    public Professor(String dni, String name, String apellidos, String categoria) {
        super();
        this.DNI = dni;
        this.name = name;
        this.surnames = apellidos;
        this.category = categoria;
    }

    public Professor(Long id, String DNI, String name, String surnames, String category) {
        this.DNI = DNI;
        this.name = name;
        this.surnames = surnames;
        this.category = category;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "DNI='" + DNI + '\'' +
                ", name='" + name + '\'' +
                ", surnames='" + surnames + '\'' +
                ", category='" + category + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(DNI, professor.DNI);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getDNI() {
        return DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}