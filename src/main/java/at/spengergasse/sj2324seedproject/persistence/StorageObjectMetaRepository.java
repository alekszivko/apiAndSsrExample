package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class StorageObjectMetaRepository implements PanacheRepository<StorageObjectMeta> {

    public Optional<StorageObjectMeta> findByNameContainsIgnoreCase(String name) {
        return find("LOWER(name) LIKE LOWER(?1)", "%" + name + "%").firstResultOptional();
    }
}
