package com.uniovi.sdi2223107spring.entities;

public class Teacher {

    private Long id;
    private String DNI;
    private String name;
    private String surname;
    private String category;

    public Teacher() {
    }

    public Teacher(Long id, String DNI, String name, String surname, String category) {
        super();
        this.id= id;
        this.DNI = DNI;
        this.name = name;
        this.surname = surname;
        this.category= category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDNI(){
        return DNI;
    }

    public void setDNI(String DNI){
        this.DNI=DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Teacher[DNI=" + DNI + ", name=" + name + ", surname="
                + surname +", category=" + category + "]";
    }
}
