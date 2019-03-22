package com.sanju.envers.entity;

import com.sanju.envers.listener.CustomRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

@Entity
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity extends DefaultRevisionEntity {
    public long getRefRevisionId() {
        return refRevisionId;
    }

    public void setRefRevisionId(long refRevisionId) {
        this.refRevisionId = refRevisionId;
    }

    long refRevisionId;
}
