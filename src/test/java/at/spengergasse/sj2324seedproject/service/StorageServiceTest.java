package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.persistence.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Es werden keine Container benötigt, es wird mit Mock Objekten gearbeitet. --> schnell
@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    private StorageService storageService;

    private @Mock StorageRepository storageRepository;

    @BeforeEach
    void setup(){
        assumeThat(storageRepository).isNotNull();
        storageService = new StorageService(storageRepository);
    }

    @Test
    void ensureFetchStorageWithNoArgumentCallFindAll(){

        //given
        Optional<String> searchCriteria = Optional.empty();
        var storage = FixtureFactory.storageFixture();
        when(storageRepository.findAll()).thenReturn(List.of(storage));

        //when
        var result = storageService.fetchStorage(searchCriteria);

        //then
        assumeThat(result).containsExactly(storage);
        verify(storageRepository).findAll();
        verifyNoMoreInteractions(storageRepository);

    }

    @Test
    void ensureFetchStorageWithValidArgumentCallFindAllByNameContainingIgnoreCase(){

        //given
        Optional<String> searchCriteria = Optional.of("Hauptlalala");
        var storage = FixtureFactory.storageFixture();
        when(storageRepository.findAllByNameContainingIgnoreCase(any())).thenReturn(List.of(storage));

        //when
        var result = storageService.fetchStorage(searchCriteria);

        //then
        assumeThat(result).containsExactly(storage);
        verify(storageRepository).findAllByNameContainingIgnoreCase(any());
        verifyNoMoreInteractions(storageRepository);

    }


}