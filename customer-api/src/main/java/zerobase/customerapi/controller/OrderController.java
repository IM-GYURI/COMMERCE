package zerobase.customerapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.customerapi.dto.customer.CustomerDto;
import zerobase.customerapi.dto.order.OrderDto;
import zerobase.customerapi.security.CustomerTokenProvider;
import zerobase.customerapi.service.CustomerService;
import zerobase.customerapi.service.OrderService;

@RequestMapping("/order")
@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService;
  private final CustomerService customerService;
  private final CustomerTokenProvider customerTokenProvider;

  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping("/{customerKey}")
  public ResponseEntity<?> makeOrder(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    OrderDto orderDto = orderService.makeOrder(customerKey);

    return ResponseEntity.ok(orderDto);
  }
}
