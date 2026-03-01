package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Customer;
import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.foundation.ApiKeyGenerator;
import at.spengergasse.sj2324seedproject.persistence.StorageObjectRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class StorageObjectService {

  private final StorageObjectRepository storageObjectRepository;
  private final ApiKeyGenerator apiKeyGenerator;

  @Transactional(readOnly = true)
  public List<StorageObject> findAll() {
    return storageObjectRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Stream<StorageObject> fetchStorageObjectsStream(Optional<String> searchParam) {
    return storageObjectRepository.findAll()
        .stream();
  }

  @Transactional(readOnly = true)
  public Optional<StorageObject> findStorageObjectByMac(String macAddress) {
    return storageObjectRepository.findStorageObjectByMacAddress(macAddress);
  }

  public StorageObject findStorageObjectMac(Optional<String> mac) {

    return storageObjectRepository.findByMacAddressContaining(mac);
  }

  public void createStorageObject(String randomKey,
      String storage,
      String serial,
      String mac,
      String remark,
      String project,
      String storedAtCu) {
    StorageObject storageObject = StorageObject.builder()
        .apiKeyID(apiKeyGenerator.getRandomKey(16))
        .storedStorage(storage.isEmpty() ? Storage.builder()
            .name("Empty")
            .build() : Storage.builder()
            .name(storage)
            .build())
        .serialNumber(serial.isEmpty() ? "Empty" : serial)
        .macAddress(mac.isEmpty() ? "Empty" : mac)
        .remark(mac.isEmpty() ? "Empty" : remark)
        .projectDevice(!project.isEmpty())
        .storedAtCustomer(Customer.builder()
            .connectionNo(storedAtCu.isEmpty() ? "No Customer" : storedAtCu)
            .build())
        .build();
    storageObjectRepository.save(storageObject);
  }


  public void delete(String key) {
    storageObjectRepository.deleteStorageObjectByApiKeyID(key);
  }

  @Transactional(readOnly = true)
  public Optional<StorageObject> getStorageObjectByKey(String key) {
    return storageObjectRepository.findStorageObjectByApiKeyID(key);
  }

  public StorageObject updateStorageObject(String apiKey,
      String storage,
      String serialNr,
      String mac,
      String remark,
      String projectDev,
      String storedAtCu) {
    return storageObjectRepository.findStorageObjectByApiKeyID(apiKey)
        .map(sto -> {
          sto.setApiKeyID(apiKey);
          sto.setStoredStorage(Storage.builder()
              .name(storage)
              .build());
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
    return storageObjectRepository.searchStoo("%% %s %%".formatted(search)).stream();
  }
}
