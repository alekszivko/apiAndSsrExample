package at.spengergasse.sj2324seedproject.service.connector;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.CustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerDataClientTest {

    @Mock
    private CustomerDataRestClient restClient;

    @InjectMocks
    private CustomerDataClient customerDataClient;

    @Test
    void ensureRetrieveCustomerDataReturnsValue() throws JsonProcessingException {
        // given
        CustomerDTO expectedCustomer = CustomerDTO.builder()
            .id("123")
            .firstName("John")
            .lastName("Doe")
            .dateOfBirth("01.01.1970")
            .address("Main Street 1")
            .country("USA")
            .city("New York")
            .zipCode("12345")
            .phoneNumber("123456789")
            .email("randomEmail@randomEmail.com")
            .build();

        String customerJson = new ObjectMapper().writeValueAsString(expectedCustomer);
        Map<String, Object> mockResponse = Map.of("data", customerJson);

        when(restClient.getCustomerData("123")).thenReturn(mockResponse);

        // when
        Optional<CustomerDTO> retrievedData = customerDataClient.retrieveCustomerData("123");

        // then
        assertThat(retrievedData).isNotEmpty();
        assertThat(retrievedData.get().id()).isEqualTo("123");
    }
}
