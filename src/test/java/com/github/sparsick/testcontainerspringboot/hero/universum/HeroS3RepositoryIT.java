package com.github.sparsick.testcontainerspringboot.hero.universum;

import com.github.sparsick.testcontainerspringboot.hero.HeroConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;


@Testcontainers
@SpringJUnitConfig(classes= {HeroS3Repository.class, HeroConfiguration.class})
class HeroS3RepositoryIT {

    @Container
    private static LocalStackContainer localstack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:1.2.0"))
            .withServices(S3);

    @Autowired
    private HeroS3Repository repositoryUnderTest;


    @Test
    void testInteractionWithDatabase(){
        repositoryUnderTest.addHero(new Hero("Wonder Woman", "London", ComicUniversum.DC_COMICS));
        repositoryUnderTest.addHero(new Hero("Batman", "Gotham City", ComicUniversum.DC_COMICS));

        Collection<Hero> heroes = repositoryUnderTest.allHeros();

        assertThat(heroes).hasSize(2);
    }


    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("aws.s3.endpoint", () -> localstack.getEndpointOverride(Service.S3));
        registry.add("aws.s3.accessKey",   localstack::getAccessKey);
        registry.add("aws.s3.secretKey", localstack::getSecretKey);
        registry.add("aws.s3.region", localstack::getRegion);
    }

}