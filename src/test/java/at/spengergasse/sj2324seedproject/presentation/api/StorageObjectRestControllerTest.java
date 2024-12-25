package at.spengergasse.sj2324seedproject.presentation.api;


import at.spengergasse.sj2324seedproject.constants.ConstantsDomain;
import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StorageObjectRestController.class)
class StorageObjectRestControllerTest {

    @Autowired
    private MockMvc              mockMvc;
    @MockitoBean
    private StorageObjectService serviceStorageObject;

    @BeforeEach
    void setup(){
        assumeThat(mockMvc).isNotNull();
        assumeThat(serviceStorageObject).isNotNull();
    }

    @Test
    void ensureFetchAllReturnsContentForExistingData() throws Exception{
        //given, when
        StorageObject storageObject = FixtureFactory.storageObjectFixture();

        when(serviceStorageObject.findStorageObjectMac(Optional.empty())).thenReturn(storageObject);
        var request = get(ConstantsDomain.URI_BASE_STORAGEOBJECT+ConstantsDomain.URI_BASE_STORAGEOBJECT_MAC, storageObject.getMacAddress()).accept(MediaType.APPLICATION_JSON);

        //then, expect


        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.serialNumber").value(storageObject.getSerialNumber()))
               .andExpect(jsonPath("$.macAddress").value(storageObject.getMacAddress()))
               .andExpect(jsonPath("$.remark").value(storageObject.getRemark()))
               .andExpect(jsonPath("$.projectDevice").value(storageObject.getProjectDevice()))
               .andExpect(jsonPath("$.storedAtCustomer.connectionNo").value(storageObject.getStoredAtCustomer().connectionNo()))
               .andDo(print());


    }
}