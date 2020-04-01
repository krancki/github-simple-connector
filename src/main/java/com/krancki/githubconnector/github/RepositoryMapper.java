package com.krancki.githubconnector.github;

import com.krancki.githubconnector.github.dto.GithubRepositoryDto;
import com.krancki.githubconnector.github.dto.RepositoryDto;

class RepositoryMapper {

    static RepositoryDto mapToRepositoryDto(GithubRepositoryDto githubRepositoryDto) {
        return new RepositoryDto(
                githubRepositoryDto.getFullName(),
                githubRepositoryDto.getDescription(),
                githubRepositoryDto.getCloneUrl(),
                githubRepositoryDto.getNumberOfStargazers(),
                githubRepositoryDto.getCreatedAt()
        );
    }
}