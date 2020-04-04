package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public class HeroClassicJpaRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addHero(Hero hero) {
        em.persist(hero);
    }

    public Collection<Hero> allHeros() {
        return em.createQuery("Select hero FROM Hero hero", Hero.class).getResultList();
    }

    public Collection<Hero> findHerosBySearchCriteria(String searchCriteria) {
        return em.createQuery("SELECT hero FROM Hero hero " +
                        "where hero.city LIKE :searchCriteria OR " +
                        "hero.name LIKE :searchCriteria OR " +
                        "hero.universum = :searchCriteria",
                Hero.class)
                .setParameter("searchCriteria", searchCriteria).getResultList();
    }

}
