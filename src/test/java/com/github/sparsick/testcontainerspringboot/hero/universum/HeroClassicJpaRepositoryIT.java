package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class HeroClassicJpaRepositoryIT {
    @Container
    private static MySQLContainer database = new MySQLContainer("mysql:5.7.34");

    @Autowired
    private HeroClassicJpaRepository repositoryUnderTest;

    @Test
    void findAllHero(){
        int numberHeros = repositoryUnderTest.allHeros().size();

        repositoryUnderTest.addHero(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));
        repositoryUnderTest.addHero(new Hero("Superman", "Metropolis", ComicUniversum.DC_COMICS));

        Collection<Hero> heros = repositoryUnderTest.allHeros();

        assertThat(heros).hasSize(numberHeros + 2);
    }

    @Test
    void findHeroByCriteria(){
        repositoryUnderTest.addHero(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));

        Collection<Hero> heros = repositoryUnderTest.findHerosBySearchCriteria("Batman");

        assertThat(heros).contains(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }
}