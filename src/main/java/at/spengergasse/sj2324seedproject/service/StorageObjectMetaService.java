package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.SfpType;
import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.domain.Type;
import at.spengergasse.sj2324seedproject.persistence.StorageObjectMetaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@RequiredArgsConstructor
public class StorageObjectMetaService {

    private StorageObjectMetaRepository storageObjectMetaRepository;


    public StorageObjectMeta saveStorageMeta(StorageObjectMeta storageObjectMeta){

        return storageObjectMetaRepository.save(storageObjectMeta);
    }

    public List<StorageObjectMeta> fetchStoMeta(Optional<String> nameParam){
        List<StorageObjectMeta> storageObjectMetas = storageObjectMetaRepository.findAll();

        return nameParam.map(param -> storageObjectMetas.stream()
            .filter(stoMeta ->
                stoMeta.getName().equalsIgnoreCase(nameParam.get())).toList())
            .orElseGet(() -> storageObjectMetas);
    }

    public StorageObjectMeta findStorageObjectMeta(String name){
        return storageObjectMetaRepository.findByNameContainsIgnoreCase(name);
    }

    @Transactional
    public StorageObjectMeta saveStorageMeta(String type,
                                             String name,
                                             String osVersion,
                                             String consumablesPerBox,
                                             String sfpType,
                                             String waveLength,
                                             String interfaceSpeed){

        StorageObjectMeta storageObjectMeta = StorageObjectMeta.builder()
                                                               .type(Type.valueOf(type))
                                                               .name(name)
                                                               .osVersion(osVersion)
                                                               .consumablesPerBox(Integer.parseInt(consumablesPerBox))
                                                               .sfpType(SfpType.valueOf(sfpType))
                                                               .wavelength(waveLength)
                                                               .interfacespeed(interfaceSpeed)
                                                               .build();

        return storageObjectMetaRepository.save(storageObjectMeta);
    }
}
