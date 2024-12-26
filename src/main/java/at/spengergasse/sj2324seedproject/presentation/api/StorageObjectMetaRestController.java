package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.exceptions.StorageObjectMetaAlreadyExistsException;
import at.spengergasse.sj2324seedproject.presentation.api.commands.StorageObjectMetaCommand;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.StorageObjectMetaDTO;
import at.spengergasse.sj2324seedproject.service.StorageObjectMetaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(StorageObjectMetaRestController.BASE_URL)
public class StorageObjectMetaRestController {

  protected static final String BASE_URL = "/api/storageObjectMeta";


  private final StorageObjectMetaService storageObjectMetaService;


  @GetMapping
  public HttpEntity<List<StorageObjectMetaDTO>> fetchStorageObjectMeta(
      @RequestParam
      Optional<String> nameParam) {

    return ResponseEntity.ok(storageObjectMetaService
        .fetchStoMeta(nameParam).stream()
        .map(StorageObjectMetaDTO::new)
        .toList());
  }

  @GetMapping({"/{name}"})
  public ResponseEntity<StorageObjectMetaDTO> fetchStorageObjectMeta(String name) {
    if (name == null) {
      throw new NullPointerException("Given String is null!!!");
    }

    StorageObjectMeta storageObjectMeta = storageObjectMetaService.findStorageObjectMeta(name);

    if (storageObjectMeta == null) {
      return ResponseEntity.noContent().build();
    }

    StorageObjectMetaDTO storageObjectMetaDTO = new StorageObjectMetaDTO(storageObjectMeta);

    return ResponseEntity
        .status(HttpStatus.OK)
        .location(URI.create(
            BASE_URL + storageObjectMetaDTO.name()))
        .body(storageObjectMetaDTO);
  }

  @PostMapping
  public ResponseEntity<StorageObjectMetaDTO> createStoMeta(
      @RequestBody
      @Valid StorageObjectMetaCommand cmdMeta) {
    StorageObjectMeta storageMeta = storageObjectMetaService.saveStorageMeta(cmdMeta.type(),
        cmdMeta.name(),
        cmdMeta.osVersion(),
        cmdMeta.consumablesPerBox(),
        cmdMeta.sfpType(),
        cmdMeta.waveLength(),
        cmdMeta.interfaceSpeed());

    URI uri = URI.create("%s, %s".formatted(BASE_URL, storageMeta.getId()));

    return ResponseEntity.created(uri)
        .body(new StorageObjectMetaDTO(storageMeta));
  }

  @ExceptionHandler(StorageObjectMetaAlreadyExistsException.class)
  public HttpEntity<ProblemDetail> StoMetaAlreadyExistsException(
      StorageObjectMetaAlreadyExistsException meta) {

    HttpStatus status = HttpStatus.CONFLICT;
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status,
        meta.getMessage());
    problemDetail.setTitle("MetaData");
    problemDetail.setProperty("StorageObjectMeta",
        meta.getStackTrace());
    return ResponseEntity.status(status)
        .body(problemDetail);
  }
}
