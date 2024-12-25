package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.constants.ConstantsDomain;
import at.spengergasse.sj2324seedproject.domain.SfpType;
import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.domain.Type;
import at.spengergasse.sj2324seedproject.service.StorageObjectMetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(StorageObjectMetaRestController.class)
class StorageObjectRestControllerMetaTest {

    @Autowired
    private MockMvc                  mockMvc;
    @Autowired
    private ObjectMapper             objectMapper;
    @MockitoBean
    private StorageObjectMetaService storageObjectMetaService;


    @BeforeEach
    void init(){
        assumeThat(mockMvc).isNotNull();
        assumeThat(objectMapper).isNotNull();
    }

    @Test
    void ensure_test_fetching_meta(){
        String exp = "eta n";
        StorageObjectMeta storageObjectMeta = StorageObjectMeta.builder()
                                                               .name("meta name1")
                                                               .type(Type.IP_PHONE)
                                                               .osVersion("version1")
                                                               .consumablesPerBox(2)
                                                               .sfpType(SfpType.MM)
                                                               .wavelength("1550nm")
                                                               .interfacespeed("100-Mbps")
                                                               .build();
        when(storageObjectMetaService.findStorageObjectMeta(exp)).thenReturn(storageObjectMeta);
        var request = get(ConstantsDomain.URL_BASE_STO_META+ConstantsDomain.URL_BASE_STO_META_NAME)
                              .accept(MediaType.APPLICATION_JSON);
    }

/*    @Test
    void ensureMetaReturnsProperProblemDetailInABadRequestResponse() throws Exception{
        //given
        var name = "meta11";
        when(serviceStorageObjectMeta.saveStorageMeta(any(),
                                                      eq(name),
                                                      any(),
                                                      any(),
                                                      any(),
                                                      any(),
                                                      any())).thenThrow(new StorageObjectMetaAlreadyExistsException("!!!!!!!!!!!!!"));
        CommandStorageObjectMeta cmdMeta = new CommandStorageObjectMeta("Router",
                                                                        "meta11",
                                                                        "bestAndLatest",
                                                                        "22",
                                                                        "sm",
                                                                        "255nm",
                                                                        "8 Knoten");
        //when //then
        var request = post(ConstantsDomain.URL_BASE_STO_META).accept(MediaType.APPLICATION_JSON)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(objectMapper.writeValueAsString(cmdMeta));

        mockMvc.perform(request)
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
               .andExpect(jsonPath("$.title").value("MetaData"))
               //               .andExpect(jsonPath("$.type").value(HttpStatus.CONFLICT.value()))
               .andExpect(jsonPath("$.name").value(HttpStatus.CONFLICT.value()))
               //               .andExpect(jsonPath("$.osVersion").value(HttpStatus.CONFLICT.value()))
               //               .andExpect(jsonPath("$.consumablesPerBox").value(HttpStatus.CONFLICT.value()))
               //               .andExpect(jsonPath("$.sfpType").value(HttpStatus.CONFLICT.value()))
               //               .andExpect(jsonPath("$.waveLength").value(HttpStatus.CONFLICT.value()))
               //               .andExpect(jsonPath("$.interfaceSpeed").value(HttpStatus.CONFLICT.value()))
               .andDo(print());
    }*/

  /*  @Test
    void ensureMetaReturnsINTERNAL_SERVER_ERROR() throws Exception{
        //given
        var name = "meta11";
        when(serviceStorageObjectMeta.saveStorageMeta(any(),
                                                      any(),
                                                      any(),
                                                      any(),
                                                      any(),
                                                      any(),
                                                      any())).thenThrow(new StorageObjectMetaAlreadyExistsException("!!!!!!!!!!!!"));
        CommandStorageObjectMeta cmdMeta = new CommandStorageObjectMeta("Router",
                                                                        "meta11",
                                                                        "bestAndLatest",
                                                                        "22",
                                                                        "sm",
                                                                        "255nm",
                                                                        "8 Knoten");
        //when //then
        var request = post(ConstantsDomain.URL_BASE_STO_META).accept(MediaType.APPLICATION_JSON)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(objectMapper.writeValueAsString(cmdMeta));

        mockMvc.perform(request)
               .andExpect(status().isInternalServerError())
               .andExpect(jsonPath("$.type").value("Persistence Error"))
               .andExpect(jsonPath("$.name").value("Persistence Error"))
               .andExpect(jsonPath("$.osVersion").value("Persistence Error"))
               .andExpect(jsonPath("$.consumablesPerBox").value("Persistence Error"))
               .andExpect(jsonPath("$.sfpType").value("Persistence Error"))
               .andExpect(jsonPath("$.waveLength").value("Persistence Error"))
               .andExpect(jsonPath("$.interfaceSpeed").value("Persistence Error"))
               .andDo(print());
    }*/


}