package com.tenPines.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;


@Repository
@Transactional
public class DatabaseFailedMails extends HibernateGenericDAO<Message> {
    @Override
    protected Class<Message> getDomainClass() {
        return Message.class;
    }
}
