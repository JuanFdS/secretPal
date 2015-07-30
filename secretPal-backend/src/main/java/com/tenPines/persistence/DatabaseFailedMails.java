package com.tenPines.persistence;

import com.tenPines.model.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class DatabaseFailedMails extends HibernateGenericDAO<Message> {

    @Override
    protected Class<Message> getDomainClass() {
        return Message.class;
    }
}
