package com.tenPines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tenPines.configuration.JsonDateDeserializer;
import com.tenPines.configuration.JsonDateSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Email private String mail;

    @NotNull private LocalDate birthday;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "worker")
    public Set<Wish> wish;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "giftReceiver")
    public List<FriendRelation> receiverRelations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "giftGiver")
    public List<FriendRelation> giverRelations;

    @NotNull
    private Boolean wantsToParticipate;

    // Necessary for hibernate
    private Worker() { }

    public Worker(String fullName, String email, LocalDate birthday, Boolean wantsToParticipate) {
        this.fullName = fullName;
        this.mail = email;
        this.birthday = birthday;
        this.wantsToParticipate = wantsToParticipate;
        this.receiverRelations = new ArrayList<>();
        this.giverRelations = new ArrayList<>();
    }

    public void changeParticipationIntention() {
        this.wantsToParticipate = ! wantsToParticipate;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Boolean getWantsToParticipate() {
        return wantsToParticipate;
    }

    @Override
    public String toString() {
        return this.getClass().getTypeName() + ": " + this.fullName + " (" + this.birthday + ")";
    }

    public List<FriendRelation> getReceiverRelations() {
        return receiverRelations;
    }
}
