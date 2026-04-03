package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StorageObjectRepository implements PanacheRepository<StorageObject> {

    public Optional<StorageObject> findByMacAddress(String mac) {
        return find("macAddress", mac).firstResultOptional();
    }

    public Optional<StorageObject> findByApiKeyID(String key) {
        return find("apiKeyID", key).firstResultOptional();
    }

    public void deleteByApiKeyID(String key) {
        delete("apiKeyID", key);
    }

    public List<StorageObject> searchStorageObjects(String keyword) {
        return list(
            "SELECT stoo FROM StorageObject stoo "
                + "LEFT JOIN Storage sto ON stoo.storedStorage.id = sto.id "
                + "WHERE sto IS NOT NULL "
                + "AND ( LOWER( CONCAT(stoo.apiKeyID, ' ', stoo.macAddress, ' ', stoo.remark, ' ', stoo.serialNumber, ' ', stoo.projectDevice, ' ', stoo.storedAtCustomer)) LIKE :keyword )",
            io.quarkus.panache.common.Parameters.with("keyword", keyword));
    }
}
