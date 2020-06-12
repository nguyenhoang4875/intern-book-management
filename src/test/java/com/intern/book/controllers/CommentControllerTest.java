package com.intern.book.controllers;

import com.google.gson.Gson;
import com.intern.book.models.dto.CommentDto;
import com.intern.book.services.CommentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class CommentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private CommentDto commentDto1;
    private CommentDto commentDto2;
    private CommentDto commentDto3;

    @BeforeEach
    public void init() {
        commentDto1 = new CommentDto();
        commentDto1.setId(1);
        commentDto1.setMessage("comment 1");
        commentDto1.setCreatedAt(LocalDateTime.now());

        commentDto2 = new CommentDto();
        commentDto2.setId(2);
        commentDto2.setMessage("comment 2");
        commentDto2.setCreatedAt(LocalDateTime.now());

        commentDto3 = new CommentDto();
        commentDto3.setId(3);
        commentDto3.setMessage("comment 3");
    }

    @AfterEach
    public void destroy() {
    }

    @Test
    public void test_getAllCommentsByPost() throws Exception {
        Mockito.when(commentService.getAllCommentsByBook(1)).thenReturn(Set.of(commentDto1, commentDto2));
        mockMvc.perform(get("/api/comments/" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void test_addComment() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(commentDto3);

        Mockito.when(commentService.save(1, commentDto3)).thenReturn(commentDto3);
        mockMvc.perform(post("/api/comments/" + 1)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateComment() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(commentDto3);

        Mockito.when(commentService.save(commentDto1.getId(), commentDto3)).thenReturn(commentDto3);
        mockMvc.perform(put("/api/comments/" + commentDto1.getId())
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteComment() throws Exception {
        mockMvc.perform(delete("/api/comments/" + commentDto3.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
