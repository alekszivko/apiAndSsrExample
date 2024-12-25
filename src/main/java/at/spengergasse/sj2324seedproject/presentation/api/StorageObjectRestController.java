package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.StorageObjectDTO;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static at.spengergasse.sj2324seedproject.constants.ConstantsDomain.URI_BASE_STORAGEOBJECT;
import static at.spengergasse.sj2324seedproject.constants.ConstantsDomain.URI_BASE_STORAGEOBJECT_MAC;

@RequiredArgsConstructor
@RestController
@RequestMapping(URI_BASE_STORAGEOBJECT)
public class StorageObjectRestController {

    //    @Autowired
    //    private final RepositoryStorageObject repositoryStorageObject;

    @Autowired
    private final StorageObjectService serviceStorageObject;

    @GetMapping
    public HttpEntity<List<StorageObjectDTO>> fetchStorageObjects(){
        return Optional.of(serviceStorageObject.fetchStorageObjectsList())
                       .filter(stoObject -> !stoObject.isEmpty())
                       .map(storageObject -> storageObject.stream()
                                                          .map(StorageObjectDTO::new)
                                                          .toList())
                       .map(storageObjectDTOS -> ResponseEntity.ok(storageObjectDTOS))
                       .orElse(ResponseEntity.noContent()
                                             .build());
    }

    @GetMapping(URI_BASE_STORAGEOBJECT_MAC)
    public ResponseEntity<StorageObjectDTO> fetchOneStorageObjectByMAC(
            @RequestParam
            Optional<String> macAddress){
        StorageObject storageObjectMac = serviceStorageObject.findStorageObjectMac(macAddress);
        return (storageObjectMac != null) ? ResponseEntity.ok()
                                                          .body(new StorageObjectDTO(storageObjectMac)) : ResponseEntity.noContent()
                                                                                                                        .build();

        //        return serviceStorageObject.findStorageObjectByMac(macAddress)
        //                                   .map(StorageObjectDTO::new)
        //                                   .map(ResponseEntity::ok)
        //                                   .orElseGet(() -> ResponseEntity.notFound()
        //                                                                  .build());
    }
}
