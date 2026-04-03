package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.StorageDTO;
import at.spengergasse.sj2324seedproject.service.StorageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path("/api/storage")
@Produces(MediaType.APPLICATION_JSON)
public class StorageRestController {

    @Inject
    StorageService storageService;

    @GET
    public List<StorageDTO> fetchStorage(@QueryParam("namePart") String namePart) {
        return storageService.fetchStorage(Optional.ofNullable(namePart))
            .stream()
            .map(StorageDTO::new)
            .toList();
    }
}
