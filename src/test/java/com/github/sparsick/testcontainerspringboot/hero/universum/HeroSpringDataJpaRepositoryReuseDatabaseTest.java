package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class HeroSpringDataJpaRepositoryReuseDatabaseTest extends DatabaseBaseTest {

    @Autowired
    private HeroSpringDataJpaRepository repositoryUnderTest;

    @Test
    void findHerosBySearchCriteria() {
        repositoryUnderTest.save(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));

        Collection<Hero> heros = repositoryUnderTest.findHerosBySearchCriteria("Batman");

        assertThat(heros).hasSize(1).contains(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));
    }

}