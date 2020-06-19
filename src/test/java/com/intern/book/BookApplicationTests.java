package com.intern.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BookApplicationTests {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Test
    void contextLoads() {
    }

    @Test
    public void test_dataSourceUrl() {
        Assertions.assertEquals(dataSourceUrl, "jdbc:postgresql://localhost:5555/book_test");
    }

}
