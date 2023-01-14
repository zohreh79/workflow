package com.payeshgaran.workflow;

import com.payeshgaran.workflow.model.Person;
import com.payeshgaran.workflow.repository.PersonRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkflowApplication {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {

            //insert Persons into database to register in Camunda as users and get priority.

            Person personA = new Person();
            personA.setPriority(1L);
            personA.setUserId("person1");
            personA.setPassword("demo");
            personA.setFirstName("شخص");
            personA.setLastName("اول");
            personA.setEmail("shakhs.aval@gmail.com");

            Person personB = new Person();
            personB.setPriority(2L);
            personB.setUserId("person2");
            personB.setPassword("demo");
            personB.setFirstName("شخص");
            personB.setLastName("دوم");
            personB.setEmail("shakhs.dovom@gmail.com");

            Person personC = new Person();
            personC.setPriority(3L);
            personC.setUserId("person3");
            personC.setPassword("demo");
            personC.setFirstName("شخص");
            personC.setLastName("سوم");
            personC.setEmail("shakhs.sevom@gmail.com");

            Person personD = new Person();
            personD.setPriority(4L);
            personD.setUserId("person4");
            personD.setPassword("demo");
            personD.setFirstName("شخص");
            personD.setLastName("چهارم");
            personD.setEmail("shakhs.chaharom@gmail.com");

            personRepository.save(personA);
            personRepository.save(personB);
            personRepository.save(personC);
            personRepository.save(personD);

            // End of inserting.

            IdentityService identityService = ProcessEngines.getDefaultProcessEngine().getIdentityService();

            // Register persons from database as User in Camunda ,order by priority.

            Person person1 = personRepository.getByPriority(1L);
            Person person2 = personRepository.getByPriority(2L);
            Person person3 = personRepository.getByPriority(3L);
            Person person4 = personRepository.getByPriority(4L);

            User user1 = identityService.newUser(person1.getUserId());
            user1.setId(person1.getUserId());
            user1.setFirstName(person1.getFirstName());
            user1.setLastName(person1.getLastName());
            user1.setPassword(person1.getPassword());
            user1.setEmail(person1.getEmail());

            if ( !identityService.checkPassword(person1.getUserId(), person1.getPassword()) ){
                identityService.saveUser(user1);
            }

            User user2 = identityService.newUser(person2.getUserId());
            user2.setId(person2.getUserId());
            user2.setFirstName(person2.getFirstName());
            user2.setLastName(person2.getLastName());
            user2.setPassword(person2.getPassword());
            user2.setEmail(person2.getEmail());

            if ( !identityService.checkPassword(person2.getUserId(), person2.getPassword()) ){
                identityService.saveUser(user2);
            }

            User user3 = identityService.newUser(person3.getUserId());
            user3.setId(person3.getUserId());
            user3.setFirstName(person3.getFirstName());
            user3.setLastName(person3.getLastName());
            user3.setPassword(person3.getPassword());
            user3.setEmail(person3.getEmail());

            if ( !identityService.checkPassword(person3.getUserId(), person3.getPassword()) ){
                identityService.saveUser(user3);
            }

            User user4 = identityService.newUser(person4.getUserId());
            user4.setId(person4.getUserId());
            user4.setFirstName(person4.getFirstName());
            user4.setLastName(person4.getLastName());
            user4.setPassword(person4.getPassword());
            user4.setEmail(person4.getEmail());

            if ( !identityService.checkPassword(person4.getUserId(), person4.getPassword()) ){
                identityService.saveUser(user4);
            }

        };
    }
}