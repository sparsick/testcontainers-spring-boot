package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ContextConfiguration(initializers = DatabaseBaseTest.Initializer.class)
@Testcontainers
public abstract class DatabaseBaseTest {

    static final MySQLContainer DATABASE = new MySQLContainer();

    static {
        DATABASE.start();
    }

    static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext
                                       configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + DATABASE.getJdbcUrl(),
                    "spring.datasource.username=" + DATABASE.getUsername(),
                    "spring.datasource.password=" + DATABASE.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
