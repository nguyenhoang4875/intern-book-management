package com.intern.book.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BookDto {

    private Integer id;

    @NonNull
    @NotEmpty
    private String title;

    @NonNull
    @NotEmpty
    private String description;

    @NonNull
    @NotEmpty
    private String author;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean enabled;

    private String username;

}
