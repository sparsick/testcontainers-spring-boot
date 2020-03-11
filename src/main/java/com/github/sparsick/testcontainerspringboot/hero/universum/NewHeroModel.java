package com.github.sparsick.testcontainerspringboot.hero.universum;

import java.util.Objects;

public class NewHeroModel {

    private Hero hero;

    private String repository;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewHeroModel that = (NewHeroModel) o;
        return Objects.equals(hero, that.hero) &&
                Objects.equals(repository, that.repository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hero, repository);
    }

    @Override
    public String toString() {
        return "NewHeroModel{" +
                "newHero=" + hero +
                ", repository='" + repository + '\'' +
                '}';
    }
}
