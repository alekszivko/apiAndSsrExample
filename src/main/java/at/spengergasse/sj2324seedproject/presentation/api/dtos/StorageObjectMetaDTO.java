package at.spengergasse.sj2324seedproject.presentation.api.dtos;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;

public record StorageObjectMetaDTO(String name,
                                   String type,
                                   String sfpType,
                                   String osVersion,
                                   Integer consumablesPerBox,
                                   String waveLength,
                                   String interfaceSpeed,
                                   ProducerDTO producerDTO){
    public StorageObjectMetaDTO(StorageObjectMeta storageObjectMeta){
        this(storageObjectMeta.getName(),
             storageObjectMeta.getType()
                              .getLongVersion(),
             storageObjectMeta.getSfpType()
                              .getLongVersion(),
             storageObjectMeta.getOsVersion(),
             storageObjectMeta.getConsumablesPerBox(),
             storageObjectMeta.getWavelength(),
             storageObjectMeta.getInterfacespeed(),
             storageObjectMeta.getProducer() != null ? new ProducerDTO(storageObjectMeta.getProducer()) : null);
    }


}
