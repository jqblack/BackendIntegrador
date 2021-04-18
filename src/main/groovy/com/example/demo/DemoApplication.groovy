package com.example.demo

import com.example.demo.controller.ComplementosController
import com.example.demo.services.ComplementosService
import org.apache.tomcat.util.descriptor.web.Injectable
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter



@SpringBootApplication
class DemoApplication {

    static void main(String[] args) {
        SpringApplication.run(DemoApplication, args)
    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:3000/");
//            }
//        };
//    }

    //LA VAINA

    //ComplementosController complementosController = new ComplementosController()

//    @Scheduled(initialDelay = 1000L, fixedDelayString = "PT5S")
//    public void TareaProgramada() throws InterruptedException{
//        complementosController.Ejecturar()
//    }



    @EnableWebMvc
    public class WebConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
        }
    }

    @Configuration
    @EnableScheduling
    @ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
    class SchedulingConfiguration{

//        @Scheduled(initialDelay = 1000L, fixedDelayString = "PT5S")
//        public void TareaProgramada() throws InterruptedException{
//
//            ComplementosService complementosService
//
//            complementosService.ExecuteTrigger()
//        }
    }

}
