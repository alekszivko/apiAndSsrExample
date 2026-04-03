package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.Storage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StorageRepository implements PanacheRepository<Storage> {

    public List<Storage> findAllByNameContainingIgnoreCase(String name) {
        return list("LOWER(name) LIKE LOWER(?1)", "%" + name + "%");
    }
}
