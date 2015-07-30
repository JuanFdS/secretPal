package com.tenPines.persistence;

import com.tenPines.model.Wish;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DatabaseWishlist extends HibernateGenericDAO<Wish> {

    @Override
    protected Class<Wish> getDomainClass() {
        return Wish.class;
    }


}
