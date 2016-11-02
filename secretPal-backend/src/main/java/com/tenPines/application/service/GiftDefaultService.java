package com.tenPines.application.service;

import com.tenPines.model.GiftDefault;
import com.tenPines.persistence.GiftDefaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aye on 02/11/16.
 */

@Service
public class GiftDefaultService {

    @Autowired
    GiftDefaultRepository repository;

    public List<GiftDefault> getAll() {
        return repository.findAll();

    }

}
