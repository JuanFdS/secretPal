package com.tenPines.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tenPines.configuration.JsonDateDeserializer;
import com.tenPines.configuration.JsonDateSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
public class Worker {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String fullName;
    @NotEmpty
    @Email
    private String eMail;
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @NotNull
    private LocalDate dateOfBirth;
    private Boolean wantsToParticipate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "worker")
    //@ManyToOne(fetch = FetchType.LAZY)
    private Set<Wish> wishList;

    public Worker() {
    }

    public Worker(String fullName, String email, LocalDate dateOfBirth) {
        checkIfIsValid(fullName, "Full name is invalid");
        checkIfValidEmail(email);
        this.fullName = fullName;
        this.eMail = email;
        this.dateOfBirth = dateOfBirth;
        this.wantsToParticipate = false;
    }

    public Long getId() {
        return id;
    }

    /* @ManyToMany(mappedBy="friendRelations")
    private Set<SecretPalEvent> secretPalEvents = new HashSet<>();*/

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

   public void changeParticipationIntention() {
        setWantsToParticipate(!wantsToParticipate);
    }

    public boolean getWantsToParticipate() { return this.wantsToParticipate;}

   public void setWantsToParticipate(Boolean wantsToParticipate) {
        this.wantsToParticipate = wantsToParticipate;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        checkIfValidEmail(eMail); //TODO Usar los validators de Hibernate
        this.eMail = eMail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate birthdayDate) {
        this.dateOfBirth = birthdayDate;
    }

    private void checkIfIsValid(String name, String message) {
        checkIfFieldIsValidUponCondition(StringUtils.isBlank(name) || !name.matches("[a-zA-Z ,.'-]+"), message);
    }

    private void checkIfValidEmail(String email) {
        checkIfFieldIsValidUponCondition(!EmailValidator.getInstance().isValid(email), "Email is invalid");
    }

    private void checkIfFieldIsValidUponCondition(Boolean condition, String message){
        if(condition) throw new RuntimeException(message);
    }

    public Set<Wish> getWishList() {
        return wishList;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) return true;

        if (!(anObject instanceof Worker)) return false;
        Worker otherWorker = (Worker) anObject;

        return
                this.getFullName().equals(otherWorker.getFullName()) &&
                        this.geteMail().equals(otherWorker.geteMail()) &&
                        this.getDateOfBirth().equals(otherWorker.getDateOfBirth());
    }
}
