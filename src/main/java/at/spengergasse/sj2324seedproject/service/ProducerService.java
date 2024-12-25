package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.exceptions.ProducerException;
import at.spengergasse.sj2324seedproject.foundation.Guard;
import at.spengergasse.sj2324seedproject.persistence.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProducerService {

    @Autowired
    private final ProducerRepository producerRepository;


    public List<Producer> fetchProducer(Optional<String> nameParam){
        List<Producer> prod2 = new ArrayList<>();

        if(nameParam.isPresent()){

            List<Producer>     producerList = producerRepository.findAll();
            Iterator<Producer> iter         = producerList.iterator();

            Producer probe = Producer.builder().name(nameParam.get()).build();

            Example<Producer> proTemp = Example.of(probe);



            producerRepository.exists(proTemp);
            while(iter.hasNext()){
                Producer temp = iter.next();
                String toUpperCase1 = temp.getName()
                                          .toUpperCase();
                String toUpperCase2 = nameParam.get()
                                               .toUpperCase();
                if(toUpperCase1.contains(toUpperCase2)){
                    prod2.add(temp);
                }
            }

            if(nameParam.isPresent() && prod2.isEmpty()){
                return producerRepository.findAll();
            }
        }else{
            return producerRepository.findAll();
        }
        return prod2;
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

//    public Producer findProducerByStringID(String id){
//        return repositoryProducer.findProducerById(Long.valueOf(id));
//    }
}
