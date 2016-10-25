package com.tenPines.model;


import java.time.LocalDate;

public class NewUser {

    private String userName;
    private String name;
    private String lastName;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    private Integer day;
    private Integer month;
    private Integer year;
    private String password;
    private String email;
    private Boolean wantToParticipate;


    public static NewUser createANewUser(String userName, String name, String lastName, Integer day, Integer month, Integer year,
                                         String password, String email, Boolean wantToParticipate ) {
        if(userName == null | name == null | lastName == null | day == null| month == null | year ==null | password == null | email == null ) {
            throw new RuntimeException(NewUser.errorMessageWhenAnyFieldIsNull());
        }
        NewUser user = new NewUser();
        user.setUserName(userName);
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setDay(day);
        user.setMonth(month);
        user.setYear(year);
        user.setEmail(email);
        user.setWantToParticipate(wantToParticipate);
        return user;
    }

    private static String errorMessageWhenAnyFieldIsNull() {
        return "Any field is null, please check your input data ";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getWantToParticipate() {
        return wantToParticipate;
    }

    public void setWantToParticipate(Boolean wantToParticipate) {
        this.wantToParticipate = wantToParticipate;
    }


}
