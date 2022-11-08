package com.github.sparsick.testcontainerspringboot.hero.universum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Hero {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String city;

    private ComicUniversum universum;

    public Hero(String name, String city, ComicUniversum universum) {
        this.name = name;
        this.city = city;
        this.universum = universum;
    }

    public Hero() {

    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public ComicUniversum getUniversum() {
        return universum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUniversum(ComicUniversum universum) {
        this.universum = universum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return Objects.equals(name, hero.name) &&
                Objects.equals(city, hero.city) &&
                universum == hero.universum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, universum);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", universum=" + universum.getDisplayName() +
                '}';
    }
}
