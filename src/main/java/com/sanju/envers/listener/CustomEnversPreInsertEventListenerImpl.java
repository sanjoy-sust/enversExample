package com.sanju.envers.listener;

import com.sanju.envers.entity.Department;
import com.sanju.envers.entity.Employee;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

public class CustomEnversPreInsertEventListenerImpl implements PreInsertEventListener {
    @Override
    public boolean onPreInsert(PreInsertEvent event) {

        if(event.getEntity() instanceof Employee){
            ContextLookup.setCurrentEntity(new Employee());
        }

        if(event.getEntity() instanceof Department){
            ContextLookup.setCurrentEntity(new Department());
        }
        System.out.println("On Pre Insert event");
        return false;
    }
}
