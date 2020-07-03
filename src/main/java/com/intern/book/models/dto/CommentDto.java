package com.intern.book.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {

    private Integer id;

    @NonNull
    @NotNull
    @NotEmpty
    private String message;

    @NonNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String username;

    private String avatarUrl;
}
