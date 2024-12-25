package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository
        extends JpaRepository<Producer, Long>{
    List<Producer> findProducerByName(Optional<String> namePart);

    List<Producer> findProducerByNameContainsIgnoreCase(Optional<String> name);

    List<Producer> findByName(Optional<String> nameParam);

    Producer findOneProducerByName(Optional<String> name);

    Producer findByShortname(String shortName);

    Producer deleteProducerByShortname(String shortName);

    Producer findProducerById(Long id);

//    Optional<Producer> findProducerById(String id);
    //    List<Producer> findAllByNameContainsIgnoreCase(String name);
}
