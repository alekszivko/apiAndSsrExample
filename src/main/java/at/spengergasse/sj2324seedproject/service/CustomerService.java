package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.CustomerDTO;
import at.spengergasse.sj2324seedproject.service.connector.CustomerDataClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerDataClient customerDataClient;

    public Optional<CustomerDTO> retrieveCustomerData(String customerId) {
        return customerDataClient.retrieveCustomerData(customerId);
    }
}
