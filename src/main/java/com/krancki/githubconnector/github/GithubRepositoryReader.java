package com.krancki.githubconnector.github;

import com.krancki.githubconnector.configuration.GithubProperties;
import com.krancki.githubconnector.github.dto.GithubRepositoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@Service
public class GithubRepositoryReader {

    private static final String GITHUB_SELECTED_USER_REPOSITORY_ENDPOINT = "repos";
    private final RestTemplate restTemplate;
    private final GithubProperties githubProperties;

    public GithubRepositoryDto getUnAuthorizeUserRepository(String userLogin, String repositoryName) {
        URI uri = buildUri(userLogin, repositoryName);
        return restTemplate.getForObject(uri, GithubRepositoryDto.class);
    }

    private URI buildUri(String userLogin, String repositoryName) {
        return UriComponentsBuilder.fromHttpUrl(githubProperties.getUrl())
                .pathSegment(GITHUB_SELECTED_USER_REPOSITORY_ENDPOINT)
                .pathSegment(userLogin)
                .pathSegment(repositoryName)
                .build()
                .toUri();
    }
}