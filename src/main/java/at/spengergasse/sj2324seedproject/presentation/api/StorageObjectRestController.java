package at.spengergasse.sj2324seedproject.presentation.api;

import static at.spengergasse.sj2324seedproject.presentation.api.StorageObjectRestController.BASE_URL;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.StorageObjectDTO;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL)
public class StorageObjectRestController {

  protected static final String BASE_URL = "/api/storageObjects";


  private final StorageObjectService storageObjectService;

  @GetMapping
  public HttpEntity<List<StorageObjectDTO>> fetchStorageObjects() {
    return ResponseEntity.ok(storageObjectService.findAll()
        .stream()
        .map(StorageObjectDTO::new)
        .toList());

  }

  @GetMapping({"/mac"})
  public ResponseEntity<StorageObjectDTO> fetchOneStorageObjectByMAC(String mac) {

    return storageObjectService.findStorageObjectByMac(mac).map(StorageObjectDTO::new)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent()
            .build());
  }
}
