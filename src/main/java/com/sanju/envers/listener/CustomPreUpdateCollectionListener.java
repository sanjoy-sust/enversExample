package com.sanju.envers.listener;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPreCollectionUpdateEventListenerImpl;
import org.hibernate.event.spi.PreCollectionUpdateEvent;

public class CustomPreUpdateCollectionListener extends EnversPreCollectionUpdateEventListenerImpl {
    public CustomPreUpdateCollectionListener(EnversService enversService) {
        super(enversService);
    }

    @Override
    public void onPreUpdateCollection(PreCollectionUpdateEvent event) {
        System.out.println("On Pre update collection event");
    }
}
