package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.exceptions.ProducerException;
import at.spengergasse.sj2324seedproject.presentation.api.commands.ProducerCommand;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.ProducerDTO;
import at.spengergasse.sj2324seedproject.service.ProducerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path(ProducerRestController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducerRestController {

    protected static final String BASE_URL = "/api/producers";

    @Inject
    ProducerService producerService;

    @GET
    public List<ProducerDTO> fetchProducers(@QueryParam("nameParam") String nameParam) {
        log.debug("fetchProducers called with nameParam={}", nameParam);
        List<ProducerDTO> result = producerService.fetchProducer(Optional.ofNullable(nameParam))
            .stream()
            .map(ProducerDTO::new)
            .toList();
        log.debug("fetchProducers returned {} elements", result.size());
        return result;
    }

    @GET
    @Path("/{id}")
    public Response getProducer(@PathParam("id") Long id) {
        log.debug("getProducer called with id={}", id);
        try {
            var producer = producerService.findProducerByID(id);
            return Response.ok(new ProducerDTO(producer))
                .header("Location", BASE_URL + "/" + producer.getId())
                .build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    @POST
    public Response createProducer(ProducerCommand command) {
        log.debug("createProducer called with {}", command);
        var producer = producerService.saveProducer(command.shortname(), command.name());
        URI uri = URI.create(BASE_URL + "/" + producer.getId());
        return Response.created(uri)
            .entity(ProducerDTO.builder()
                .name(producer.getName())
                .shortname(producer.getShortname())
                .build())
            .build();
    }

    @DELETE
    @Path("/{delShortname}")
    public Response delete(@PathParam("delShortname") String delShortname) throws ProducerException {
        log.debug("delete called with shortName={}", delShortname);
        var producer = producerService.deleteProducerB(delShortname);
        return Response.ok(new ProducerDTO(producer)).build();
    }
}
