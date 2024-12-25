package at.spengergasse.sj2324seedproject.presentation.api;


import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StorageObjectRestController.class)
class StorageObjectRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private StorageObjectService storageObjectService;

  @BeforeEach
  void setup() {
    assumeThat(mockMvc).isNotNull();
    assumeThat(storageObjectService).isNotNull();
  }

  @Test
  void ensureFetchAllReturnsContentForExistingData() throws Exception {
    //given, when
    StorageObject storageObject = FixtureFactory.storageObjectFixture();

    when(storageObjectService.findAll()).thenReturn(
        List.of(storageObject));
    var request =
        get(StorageObjectRestController.BASE_URL).accept(
            MediaType.APPLICATION_JSON);
    //then, expect

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("[0].serialNumber").value(storageObject.getSerialNumber()))
        .andExpect(jsonPath("[0].macAddress").value(storageObject.getMacAddress()))
        .andExpect(jsonPath("[0].remark").value(storageObject.getRemark()))
        .andExpect(jsonPath("[0].projectDevice").value(storageObject.getProjectDevice()))
        .andExpect(jsonPath("[0].storedAtCustomer.connectionNo").value(
            storageObject.getStoredAtCustomer().connectionNo()))
        .andDo(print());


  }
}