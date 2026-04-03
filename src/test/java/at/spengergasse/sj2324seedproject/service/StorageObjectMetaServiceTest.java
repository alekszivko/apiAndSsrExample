package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.persistence.StorageObjectMetaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageObjectMetaServiceTest {

    @InjectMocks
    private StorageObjectMetaService storageObjectMetaService;

    @Mock
    private StorageObjectMetaRepository storageObjectMetaRepository;

    @Test
    void ensureFetchStoMetaWorks() {
        var storageObjectMeta = FixtureFactory.storageObjectMetaFixture();
        when(storageObjectMetaRepository.listAll()).thenReturn(List.of(storageObjectMeta));

        var result = storageObjectMetaService.fetchStoMeta(Optional.empty());

        verify(storageObjectMetaRepository, times(1)).listAll();
    }

    @Test
    void ensureFetchStoMetaWithoutParamCallsFindAll() throws Exception {
        //given
        StorageObjectMeta storageObjectMeta = FixtureFactory.storageObjectMetaFixture();
        Optional<String> nameParam = Optional.empty();
        when(storageObjectMetaRepository.listAll()).thenReturn(List.of(storageObjectMeta));

        //when
        var result = storageObjectMetaService.fetchStoMeta(nameParam);

        //expect
        verify(storageObjectMetaRepository, times(1)).listAll();
    }

    @Test
    void ensureFetchStoMetaWithParamReturnsEqualStoMeta() {
        StorageObjectMeta equalStorageObjectMeta = FixtureFactory.storageObjectMetaFixture();
        Optional<String> nameParam = Optional.of("name");
        equalStorageObjectMeta.setName(nameParam.get());
        StorageObjectMeta unequalStorageObjectMeta = FixtureFactory.storageObjectMetaFixture();

        when(storageObjectMetaRepository.listAll())
            .thenReturn(List.of(equalStorageObjectMeta, unequalStorageObjectMeta));

        var result = storageObjectMetaService.fetchStoMeta(nameParam);

        assertThat(result).doesNotContain(unequalStorageObjectMeta);
        assertThat(result).contains(equalStorageObjectMeta);
    }
}
