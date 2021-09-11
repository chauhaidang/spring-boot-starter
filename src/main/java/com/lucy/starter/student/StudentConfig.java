package com.lucy.starter.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.DECEMBER;
import static java.time.Month.SEPTEMBER;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student dang = new Student(
                    "Dang",
                    "dang@gmail.com",
                    LocalDate.of(1995, SEPTEMBER, 9)
            );

            Student phuong = new Student(
                    "Phuong",
                    "phuong@gmail.com",
                    LocalDate.of(1995, DECEMBER, 31)
            );

            studentRepository.saveAll(
                    List.of(dang, phuong)
            );
        };
    }
}
