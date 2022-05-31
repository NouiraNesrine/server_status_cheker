package com.fullstack.back;

import com.fullstack.back.enumeration.Status;
import com.fullstack.back.model.Server;
import com.fullstack.back.repository.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepo serverRepo){
        return args -> {
            serverRepo.save(new Server(null,"192.168.1.168","Ubuntu","16 GB","Personal",
                    "http://localhost:8080/server/image/server1.png", Status.UP));
            serverRepo.save(new Server(null,"192.168.1.167","Fedora","32 GB","Personal",
                    "http://localhost:8080/server/image/server2.png", Status.UP));
            serverRepo.save(new Server(null,"192.168.1.166","Red Hat","16 GB","Personal",
                    "http://localhost:8080/server/image/server3.png", Status.UP));
            serverRepo.save(new Server(null,"192.168.1.13","MS","8 GB","Personal",
                    "http://localhost:8080/server/image/server4.png", Status.UP));
        };
    }

}
