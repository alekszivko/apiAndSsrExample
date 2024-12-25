package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.persistence.StorageObjectMetaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class StorageObjectMetaServiceTest {

    private StorageObjectMetaService storageObjectMetaService;
    private @Mock StorageObjectMetaRepository storageObjectMetaRepository;

    @BeforeEach
    void setup(){
        assumeThat(storageObjectMetaRepository).isNotNull();
        this.storageObjectMetaService = new StorageObjectMetaService(storageObjectMetaRepository);
    }

    @Test
    void ensureFetchStoMetaWorks() {
        var storageObjectMeta = FixtureFactory.storageObjectMetaFixture();
        when(storageObjectMetaRepository.findAll()).thenReturn(List.of(storageObjectMeta));

        var result = storageObjectMetaService.fetchStoMeta(Optional.empty());

        verify(storageObjectMetaRepository, times(1)).findAll();
    }

    @Test
    void ensureFetchStoMetaWithoutParamCallsFindAll() throws Exception{
        //given
        StorageObjectMeta storageObjectMeta = FixtureFactory.storageObjectMetaFixture();

        Optional<String> nameParam = Optional.empty();

        when(storageObjectMetaRepository.findAll()).thenReturn(List.of(storageObjectMeta));
        //when
        var result = storageObjectMetaService.fetchStoMeta(nameParam);
        //expect

        verify(storageObjectMetaRepository, times(1)).findAll();
    }

    @Test
    void ensureFetchStoMetaWithParamReturnsEqualStoMeta() {
       StorageObjectMeta equalStorageObjectMeta = FixtureFactory.storageObjectMetaFixture();
       Optional<String> nameParam = Optional.of("name");
       equalStorageObjectMeta.setName(nameParam.get());
       StorageObjectMeta unequalStorageObjectMeta = FixtureFactory.storageObjectMetaFixture();

       when(storageObjectMetaRepository.findAll())
           .thenReturn(List.of(equalStorageObjectMeta, unequalStorageObjectMeta));

       var result = storageObjectMetaService.fetchStoMeta(nameParam);

       assertThat(result).doesNotContain(unequalStorageObjectMeta);
       assertThat(result).contains(equalStorageObjectMeta);

    }

}