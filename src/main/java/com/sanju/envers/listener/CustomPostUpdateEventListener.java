package com.sanju.envers.listener;

import com.sanju.envers.entity.Employee;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostUpdateEventListenerImpl;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PreUpdateEvent;

public class CustomPostUpdateEventListener extends EnversPostUpdateEventListenerImpl {
    public CustomPostUpdateEventListener(EnversService enversService) {
        super(enversService);
    }
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Employee
                && ((Employee) event.getEntity()).getDepartment() == null) {
            System.out.println("Department should be assign for an employee.");

        }
        System.out.println("On Post Insert event");
        super.onPostUpdate(event);
    }
}
