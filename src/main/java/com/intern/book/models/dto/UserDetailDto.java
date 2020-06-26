package com.intern.book.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDetailDto {
    private Integer id;

    @NotNull
    @NotEmpty
    private String username;

    private String firstName;

    private String lastName;

    private  boolean enabled;

    @NotNull
    @NotEmpty
    private String email;

    private String avatar;

}
