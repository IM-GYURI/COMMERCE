package zerobase.sellerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SellerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SellerApiApplication.class, args);
  }

}
