package at.spengergasse.sj2324seedproject.presentation.api.commands;

public record StorageObjectMetaCommand(String type,
                                       String name,
                                       String osVersion,
                                       String consumablesPerBox,
                                       String sfpType,
                                       String waveLength,
                                       String interfaceSpeed){
    /*public CommandStorageObjectMeta(StorageObjectMeta storageObjectMeta){
        this(storageObjectMeta.getType().getLongVersion(),
             storageObjectMeta.getName(),
             storageObjectMeta.getOsVersion(),
             storageObjectMeta.getConsumablesPerBox().toString(),
             storageObjectMeta.getSfpType().getLongVersion(),
             storageObjectMeta.getWavelength(),
             storageObjectMeta.getInterfacespeed());*/
    }

