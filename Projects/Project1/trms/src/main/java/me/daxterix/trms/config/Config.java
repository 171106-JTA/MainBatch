package me.daxterix.trms.config;

import me.daxterix.trms.dao.*;
import me.daxterix.trms.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Properties;

@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class Config {

    public static final int msecsToWait = 1000 * 60;    // every minute

    /**
     * todo: recurring task never fires
     * update requests filed than 2 weeks ago or earlier to urgent status every secsToWait seconds
     */
    @Scheduled(fixedRate=msecsToWait)
    public void updateRequestUrgency() {
        DAOUtils.getRequestDAO().updateOlderToUrgent(LocalDate.now().minusWeeks(2));
        System.out.println("Doing recurring task!");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(System.getenv("DBDC"));
        ds.setUrl(System.getenv("DBUR"));
        ds.setUsername(System.getenv("DBU"));
        ds.setPassword(System.getenv("DBP"));
        return ds;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setPackagesToScan("daxterix.pubhub.model"); // varargs method

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "validate");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        props.setProperty("hibernate.connection.isolation", String.valueOf(Connection.TRANSACTION_READ_COMMITTED));

        // user hibernate default connection pool; not for production
        // props.setProperty("hibernate.connection.pool_size", "10");
        // c3po connection pool
        props.setProperty("hibernate.c3p0.min_size", "3");
        props.setProperty("hibernate.c3p0.max_size", "20");
        props.setProperty("hibernate.c3p0.timeout", "300");
        props.setProperty("hibernate.c3p0.max_statements", "500");

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(    // register @Entity classes
                Department.class,
                Employee.class,
                Address.class,
                EmployeeRank.class,
                EmployeeAccount.class,
                EmployeeInfo.class,
                EventType.class,
                ReimbursementRequest.class,
                RequestFile.class,
                FilePurpose.class,
                RequestStatus.class,
                EventGrade.class,
                MimeType.class,
                RequestHistory.class,
                AccountRequest.class
        );
        return factoryBean;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    PlatformTransactionManager txManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        // to translate hibernate exceptions to spring exceptions (for whatever reason)
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public EmployeeDAO employeeDAO(SessionFactory sessionFactory) {
        EmployeeDAOImpl dao = new EmployeeDAOImpl();
        dao.setSessionFactory(sessionFactory);
        return dao;
    }

    @Bean
    public RequestDAO requestDAO(SessionFactory sessionFactory) {
        return new RequestDAOImpl(sessionFactory);
    }

    @Bean
    public HistoryDAO historyDAO(SessionFactory sessionFactory) {
        return new HistoryDAOImpl(sessionFactory);
    }

    @Bean
    public RequestInfoDAO requestInfoDAO(SessionFactory sessionFactory) {
        return new RequestInfoDAOImpl(sessionFactory);
    }

    @Bean
    public ObjectDAO objectDAO(SessionFactory sessionFactory) {
        return new ObjectDAO(sessionFactory);
    }

}
