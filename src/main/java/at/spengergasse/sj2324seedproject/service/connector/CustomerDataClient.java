package at.spengergasse.sj2324seedproject.service.connector;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.CustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@ApplicationScoped
public class CustomerDataClient {

    @Inject
    @RestClient
    CustomerDataRestClient restClient;

    //TODO connect to API that returns dynamic/"real" Customer data
    public Optional<CustomerDTO> retrieveCustomerData(String customerId) {
        log.debug("Retrieving customer data for id {}", customerId);
        try {
            Map<String, Object> customerResp = restClient.getCustomerData(customerId);
            ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
            CustomerDTO dto = mapper.readValue(
                customerResp.get("data").toString(), CustomerDTO.class);
            return Optional.of(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse customer data response", e);
        }
    }

    //TODO remove when connected to real API
    @SuppressWarnings("unused")
    private String serializeCustomerDto(String customerId) {
        var mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(CustomerDTO.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth("01.01.1970")
                .address("Main Street 1")
                .country("USA")
                .city("New York")
                .zipCode("12345")
                .phoneNumber("123456789")
                .email("randomEmail@randomEmail.com")
                .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize CustomerDTO to JSON.", e);
        }
    }
}
