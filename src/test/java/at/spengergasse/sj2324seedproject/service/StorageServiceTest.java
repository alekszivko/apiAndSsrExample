package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.persistence.StorageRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;

//Es werden keine Container benötigt, es wird mit Mock Objekten gearbeitet. --> schnell
@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @InjectMocks
    private StorageService storageService;

    @Mock
    private StorageRepository storageRepository;

    @Test
    void ensureFetchStorageWithNoArgumentCallFindAll() {
        //given
        Optional<String> searchCriteria = Optional.empty();
        var storage = FixtureFactory.storageFixture();
        when(storageRepository.listAll()).thenReturn(List.of(storage));

        //when
        var result = storageService.fetchStorage(searchCriteria);

        //then
        assumeThat(result).containsExactly(storage);
        verify(storageRepository).listAll();
        verifyNoMoreInteractions(storageRepository);
    }

    @Test
    void ensureFetchStorageWithValidArgumentCallFindAllByNameContainingIgnoreCase() {
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
