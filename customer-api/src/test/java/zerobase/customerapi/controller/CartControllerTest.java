package zerobase.customerapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import zerobase.common.type.Role;
import zerobase.customerapi.CustomerApiApplication;
import zerobase.customerapi.dto.cart.CartItemEditDto;
import zerobase.customerapi.dto.cart.CartItemRegisterDto;
import zerobase.customerapi.dto.customer.CustomerDto;
import zerobase.customerapi.entity.CartEntity;
import zerobase.customerapi.security.CustomerTokenProvider;
import zerobase.customerapi.service.CartService;
import zerobase.customerapi.service.CustomerService;

@SpringBootTest(classes = CustomerApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CustomerTokenProvider tokenProvider;

  @MockBean
  private CustomerService customerService;

  @MockBean
  private CartService cartService;

  @BeforeEach
  void setup() {
    CustomerDto customerDto = CustomerDto.builder()
        .customerKey("C12345")
        .email("testuser@example.com")
        .point(0L)
        .address("address 12345")
        .birth(LocalDate.parse("2002-02-02"))
        .phone("010-0000-0000")
        .role(Role.CUSTOMER)
        .build();

    when(customerService.validateAuthorizationAndGetCustomer("C12345", "testuser@example.com"))
        .thenReturn(customerDto);

    CartEntity mockCart = CartEntity.builder()
        .customerKey("C12345").build();

    when(cartService.getCartByCustomerKey("C12345")).thenReturn(mockCart);
  }

  @Test
  void testCartInformation() throws Exception {
    String token = tokenProvider.generateToken(CustomerDto.builder()
        .customerKey("C12345")
        .email("testuser@example.com")
        .point(0L)
        .address("address 12345")
        .birth(LocalDate.parse("2002-02-02"))
        .phone("010-0000-0000")
        .role(Role.CUSTOMER)
        .build());

    mockMvc.perform(get("/cart/C12345")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testAddProductToCart() throws Exception {
    String token = tokenProvider.generateToken(CustomerDto.builder()
        .customerKey("C12345")
        .email("testuser@example.com")
        .point(0L)
        .address("address 12345")
        .birth(LocalDate.parse("2002-02-02"))
        .phone("010-0000-0000")
        .role(Role.CUSTOMER)
        .build());

    CartItemRegisterDto cartItemRegisterDto = CartItemRegisterDto.builder()
        .productKey("P123")
        .count(2L)
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/cart/add-product/C12345")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cartItemRegisterDto)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerKey").value("C12345"));
  }

  @Test
  void testEditCartProductQuantity() throws Exception {
    String token = tokenProvider.generateToken(CustomerDto.builder()
        .customerKey("C12345")
        .email("testuser@example.com")
        .point(0L)
        .address("address 12345")
        .birth(LocalDate.parse("2002-02-02"))
        .phone("010-0000-0000")
        .role(Role.CUSTOMER)
        .build());

    CartItemEditDto cartItemEditDto = CartItemEditDto.builder()
        .productKey("P123")
        .newCount(3L)
        .build();

    mockMvc.perform(patch("/cart/edit-product-quantity/C12345")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cartItemEditDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customerKey").value("C12345"));
  }

  @Test
  void testClearCart() throws Exception {
    String token = tokenProvider.generateToken(CustomerDto.builder()
        .customerKey("C12345")
        .email("testuser@example.com")
        .point(0L)
        .address("address 12345")
        .birth(LocalDate.parse("2002-02-02"))
        .phone("010-0000-0000")
        .role(Role.CUSTOMER)
        .build());

    mockMvc.perform(delete("/cart/clear/C12345")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}