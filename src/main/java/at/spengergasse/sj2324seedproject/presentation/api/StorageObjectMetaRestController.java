package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.exceptions.StorageObjectMetaAlreadyExistsException;
import at.spengergasse.sj2324seedproject.presentation.api.commands.StorageObjectMetaCommand;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.StorageObjectMetaDTO;
import at.spengergasse.sj2324seedproject.service.StorageObjectMetaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Path(StorageObjectMetaRestController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StorageObjectMetaRestController {

    protected static final String BASE_URL = "/api/storageObjectMeta";

    @Inject
    StorageObjectMetaService storageObjectMetaService;

    @GET
    public List<StorageObjectMetaDTO> fetchStorageObjectMeta(@QueryParam("nameParam") String nameParam) {
        return storageObjectMetaService.fetchStoMeta(Optional.ofNullable(nameParam))
            .stream()
            .map(StorageObjectMetaDTO::new)
            .toList();
    }

    @GET
    @Path("/{name}")
    public Response fetchStorageObjectMetaByName(@PathParam("name") String name) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return storageObjectMetaService.findStorageObjectMeta(name)
            .map(StorageObjectMetaDTO::new)
            .map(dto -> Response.ok(dto)
                .header("Location", BASE_URL + "/" + dto.name())
                .build())
            .orElse(Response.noContent().build());
    }

    @POST
    public Response createStoMeta(@Valid StorageObjectMetaCommand cmdMeta) {
        var storageMeta = storageObjectMetaService.saveStorageMeta(
            cmdMeta.type(), cmdMeta.name(), cmdMeta.osVersion(),
            cmdMeta.consumablesPerBox(), cmdMeta.sfpType(),
            cmdMeta.waveLength(), cmdMeta.interfaceSpeed());
        URI uri = URI.create("%s/%s".formatted(BASE_URL, storageMeta.getId()));
        return Response.created(uri).entity(new StorageObjectMetaDTO(storageMeta)).build();
    }
}
