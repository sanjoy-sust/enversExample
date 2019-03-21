package com.sanju.envers.listener;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Component
public class EnversListenerConfiguration {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        EnversService enversService = sessionFactory.getServiceRegistry().getService(EnversService.class);

        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(new CustomEnversPreInsertEventListenerImpl());
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(new CustomEnversPreUpdateEventListenerImpl(enversService));
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(new CustomPostUpdateEventListener(enversService));
        registry.getEventListenerGroup(EventType.PRE_COLLECTION_UPDATE).appendListener(new CustomPreUpdateCollectionListener(enversService));
        registry.getEventListenerGroup(EventType.POST_COLLECTION_UPDATE).appendListener(new CustomPostUpdateCollectionListener());
    }
}
