import models.Entity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import services.kohonenNetwork.KohonenNetwork;
import services.kohonenNetwork.KohonenNetworkImpl;
import services.tools.FormatterService;
import services.tools.FormatterServiceImpl;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories
@PropertySource("config.properties")
public class Config {

    @Value("${db.host}")
    private String dbHost;
    @Value("${db.login}")
    private String dbLogin;
    @Value("${db.password}")
    private String dbPassword;
    @Value("${db.showSql}")
    private boolean dbShowSql;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setUrl(dbHost);
        driver.setUsername(dbLogin);
        driver.setPassword(dbPassword);
        return driver;
    }

    @Bean
    public FormatterService formatService() {
        FormatterService<Entity> formatterService = new FormatterServiceImpl<>();
        return formatterService;
    }

    @Bean
    public KohonenNetwork kohonenNetwork(FormatterService formatterService) {
        KohonenNetwork kohonenNetwork = new KohonenNetworkImpl(formatterService, 1, 2);
        return kohonenNetwork;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("models");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        hibernateJpaVendorAdapter.setShowSql(dbShowSql);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}