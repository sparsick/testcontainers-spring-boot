package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class HeroRepository  {

    private Set<Hero> heroes = new HashSet<>();

    @PostConstruct
    public void init() {
        heroes.add(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));
        heroes.add(new Hero("Superman", "Metropolis", ComicUniversum.DC_COMICS));
    }

    public void addHero(Hero hero) {
        heroes.add(hero);
    }

    public Collection<Hero> allHeros() {
        return new HashSet<>(heroes);
    }

    public Collection<Hero> findHerosBySearchCriteria(String searchCriteria) {
        return heroes.stream().filter( hero -> StringUtils.containsIgnoreCase(hero.toString(), searchCriteria)).collect(Collectors.toSet());
    }

}
