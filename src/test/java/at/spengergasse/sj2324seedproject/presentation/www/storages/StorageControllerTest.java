package at.spengergasse.sj2324seedproject.presentation.www.storages;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.StorageService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StorageController.class)
class StorageControllerTest {

  private @Autowired MockMvc mockMvc;
  private @MockitoBean StorageService storageService;

  @Test
  void ensureGetStorageReturnsProperView() throws Exception {
    List<Storage> storages = List.of(FixtureFactory.storageFixture(),
        FixtureFactory.storageFixture());

    when(storageService.fetchStorage(Optional.empty())).thenReturn(storages);

    // expect

    mockMvc.perform(get(StorageController.BASE_URL))
        .andExpect(status().isOk())
        .andExpect(model().attribute("storages", storages))
        .andExpect(view().name("storages/list"))
        .andDo(print());
  }
}