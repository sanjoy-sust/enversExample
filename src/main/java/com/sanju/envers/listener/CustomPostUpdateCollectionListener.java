package com.sanju.envers.listener;

import org.hibernate.event.spi.PostCollectionUpdateEvent;
import org.hibernate.event.spi.PostCollectionUpdateEventListener;

public class CustomPostUpdateCollectionListener implements PostCollectionUpdateEventListener {
    @Override
    public void onPostUpdateCollection(PostCollectionUpdateEvent event) {

    }
}
