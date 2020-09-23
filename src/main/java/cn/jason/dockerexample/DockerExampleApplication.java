package cn.jason.dockerexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class DockerExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerExampleApplication.class, args);
    }

    @GetMapping("/hello")
    public Mono<String> hello(){
        log.info("hello request");
        return Mono.just("hello docker!");
    }

}
