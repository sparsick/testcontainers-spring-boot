package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeroSpringDataJpaRepository extends CrudRepository<Hero, Long> {

    @Query("SELECT hero FROM Hero hero where hero.city LIKE :searchCriteria OR hero.name LIKE :searchCriteria OR hero.universum = :searchCriteria")
    List<Hero> findHerosBySearchCriteria(@Param("searchCriteria") String searchCriteria);
}
