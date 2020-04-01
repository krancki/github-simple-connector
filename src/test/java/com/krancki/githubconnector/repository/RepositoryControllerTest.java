package com.krancki.githubconnector.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.krancki.githubconnector.github.dto.GithubRepositoryDto;
import com.krancki.githubconnector.github.dto.RepositoryDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final LocalDateTime localDateTime = LocalDateTime.of(1999, 1, 1, 1, 1, 0);
    private static WireMockRule wireMockRule = new WireMockRule(9000);

    @BeforeAll
    static void setUp() {
        wireMockRule.start();
    }

    @AfterAll
    static void after() {
        wireMockRule.stop();
    }

    @Test
    void shouldReturnRepository() throws Exception {
        //given
        wireMockRule.stubFor(get(urlEqualTo("/repos/user/newRepository"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(getExampleRepositoryDto()))
                )
        );

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/repositories/user/newRepository"));

        //then
        String responseContent = response.andReturn().getResponse().getContentAsString();
        response.andExpect(status().isOk());
        RepositoryDto repository = objectMapper.readValue(responseContent, RepositoryDto.class);

        assertEquals("repoName", repository.getFullName());
        assertEquals("newDescription", repository.getDescription());
        assertEquals("http://localhost/", repository.getCloneUrl());
        assertEquals(12, repository.getNumberOfStargazers());
        assertEquals(localDateTime, repository.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenRepositoryDoesntExist() throws Exception {
        //given
        wireMockRule.stubFor(get(urlEqualTo("/repos/user/notExistRepository"))
                .willReturn(aResponse().withStatus(404))
        );

        //expect
        mockMvc.perform(MockMvcRequestBuilders
                .get("/repositories/user/notExistRepository"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowExceptionWhenGithubIsNotAvailable() throws Exception {
        //given
        wireMockRule.stubFor(get(urlEqualTo("/repos/user/oldRepository"))
                .willReturn(aResponse().withStatus(500))
        );

        //expect
        mockMvc.perform(MockMvcRequestBuilders
                .get("/repositories/user/oldRepository"))
                .andExpect(status().isServiceUnavailable());
    }

    private GithubRepositoryDto getExampleRepositoryDto() {
        return new GithubRepositoryDto(
                "repoName",
                "newDescription",
                "http://localhost/",
                12,
                localDateTime
        );
    }
}