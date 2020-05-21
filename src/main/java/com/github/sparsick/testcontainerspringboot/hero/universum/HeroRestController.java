package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroRestController {

    private final HeroSpringDataJpaRepository heroRepository;

    public HeroRestController(HeroSpringDataJpaRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @GetMapping("heros")
    public Iterable<Hero> allHeros(String searchCriteria) {
        if (searchCriteria == null || searchCriteria.equals("")) {
            return heroRepository.findAll();

        }

        return heroRepository.findHerosBySearchCriteria(searchCriteria);
    }

    @PostMapping("hero")
    public void hero(@RequestBody Hero hero) {
        heroRepository.save(hero);
    }
}
