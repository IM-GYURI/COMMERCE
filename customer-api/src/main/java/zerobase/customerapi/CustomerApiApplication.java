package zerobase.customerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan(basePackages = {"zerobase.common", "zerobase.customerapi"})
public class CustomerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApiApplication.class, args);
  }

}
