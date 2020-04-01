package com.krancki.githubconnector.github;

import com.krancki.githubconnector.github.dto.RepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/repositories")
@RestController
@RequiredArgsConstructor
class RepositoryController {

    private final GithubRepositoryReader githubRepositoryReader;

    @GetMapping("/{owner}/{repositoryName}")
    RepositoryDto getGithubRepository(
            @PathVariable String owner,
            @PathVariable String repositoryName
    ) {
        return RepositoryMapper.mapToRepositoryDto(githubRepositoryReader.getUnAuthorizeUserRepository(owner, repositoryName));
    }
}