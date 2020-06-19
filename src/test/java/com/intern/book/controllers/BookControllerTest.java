package com.intern.book.controllers;

import com.google.gson.Gson;
import com.intern.book.models.dto.BookDto;
import com.intern.book.services.BookService;
import org.hamcrest.Matchers;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private BookDto bookDto1;
    private BookDto bookDto2;

    @BeforeEach
    public void init() {
        bookDto1 = new BookDto();
        bookDto1.setId(1);
        bookDto1.setTitle("Book 1");
        bookDto1.setDescription("description 1");
        bookDto1.setAuthor("author 1");
        bookDto1.setEnabled(true);
        bookDto1.setUsername("teo");

        bookDto2 = new BookDto();
        bookDto2.setId(2);
        bookDto2.setTitle("Book 2");
        bookDto2.setDescription("description 2");
        bookDto2.setAuthor("author 2");
        bookDto2.setEnabled(true);
        bookDto2.setUsername("ti");
    }

    @Test
    public void test_getAllBooksEnabled() throws Exception {
        Mockito.when(bookService.getAllBooksEnabled()).thenReturn(Arrays.asList(bookDto1, bookDto2));
        mockMvc.perform(get("/api/books/enabled"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void test_getBookById() throws Exception {
        Mockito.when(bookService.getBookById(bookDto1.getId())).thenReturn(bookDto1);
        mockMvc.perform(get("/api/books/" + bookDto1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test_search() throws Exception {
        Mockito.when(bookService.search(bookDto1.getAuthor())).thenReturn(Arrays.asList(bookDto1));
        mockMvc.perform(get("/api/books/search?keyword=" + bookDto1.getAuthor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void test_addBook() throws Exception {
        BookDto bookDto3 = new BookDto();
        bookDto3.setId(3);
        bookDto3.setTitle("Book 3");
        bookDto3.setDescription("description 3");
        bookDto3.setAuthor("author 3");
        bookDto3.setEnabled(true);
        bookDto3.setUsername("nam");

        Gson gson = new Gson();
        String json = gson.toJson(bookDto3);

        Mockito.when(bookService.addBook(bookDto3)).thenReturn(bookDto3);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateBook_Okay() throws Exception {
        BookDto bookDto3 = new BookDto();
        bookDto3.setId(3);
        bookDto3.setTitle("Book 3");
        bookDto3.setDescription("description 3");
        bookDto3.setAuthor("author 3");
        bookDto3.setEnabled(true);
        bookDto3.setUsername("nam");

        Gson gson = new Gson();
        String json = gson.toJson(bookDto3);

        Mockito.when(bookService.checkCanEditBook(bookDto3.getId())).thenReturn(true);
        Mockito.when(bookService.updateBook(bookDto3)).thenReturn(bookDto3);
        mockMvc.perform(put("/api/books/" + bookDto3.getId())
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateBook_Fail() throws Exception {
        BookDto bookDto3 = new BookDto();
        bookDto3.setId(3);
        bookDto3.setTitle("Book 3");
        bookDto3.setDescription("description 3");
        bookDto3.setAuthor("author 3");
        bookDto3.setEnabled(true);
        bookDto3.setUsername("nam");

        Gson gson = new Gson();
        String json = gson.toJson(bookDto3);

        Mockito.when(bookService.updateBook(bookDto3)).thenReturn(bookDto3);
        mockMvc.perform(put("/api/books/" + bookDto3.getId())
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_deleteBook_Okay() throws Exception {
        Mockito.when(bookService.checkCanEditBook(bookDto2.getId())).thenReturn(true);
        mockMvc.perform(delete("/api/books/" + bookDto2.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteBook_Fail() throws Exception {
        mockMvc.perform(delete("/api/books/" + bookDto2.getId()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
