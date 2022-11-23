package com.github.sparsick.testcontainerspringboot.hero.universum;


import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.Testcontainers.exposeHostPorts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HeroStartPagePlaywrightIT {

    @Container
    private static GenericContainer<?> chromeContainer = new GenericContainer<>( DockerImageName.parse( "browserless/chrome:1.54.0-chrome-stable" ) )
            .withAccessToHost( true )
            .withExposedPorts( 3000 )
            .waitingFor( Wait.forHttp( "/" ) );

    @Container
    private static final MySQLContainer database = new MySQLContainer("mysql:5.7.34");

    @LocalServerPort
    private int heroPort;

    private Playwright playwright;
    private Browser browser;



    @BeforeEach
    void setUp(){
        exposeHostPorts(heroPort);
        playwright = Playwright.create();
        browser = playwright.chromium( ).connectOverCDP( "ws://" + chromeContainer.getHost( ) + ":" + chromeContainer.getFirstMappedPort( ) );
    }
    @AfterEach
    void cleanUp() {
        browser.close();
        playwright.close();
    }

    @Test
    void titleIsHeroSearchMachine(){
        Page page = browser.newPage();
        page.navigate("http://host.testcontainers.internal:" + heroPort + "/hero");

        String title = page.textContent("h1").trim();
        assertThat(title)
                .isEqualTo("Hero Search Machine");
    }

    @Test
    void searchForHero(){
        Page page = browser.newPage();
        page.navigate("http://host.testcontainers.internal:" + heroPort + "/hero");
        page.fill("id=search", "Batman");
        page.click("id=button-search");

        String title = page.textContent("h1").trim();
        assertThat(title)
                .isEqualTo("Hero List");
    }

    @Test
    void addNewHero(){
        Page page = browser.newPage();
        page.navigate("http://host.testcontainers.internal:" + heroPort + "/hero");

        page.click("id=button-add");

        String title = page.textContent("h1").trim();
        assertThat(title)
                .isEqualTo("New Hero");
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }


}
