package com.tenPines.persistence;

import com.tenPines.model.Wish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryWishlist implements AbstractRepository<Wish> {

    List<Wish> wishList = new ArrayList<>();

    @Override
    public List<Wish> retrieveAll() {
        return wishList;
    }

    @Override
    public void save(Wish... wishes) {
        Collections.addAll(wishList, wishes);
    }

    @Override
    public Wish refresh(Wish wish) {
        return wish;
    }

    @Override
    public void delete(Wish wish) {
        wishList.remove(wish);
    }

    @Override
    public Wish findById(Long id) {
        return wishList.get(id.intValue());
    }

    @Override
    public void update(Wish wish) {
    }
}
