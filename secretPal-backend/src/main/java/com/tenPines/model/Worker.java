package com.tenPines.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tenPines.configuration.JsonDateDeserializer;
import com.tenPines.configuration.JsonDateSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

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
    private List<Wish> wishList;

    public Worker() {
    }

    public Worker(String fullName, String email, LocalDate dateOfBirth) {
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

    public void seteMail(String eMail) throws Exception {
        this.eMail = eMail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate birthdayDate) {
        this.dateOfBirth = birthdayDate;
    }

    private void checkIfFieldIsValidUponCondition(Boolean condition, String message) throws Exception {
        if (condition) throw new Exception(message);
    }

    public List<Wish> getWishList() {
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
