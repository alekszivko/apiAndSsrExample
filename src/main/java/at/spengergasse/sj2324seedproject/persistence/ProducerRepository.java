package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.Producer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProducerRepository implements PanacheRepository<Producer> {

    public List<Producer> findByNameContainingIgnoreCase(String name) {
        return list("LOWER(name) LIKE LOWER(?1)", "%" + name + "%");
    }

    public Optional<Producer> findByShortname(String shortName) {
        return find("shortname", shortName).firstResultOptional();
    }

    public Optional<Producer> findProducerById(Long id) {
        return findByIdOptional(id);
    }

    @Transactional
    public void deleteByShortname(String shortName) {
        delete("shortname", shortName);
    }
}
