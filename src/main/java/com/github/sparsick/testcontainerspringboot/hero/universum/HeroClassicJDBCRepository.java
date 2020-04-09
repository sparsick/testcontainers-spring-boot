package com.github.sparsick.testcontainerspringboot.hero.universum;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;

public class HeroClassicJDBCRepository {

    private JdbcTemplate jdbcTemplate;

    public HeroClassicJDBCRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addHero(Hero hero) {
        jdbcTemplate.update("insert into hero (city, name, universum) values (?,?,?)", hero.getCity(), hero.getName(), hero.getUniversum().name());

    }

    public Collection<Hero> allHeros() {
        return jdbcTemplate.query("select * From hero",
                (resultSet, i) -> new Hero(resultSet.getString("name"), resultSet.getString("city"), ComicUniversum.valueOf(resultSet.getString("universum"))));
    }

}
