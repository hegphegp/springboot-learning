package com.hegp;

import com.hegp.domain.Worker;
import com.hegp.service.business.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private WorkerService workerService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String name = "name";
        String tel = "tel";
        String username = "username";
        String password = "password";
        String role = "role";
        String position = "position";
        String street = "street";
        boolean del = false;
        Worker worker = new Worker(name, tel, username, password, role, position, street);
        workerService.save(worker);
        worker = new Worker(name, tel, username, password, role, position, street);
        workerService.save(worker);
        worker = new Worker(name, tel, username, password, role, position, street);
        workerService.save(worker);
        worker = new Worker(name, tel, username, password, role, position, street);
        workerService.save(worker);
        worker = new Worker(name, tel, username, password, role, position, street);
        workerService.save(worker);
        worker = new Worker(name, tel, username, password, role, position, street);
        workerService.save(worker);
        System.out.println(workerService.selectAll());
    }
}
