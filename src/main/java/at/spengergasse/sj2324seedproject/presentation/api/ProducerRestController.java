package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.constants.ConstantsDomain;
import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.exceptions.ProducerException;
import at.spengergasse.sj2324seedproject.presentation.api.commands.ProducerCommand;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.ProducerDTO;
import at.spengergasse.sj2324seedproject.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(ConstantsDomain.URL_BASE_PRODUCER)
@Log4j2
@ComponentScan(basePackages = {"at.spengergasse.sj2324seedproject.persistence"})
public class ProducerRestController {

    @Autowired
    private final ProducerService producerService;

    @GetMapping()
    public List<ProducerDTO> fetchProducers(
            @RequestParam
            Optional<String> nameParam){
        List<ProducerDTO> result = new ArrayList<>();
        log.debug("fetchProducers called with nameParam={}",
                  nameParam);
        List<Producer> persProducer = producerService.fetchProducer(nameParam);
        for(Producer pro: persProducer){
            ProducerDTO producerDTO = new ProducerDTO(pro);
            result.add(producerDTO);
        }
        log.debug("fetchProducers returned {} elements",
                  result.size());
        return result;
        //        return  persistenceProducer.findAll()
        //                        .stream()
        //                        .map(ProducerDTO::new)
        //                        .toList();
    }

    @GetMapping(ConstantsDomain.URL_BASE_PRODUCER+ConstantsDomain.URI_ID)
    public ResponseEntity<ProducerDTO> getProducerById(String idVal){
        //        if(/*idVal != null && */Character.isDigit(Integer.parseInt(idVal))){
        Long     tempId       = Long.parseLong(idVal);
        Producer producerByID = producerService.findProducerByID(tempId);
        //            if(producerByID != null ){
        return ResponseEntity.ok(new ProducerDTO(producerByID));
        //            return ResponseEntity.ok(new ProducerDTO(producerByID));
        //            }
        //        }
        //        return ResponseEntity.status(888).build();
    }
    //    @GetMapping()
    //    public List<ProducerDTO> fetchProducersName(@RequestParam Optional<String> namePart){
    //
    //        List<ProducerDTO> result       = new ArrayList<>();
    //        List<Producer>    persProducer = serviceProducer.fetchProducerName(namePart);
    //        for(Producer pro : persProducer){
    //            ProducerDTO producerDTO = new ProducerDTO(pro);
    //            result.add(producerDTO);
    //        }
    //        return result;
    //    }

    @GetMapping(ConstantsDomain.URI_ID)
    public ResponseEntity<ProducerDTO> getProducer(
            @PathVariable
            String id){

        //        if(id == null)
        //            throw new ExceptionProducer("Given ID is null!");

        log.debug("getProducer called with id={}",
                  id);

        Long tempID = -1L;
        if(id.contains("01234567890")){
            tempID = Long.parseLong(id);
        }
        Producer producerByID = producerService.findProducerByID(tempID);
        URI      location     = URI.create(ConstantsDomain.URL_BASE_PRODUCER+ConstantsDomain.URI_ID);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(location);
        responseHeader.set("Test Header 1 ACCEPT",
                           HttpHeaders.ACCEPT);

        if(producerByID == null){
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

        //        if(isNumeric(idTemp)){
        //            var                         prod   = serviceProducer.findProdducerByID(idLong); //2559
        //            ResponseEntity<ProducerDTO> result = ResponseEntity.ok(new ProducerDTO(prod));
        //            return result;
        //        }else{
        //            throw new ExceptionProducer("ID is not numeric");
        //        }
    }

    @PostMapping
    public ResponseEntity<ProducerDTO> createProducer(
            @RequestBody
            ProducerCommand command){
        log.debug("createProducer called with {}",
                  command);
        var producer = producerService.saveProducer(command.shortname(),
                                                    command.name());

        URI uri = URI.create(ConstantsDomain.URL_BASE_PRODUCER+producer.getId());
        return ResponseEntity.created(uri)
                             .body(ProducerDTO.builder()
                                              .name(producer.getName())
                                              .shortname(producer.getShortname())
                                              .build());
    }

    @DeleteMapping(ConstantsDomain.URI_SHORTNAME)
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
