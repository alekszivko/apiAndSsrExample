package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.exceptions.ProducerException;
import at.spengergasse.sj2324seedproject.presentation.api.commands.ProducerCommand;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.ProducerDTO;
import at.spengergasse.sj2324seedproject.service.ProducerService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Log4j2

@RestController
@RequestMapping(ProducerRestController.BASE_URL)
public class ProducerRestController {

  protected static final String BASE_URL = "/api/producers";

  private final ProducerService producerService;

  @GetMapping()
  public List<ProducerDTO> fetchProducers(
      @RequestParam
      Optional<String> nameParam) {
    List<ProducerDTO> result = new ArrayList<>();
    log.debug("fetchProducers called with nameParam={}",
        nameParam);
    List<Producer> persProducer = producerService.fetchProducer(nameParam);
    for (Producer pro : persProducer) {
      ProducerDTO producerDTO = new ProducerDTO(pro);
      result.add(producerDTO);
    }
    log.debug("fetchProducers returned {} elements",
        result.size());
    return result;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProducerDTO> getProducer(
      @PathVariable
      String id) {

    log.debug("getProducer called with id={}",
        id);

    Long tempID = -1L;
    if (id.contains("01234567890")) {
      tempID = Long.parseLong(id);
    }
    Producer producerByID = producerService.findProducerByID(tempID);
    URI location = URI.create(BASE_URL + producerByID.getId());

    HttpHeaders responseHeader = new HttpHeaders();
    responseHeader.setLocation(location);
    responseHeader.set("Test Header 1 ACCEPT",
        HttpHeaders.ACCEPT);

    if (producerByID == null) {
      responseHeader.set("Test Header 2 IF NONE MATCH",
          HttpHeaders.IF_NONE_MATCH);
      ResponseEntity<ProducerDTO> entity = new ResponseEntity<>(responseHeader,
          HttpStatus.NO_CONTENT);
      return entity;
    }

    ProducerDTO producerDTO = new ProducerDTO(producerByID);

    ResponseEntity<ProducerDTO> entity = new ResponseEntity<>(producerDTO,
        responseHeader,
        HttpStatus.OK);

    return entity;
  }

  @PostMapping
  public ResponseEntity<ProducerDTO> createProducer(
      @RequestBody
      ProducerCommand command) {
    log.debug("createProducer called with {}",
        command);
    var producer = producerService.saveProducer(command.shortname(),
        command.name());

    URI uri = URI.create(BASE_URL + producer.getId());
    return ResponseEntity.created(uri)
        .body(ProducerDTO.builder()
            .name(producer.getName())
            .shortname(producer.getShortname())
            .build());
  }

  @DeleteMapping("/{delShortname}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<ProducerDTO> delete(
      @PathVariable
      String delShortname) throws ProducerException {
    log.debug("delete called with shortName= {}",
        delShortname);

    var producer = producerService.deleteProducerB(delShortname);
    ResponseEntity<ProducerDTO> prod = ResponseEntity.ok()
        .body(new ProducerDTO(producer));
    return prod;
  }//TODO T011051

}
