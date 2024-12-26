package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StorageObjectMetaRepository extends JpaRepository<StorageObjectMeta, Long>{

    StorageObjectMeta findByNameContainsIgnoreCase(String name);
}
