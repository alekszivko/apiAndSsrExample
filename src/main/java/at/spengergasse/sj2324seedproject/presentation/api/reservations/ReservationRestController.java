package at.spengergasse.sj2324seedproject.presentation.api.reservations;

import at.spengergasse.sj2324seedproject.service.ReservationService;
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
@Path(ReservationRestController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationRestController {

    protected static final String BASE_URL = "/api/reservations";

    @Inject
    ReservationService reservationService;

    @GET
    public Response fetchReservations(@QueryParam("completed") Boolean completed) {
        List<ReservationDTO> reservations = reservationService
            .fetchReservations(Optional.ofNullable(completed))
            .stream()
            .map(ReservationDTO::new)
            .toList();

        return reservations.isEmpty()
            ? Response.noContent().build()
            : Response.ok(reservations).build();
    }

    @POST
    public Response createReservation(@Valid CreateReservationCommand createReservationCmd) {
        var reservation = reservationService.createReservation(
            createReservationCmd.reservationDescription(),
            createReservationCmd.customerConnectionNo());

        URI location = URI.create("%s/%s".formatted(BASE_URL, reservation.getReservationId()));
        return Response.created(location).entity(new ReservationDTO(reservation)).build();
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") String id) {
        return reservationService.getReservationByReservationID(id)
            .map(ReservationDTO::new)
            .map(dto -> Response.ok(dto).build())
            .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
