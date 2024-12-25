package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageObjectRepository extends JpaRepository<StorageObject, Long>{
    StorageObject findAllByMacAddress(String mac);

    StorageObject findStorageObjectByMacAddress(Optional<String> mac);

    StorageObject findByMacAddressContaining(Optional<String> mac);

    void deleteStorageObjectByApiKeyID(String key);

    Optional<StorageObject> findStorageObjectByApiKeyID(String key);

    @Query("SELECT stoo "+"FROM StorageObject stoo "+ "LEFT JOIN Storage sto ON stoo.storedStorage.id = sto.id "+ "WHERE sto IS NOT NULL " + "AND ( LOWER( CONCAT(stoo.apiKeyID, ' ', stoo.macAddress, ' ', stoo.remark, ' ', stoo.serialNumber, ' ', stoo.projectDevice, ' ', stoo.storedAtCustomer)) LIKE :keyword )")
    List<StorageObject> searchStoo(String keyword); //TODO
}
