package com.tenPines.application.service;

import com.tenPines.model.SecretPalEvent;
import com.tenPines.persistence.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private FriendRelationService friendRelationService;

    public SecretPalEvent retrieveCurrentEvent() {
        //TODO: reificar el concepto de evento :D
        return eventRepository.findAll().get(0);
    }

    public List<SecretPalEvent> retrieveAllEvents() {
        return Collections.singletonList(retrieveCurrentEvent());
    }

    public SecretPalEvent create() {
        return eventRepository.save(new SecretPalEvent());
    }

    public void save(SecretPalEvent secretPalEvent) {
        eventRepository.save(secretPalEvent);
    }

    public void delete(SecretPalEvent event) {
        eventRepository.delete(event);
    }
}
