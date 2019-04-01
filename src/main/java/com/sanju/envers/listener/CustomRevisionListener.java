package com.sanju.envers.listener;

import com.sanju.envers.config.AppConfig;
import com.sanju.envers.entity.CustomRevisionEntity;
import com.sanju.envers.entity.Department;
import com.sanju.envers.entity.Employee;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class CustomRevisionListener implements RevisionListener {


    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;

        EntityManagerFactory emf = ContextLookup.getApplicationContext().getBean(EntityManagerFactory.class);
        AuditReader auditReader = AuditReaderFactory.get(emf.createEntityManager());
        Number resultList = null;
        Object obj = ContextLookup.getCurrentEntity();
        if( Employee.class.isInstance(obj)) {
            AuditQuery query = auditReader.createQuery()
                    .forRevisionsOfEntity(Department.class, false, true)
                    .addProjection(AuditEntity.revisionNumber().max());

            resultList = (Number) query.getSingleResult();
        }
        customRevisionEntity.setRefRevisionId(resultList != null?resultList.longValue():0);
    }
}
