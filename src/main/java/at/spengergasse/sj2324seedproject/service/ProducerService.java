package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.exceptions.ProducerException;
import at.spengergasse.sj2324seedproject.foundation.Guard;
import at.spengergasse.sj2324seedproject.persistence.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;


    public List<Producer> fetchProducer(Optional<String> nameParam){
        return nameParam.map(name -> {
            List<Producer> all = producerRepository.findAll();
            List<Producer> filtered = all.stream()
                .filter(p -> p.getName().toUpperCase().contains(name.toUpperCase()))
                .toList();
            return filtered.isEmpty() ? all : filtered;
        }).orElseGet(producerRepository::findAll);
    }

    public List<Producer> fetchProducerName(Optional<String> namePart){
        return producerRepository.findProducerByName(namePart);
    }

    public Producer saveProducer(String shortName,
                                 String name){
        Producer producer = Producer.builder()
                                    .shortname(shortName)
                                    .name(name)
                                    .build();

        return producerRepository.save(producer);

    }

    public void deleteProducer(String shortName) throws ProducerException {
        if(shortName != null){
            producerRepository.deleteProducerByShortname(shortName);
        }else{
            throw new ProducerException("ShortName is null");
        }
    }

    public Producer deleteProducerB(String shortName) throws ProducerException {
        if(shortName != null){
            return producerRepository.deleteProducerByShortname(shortName);
        }else{
            throw new ProducerException("ShortName is null");
        }
    }

    public Producer findProducerByID(Long id){
        if(Guard.isPositive(id)){
            return producerRepository.findProducerById(id);
        }else{
            throw new NoSuchElementException("Producer id is negativ; therefore, no value is available!");
        }
    }
}
