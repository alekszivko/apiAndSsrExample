package at.spengergasse.sj2324seedproject.presentation.api;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.CustomerService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

  private @Autowired MockMvc mockMvc;
  private @MockitoBean CustomerService customerService;


  @BeforeEach
  void setUp() {
    assumeThat(mockMvc).isNotNull();
  }

  @Test
  void ensureFetchCustomerDataReturnsOkForExistingCustomer() throws Exception {
    //given
    var customer = FixtureFactory.customerDTOFixture("123");
    when(customerService.retrieveCustomerData("123")).thenReturn(Optional.of(customer));

    //expect
    var request = get(CustomerRestController.BASE_URL + "?connectionNo=123")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(request).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print());
  }

  @Test
  void ensureFetchCustomerDataReturnsCustomerDataForExistingCustomer() throws Exception {
    //given
    var customer = FixtureFactory.customerDTOFixture("123");
    when(customerService.retrieveCustomerData("123")).thenReturn(Optional.of(customer));

    //expect
    var request = get(CustomerRestController.BASE_URL + "?connectionNo=123")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(request).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(customer.id()))
        .andExpect(jsonPath("$.firstName").value(customer.firstName()))
        .andExpect(jsonPath("$.dateOfBirth").value(customer.dateOfBirth())).andDo(print());
  }
}