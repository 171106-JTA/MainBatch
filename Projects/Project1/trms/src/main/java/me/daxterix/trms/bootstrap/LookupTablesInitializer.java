package me.daxterix.trms.bootstrap;

import javafx.util.Pair;
import me.daxterix.trms.config.Config;
import me.daxterix.trms.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;

@Repository
public class LookupTablesInitializer {
    private static ApplicationContext context;
    private static SessionFactory sessionFactory;

    static {
        context = new AnnotationConfigApplicationContext(Config.class);
        sessionFactory = context.getBean(SessionFactory.class);
    }

    public static void main(String[] args) {
        LookupTablesInitializer pop = new LookupTablesInitializer();

        pop.populateMimeTable();
        pop.populateFilePurposeTable();
        pop.populateEmployeeRankTable();
        pop.populateRequestStatusTable();
        pop.populateDepartmentTable();
        pop.populateEventTypeTable();
    }

    @Transactional
    void populateMimeTable() {
        String [] mimeTypes = {
                "image/jpeg",
                "image/png",
                "text/plain",
                "application/pdf",
                "application/msword",
                "application/vnd.ms-outlook"
        };
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (String mType : mimeTypes)
                session.save(new MimeType(mType));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Error populating MimeType table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating MimeType table");
        }
    }

    @Transactional
    void populateFilePurposeTable() {
        String [] filePurposes = {
                "Approval Email",
                "Grade Document",
                "Event information"
        };
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (String purpose : filePurposes)
                session.save(new FilePurpose(purpose));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Successfully populated FilePurpose table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating FilePurpose table");
        }
    }

    @Transactional
    void populateEmployeeRankTable() {
        String [] ranks = {
                "Regular",
                "Supervisor",
                "Department Head",
                "Benefits Coordinator"
        };
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (String rank: ranks)
                session.save(new EmployeeRank(rank));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Successfully Populated EmployeeRank table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating EmployeeRank table");
        }
    }

    @Transactional
    void populateRequestStatusTable() {

        String [] statuses = {
                "Pending Direct Supervisor Approval",
                "Approved by Direct Supervisor",
                "Approved by Department Head",
                "Approved by Benefits Coordinator",
                "Pending Grade Review",  // should this be broken up?
                "Granted",
                "Denied"
        };
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (String status: statuses)
                session.save(new RequestStatus(status));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Successfully populated RequestStatus table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating RequestStatus table");
        }
    }

    @Transactional
    void populateDepartmentTable() {
        String [] departments = {
                "Gryffindor",
                "Hufflepuff",
                "Ravenclaw",
                "Slytherin"
        };
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (String dept: departments)
                session.save(new Department(dept));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Successfully populated Department table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating Department table");
        }
    }

    @Transactional
    void populateEventTypeTable() {

        // can't have generic arrays; who knew
        List<Pair<String, Float>> eventTypes = Arrays.asList(
                new Pair<>("University Course", 80f),
                new Pair<>("Seminars", 60f),
                new Pair<>("Certification Preparation Class", 75f),
                new Pair<>("Certification", 100f),
                new Pair<>("Technical Training", 90f),
                new Pair<>("Other", 30f)
        );
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (Pair<String, Float> type: eventTypes)
                session.save(new EventType(type.getKey(), type.getValue()));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Successfully populated EventType table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating EventType table");
        }
    }
}
