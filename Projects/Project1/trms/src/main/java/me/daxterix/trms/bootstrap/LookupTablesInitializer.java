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

        pop.createAccount();
        pop.populateFilePurposeTable();
        pop.populateEmployeeRankTable();
        pop.populateRequestStatusTable();
        pop.populateDepartmentTable();
        pop.populateEventTypeTable();
    }

    @Transactional
    void createAccount() {
        String [] mimeTypes = {
                MimeType.PNG,
                MimeType.JPEG,
                MimeType.TEXT_PLAIN,
                MimeType.PDF,
                MimeType.MS_OUTLOOK,
                MimeType.MS_WORD,
        };
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            for (String mType : mimeTypes)
                session.save(new MimeType(mType));

            session.getTransaction().commit();
            System.out.println("[PopulateLookupTables] Successfully populated MimeType table");
        }
        catch(PersistenceException e) {
            e.printStackTrace();
            System.out.println("Error populating MimeType table");
        }
    }

    @Transactional
    void populateFilePurposeTable() {
        String [] filePurposes = {
                FilePurpose.APPROVAL_EMAIL,
                FilePurpose.GRADE_DOCUMENT,
                FilePurpose.EVENT_INFO
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
                EmployeeRank.REGULAR,
                EmployeeRank.SUPERVISOR,
                EmployeeRank.DEPARTMENT_HEAD,
                EmployeeRank.BENCO
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
                RequestStatus.AWAITING_SUPERVISOR,
                RequestStatus.AWAITING_DEPT_HEAD,
                RequestStatus.AWAITING_BENCO,
                RequestStatus.AWAITING_GRADE,
                RequestStatus.DENIED,
                RequestStatus.GRANTED,
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
                Department.RAVENCLAW,
                Department.HUFFLEPUFF,
                Department.GYFFINDOR,
                Department.SLYTHERIN
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
                new Pair<>(EventType.UNIVERSITY_COURSE, 80f),
                new Pair<>(EventType.SEMINAR, 60f),
                new Pair<>(EventType.CERTIFICATION_PREP, 75f),
                new Pair<>(EventType.CERTIFICATION, 100f),
                new Pair<>(EventType.TECH_TRAINING, 90f),
                new Pair<>(EventType.OTHER, 30f)
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
