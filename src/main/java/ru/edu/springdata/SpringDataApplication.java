package ru.edu.springdata;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringDataApplication {

    private final JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }

    @PostConstruct
    private void initDb() {
        jdbcTemplate.execute("CREATE TABLE book(id SERIAL, name VARCHAR(255), category VARCHAR(255), language VARCHAR(255));");
        jdbcTemplate.execute(
                "insert into book (name, category, language) values ('book1', 'category1', 'language1');\n" +
                        "insert into book (name, category, language) values ('book2', 'category1', 'language1');\n" +
                        "insert into book (name, category, language) values ('book3', 'category1', 'language1');\n" +
                        "insert into book (name, category, language) values ('book4', 'category2', 'language2');\n" +
                        "insert into book (name, category, language) values ('book5', 'category2', 'language2');\n" +
                        "insert into book (name, category, language) values ('book6', 'category2', 'language2');\n"
        );
    }
}
