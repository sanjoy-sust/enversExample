package com.sanju.envers.listener;

import com.sanju.envers.entity.Employee;
import org.apache.log4j.Logger;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPreUpdateEventListenerImpl;
import org.hibernate.event.spi.PreUpdateEvent;

public class CustomEnversPreUpdateEventListenerImpl extends
        EnversPreUpdateEventListenerImpl {

    Logger log = Logger.getLogger(CustomEnversPreUpdateEventListenerImpl.class
            .getName());

    public CustomEnversPreUpdateEventListenerImpl(EnversService enversService) {
        super(enversService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof Employee
                && ((Employee) event.getEntity()).getDepartment() == null) {
            System.out.println("Department should be assign for an employee.");
            return false;
        }
        System.out.println("On Pre Update event");
        return super.onPreUpdate(event);
    }

}
