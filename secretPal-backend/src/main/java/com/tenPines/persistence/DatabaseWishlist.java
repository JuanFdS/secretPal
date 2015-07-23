package com.tenPines.persistence;

import com.tenPines.model.Wish;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DatabaseWishlist extends HibernateGenericDAO<Wish> {

    @Override
    protected Class<Wish> getDomainClass() {
        return Wish.class;
    }



}
