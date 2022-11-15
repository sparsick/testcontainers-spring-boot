package com.github.sparsick.testcontainerspringboot.hero.universum;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.Testcontainers.exposeHostPorts;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HeroStartPageIT {

    @Container
    private static final BrowserWebDriverContainer<?> chromeBrowserContainer = new BrowserWebDriverContainer<>() // one browser for all tests
            .withCapabilities(new ChromeOptions())
            .withAccessToHost(true);

    @Container
    private static MySQLContainer database = new MySQLContainer("mysql:5.7.34");

    @LocalServerPort
    private int heroPort;

    private RemoteWebDriver browser;


    @BeforeEach
    void setUp(){
        exposeHostPorts(heroPort);
        browser = chromeBrowserContainer.getWebDriver();
    }

    @Test
    void titleIsHeroSearchMachine(){
        browser.get("http://host.testcontainers.internal:" + heroPort + "/hero");
        WebElement title = browser.findElement(By.tagName("h1"));
        assertThat(title.getText().trim())
                .isEqualTo("Hero Search Machine");
    }

    @Test
    void searchForHero(){
        browser.get("http://host.testcontainers.internal:" + heroPort + "/hero");
        WebElement formTextField = browser.findElement(By.id("search"));
        formTextField.sendKeys("Batman");

        WebElement searchButton = browser.findElement(By.id("button-search"));
        searchButton.click();

        WebElement title = browser.findElement(By.tagName("h1"));
        assertThat(title.getText().trim())
                .isEqualTo("Hero List");
    }

    @Test
    void addNewHero(){
        browser.get("http://host.testcontainers.internal:" + heroPort + "/hero");

        WebElement addNewHeroLink = browser.findElement(By.id("button-add"));
        addNewHeroLink.click();

        WebElement title = browser.findElement(By.tagName("h1"));
        assertThat(title.getText().trim())
                .isEqualTo("New Hero");
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }


}
