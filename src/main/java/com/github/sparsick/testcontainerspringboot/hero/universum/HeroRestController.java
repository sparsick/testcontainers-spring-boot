package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroRestController {

    private final HeroSpringDataJpaRepository heroController;

    public HeroRestController(HeroSpringDataJpaRepository heroController) {
        this.heroController = heroController;
    }

    @GetMapping("heros")
    public Iterable<Hero> allHeros(String searchCriteria) {
        if (searchCriteria == null || searchCriteria.equals("")) {
            return heroController.findAll();

        }

        return heroController.findHerosBySearchCriteria(searchCriteria);
    }

    @PostMapping("hero")
    public void hero(@RequestBody Hero hero) {
        heroController.save(hero);
    }

}
