package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Address;
import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.persistence.StorageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class StorageService {

    @Inject
    StorageRepository repository;

    public List<Storage> fetchStorage(Optional<String> namePart) {
        return namePart.map(repository::findAllByNameContainingIgnoreCase)
            .orElseGet(repository::listAll);
    }

    public void createStorage(String name, String street, Integer number,
        String addressAddition, Integer zipcode, String city) {
        Storage storage = Storage.builder()
            .name(name)
            .address(Address.builder()
                .street(street)
                .number(number)
                .addressAddition(addressAddition)
                .zipcode(zipcode)
                .city(city).build())
            .build();
        repository.persist(storage);
    }

    public Optional<Storage> getStorageById(Long id) {
        return repository.findByIdOptional(id);
    }

    public void removeStorage(Long id) {
        repository.deleteById(id);
    }

    public void updateStorage(Long id, String name, String addressAddition, String street,
        Integer number, Integer zipcode, String city) {
        repository.findByIdOptional(id).ifPresent(st -> {
            st.setName(name);
            st.setAddress(Address.builder()
                .city(city)
                .zipcode(zipcode)
                .addressAddition(addressAddition)
                .street(street)
                .number(number)
                .build());
        });
    }
}
