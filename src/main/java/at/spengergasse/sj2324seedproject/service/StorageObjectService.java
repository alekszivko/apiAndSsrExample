package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Customer;
import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.foundation.ApiKeyGenerator;
import at.spengergasse.sj2324seedproject.persistence.StorageObjectRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Transactional
public class StorageObjectService {

    @Inject
    StorageObjectRepository storageObjectRepository;

    @Inject
    ApiKeyGenerator apiKeyGenerator;

    public List<StorageObject> findAll() {
        return storageObjectRepository.listAll();
    }

    public Stream<StorageObject> fetchStorageObjectsStream(Optional<String> searchParam) {
        return storageObjectRepository.listAll().stream();
    }

    public Optional<StorageObject> findStorageObjectByMac(String macAddress) {
        return storageObjectRepository.findByMacAddress(macAddress);
    }

    public void createStorageObject(String randomKey,
        String storage,
        String serial,
        String mac,
        String remark,
        String project,
        String storedAtCu) {
        String generatedKey = apiKeyGenerator.getRandomKey(16);
        log.debug("Creating storage object with key {}", generatedKey);
        StorageObject storageObject = StorageObject.builder()
            .apiKeyID(generatedKey)
            .storedStorage(storage.isEmpty() ? Storage.builder().name("Empty").build()
                : Storage.builder().name(storage).build())
            .serialNumber(serial.isEmpty() ? "Empty" : serial)
            .macAddress(mac.isEmpty() ? "Empty" : mac)
            .remark(mac.isEmpty() ? "Empty" : remark)
            .projectDevice(!project.isEmpty())
            .storedAtCustomer(Customer.builder()
                .connectionNo(storedAtCu.isEmpty() ? "No Customer" : storedAtCu)
                .build())
            .build();
        storageObjectRepository.persist(storageObject);
    }

    public void delete(String key) {
        storageObjectRepository.deleteByApiKeyID(key);
    }

    public Optional<StorageObject> getStorageObjectByKey(String key) {
        return storageObjectRepository.findByApiKeyID(key);
    }

    public StorageObject updateStorageObject(String apiKey,
        String storage,
        String serialNr,
        String mac,
        String remark,
        String projectDev,
        String storedAtCu) {
        return storageObjectRepository.findByApiKeyID(apiKey)
            .map(sto -> {
                sto.setApiKeyID(apiKey);
                sto.setStoredStorage(Storage.builder().name(storage).build());
                sto.setSerialNumber(serialNr);
                sto.setMacAddress(mac);
                sto.setRemark(remark);
                sto.setProjectDevice(!projectDev.isEmpty());
                sto.setStoredAtCustomer(Customer.builder().connectionNo(storedAtCu).build());
                return sto;
            }).orElseThrow(() -> new IllegalArgumentException(
                "StorageObject with key %s doesnt exist in DB".formatted(apiKey)));
    }

    public Stream<StorageObject> searchFind(String search) {
        String keyword = "%% %s %%".formatted(search);
        return storageObjectRepository.searchStorageObjects(keyword).stream();
    }
}
