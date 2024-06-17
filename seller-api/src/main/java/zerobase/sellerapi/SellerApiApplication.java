package zerobase.sellerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"zerobase.common", "zerobase.sellerapi"})
public class SellerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SellerApiApplication.class, args);
  }

}
