package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.SfpType;
import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.domain.Type;
import at.spengergasse.sj2324seedproject.persistence.StorageObjectMetaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class StorageObjectMetaService {

    @Inject
    StorageObjectMetaRepository storageObjectMetaRepository;

    public StorageObjectMeta saveStorageMeta(StorageObjectMeta storageObjectMeta) {
        storageObjectMetaRepository.persist(storageObjectMeta);
        return storageObjectMeta;
    }

    public List<StorageObjectMeta> fetchStoMeta(Optional<String> nameParam) {
        List<StorageObjectMeta> all = storageObjectMetaRepository.listAll();
        return nameParam.map(param -> all.stream()
                .filter(stoMeta -> stoMeta.getName().equalsIgnoreCase(param))
                .toList())
            .orElse(all);
    }

    public Optional<StorageObjectMeta> findStorageObjectMeta(String name) {
        return storageObjectMetaRepository.findByNameContainsIgnoreCase(name);
    }

    public StorageObjectMeta saveStorageMeta(String type, String name, String osVersion,
        String consumablesPerBox, String sfpType, String waveLength, String interfaceSpeed) {
        StorageObjectMeta storageObjectMeta = StorageObjectMeta.builder()
            .type(Type.valueOf(type))
            .name(name)
            .osVersion(osVersion)
            .consumablesPerBox(Integer.parseInt(consumablesPerBox))
            .sfpType(SfpType.valueOf(sfpType))
            .wavelength(waveLength)
            .interfacespeed(interfaceSpeed)
            .build();
        storageObjectMetaRepository.persist(storageObjectMeta);
        return storageObjectMeta;
    }
}
