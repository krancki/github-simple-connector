package com.krancki.githubconnector.github.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RepositoryDto {
    String fullName;
    String description;
    String cloneUrl;
    int numberOfStargazers;
    LocalDateTime createdAt;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RepositoryDto(
            String fullName,
            String description,
            String cloneUrl,
            int numberOfStargazers,
            @JsonSerialize(using = LocalDateTimeSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime createdAt
    ) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.numberOfStargazers = numberOfStargazers;
        this.createdAt = createdAt;
    }
}