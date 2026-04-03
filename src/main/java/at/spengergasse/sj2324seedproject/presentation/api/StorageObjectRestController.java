package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.StorageObjectDTO;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path(StorageObjectRestController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
public class StorageObjectRestController {

    protected static final String BASE_URL = "/api/storageObjects";

    @Inject
    StorageObjectService storageObjectService;

    @GET
    public List<StorageObjectDTO> fetchStorageObjects() {
        return storageObjectService.findAll()
            .stream()
            .map(StorageObjectDTO::new)
            .toList();
    }

    @GET
    @Path("/mac")
    public Response fetchOneStorageObjectByMAC(@QueryParam("mac") String mac) {
        return storageObjectService.findStorageObjectByMac(mac)
            .map(StorageObjectDTO::new)
            .map(dto -> Response.ok(dto).build())
            .orElse(Response.noContent().build());
    }
}
