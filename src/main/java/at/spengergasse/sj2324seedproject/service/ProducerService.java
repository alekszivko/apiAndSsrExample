package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.exceptions.ProducerException;
import at.spengergasse.sj2324seedproject.foundation.Guard;
import at.spengergasse.sj2324seedproject.persistence.ProducerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ProducerService {

    @Inject
    ProducerRepository producerRepository;

    public List<Producer> fetchProducer(Optional<String> nameParam) {
        if (nameParam.isPresent()) {
            List<Producer> prod2 = new ArrayList<>();
            List<Producer> producerList = producerRepository.listAll();
            Iterator<Producer> iter = producerList.iterator();

            while (iter.hasNext()) {
                Producer temp = iter.next();
                if (temp.getName().toUpperCase().contains(nameParam.get().toUpperCase())) {
                    prod2.add(temp);
                }
            }

            if (prod2.isEmpty()) {
                return producerRepository.listAll();
            }
            return prod2;
        } else {
            return producerRepository.listAll();
        }
    }

    public Producer saveProducer(String shortName, String name) {
        Producer producer = Producer.builder()
            .shortname(shortName)
            .name(name)
            .build();
        producerRepository.persist(producer);
        return producer;
    }

    public void deleteProducer(String shortName) throws ProducerException {
        if (shortName != null) {
            producerRepository.deleteByShortname(shortName);
        } else {
            throw new ProducerException("ShortName is null");
        }
    }

    public Producer deleteProducerB(String shortName) throws ProducerException {
        if (shortName != null) {
            Producer producer = producerRepository.findByShortname(shortName)
                .orElseThrow(() -> new NoSuchElementException("Producer not found: " + shortName));
            producerRepository.delete(producer);
            return producer;
        } else {
            throw new ProducerException("ShortName is null");
        }
    }

    public Producer findProducerByID(Long id) {
        if (Guard.isPositive(id)) {
            return producerRepository.findProducerById(id)
                .orElseThrow(() -> new NoSuchElementException("Producer not found with id: " + id));
        } else {
            throw new NoSuchElementException("Producer id is negative; therefore, no value is available!");
        }
    }
}
