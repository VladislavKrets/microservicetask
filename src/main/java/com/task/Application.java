package com.task;
import com.task.model.DAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        DAO.getInstance();
        SpringApplication.run(Application.class, args);
    }

}