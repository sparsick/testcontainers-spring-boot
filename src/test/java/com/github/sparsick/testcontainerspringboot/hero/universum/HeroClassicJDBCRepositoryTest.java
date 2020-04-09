package com.github.sparsick.testcontainerspringboot.hero.universum;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.delegate.DatabaseDelegate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class HeroClassicJDBCRepositoryTest {


    @Container
    private MySQLContainer database = new MySQLContainer();


    private HeroClassicJDBCRepository repositoryUnderTest;

    @BeforeEach
    void setUp(){
        repositoryUnderTest = new HeroClassicJDBCRepository(dataSource());
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(database, ""),"ddl.sql");
    }

    @Test
    void testInteractionWithDatabase() {
        repositoryUnderTest.addHero(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));

        Collection<Hero> heroes = repositoryUnderTest.allHeros();

        assertThat(heroes).hasSize(1);
    }

    @NotNull
    private DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(database.getJdbcUrl());
        dataSource.setUser(database.getUsername());
        dataSource.setPassword(database.getPassword());
        return dataSource;
    }
}