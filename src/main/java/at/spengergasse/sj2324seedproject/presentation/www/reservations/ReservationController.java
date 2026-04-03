package at.spengergasse.sj2324seedproject.presentation.www.reservations;

import at.spengergasse.sj2324seedproject.presentation.api.reservations.ReservationDTO;
import at.spengergasse.sj2324seedproject.service.ReservationService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Path(ReservationController.BASE_URL)
public class ReservationController {

    protected static final String BASE_URL = "/reservations";

    @Inject
    ReservationService reservationService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance list(List<ReservationDTO> reservations);
        public static native TemplateInstance newReservation(CreateReservationForm form);
        public static native TemplateInstance edit(EditReservationForm form);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getReservations() {
        List<ReservationDTO> reservations = reservationService
            .fetchReservations(Optional.empty())
            .stream()
            .map(ReservationDTO::new)
            .toList();
        return Templates.list(reservations);
    }

    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response editReservation(@PathParam("id") String id) {
        return reservationService.getReservationByReservationID(id)
            .map(EditReservationForm::create)
            .map(form -> Response.ok(Templates.edit(form)).build())
            .orElse(Response.seeOther(URI.create(BASE_URL)).build());
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteReservation(@PathParam("id") String id) {
        reservationService.removeReservation(id);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showNewReservationForm() {
        return Templates.newReservation(CreateReservationForm.create());
    }

    @POST
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleEditReservationFormSubmission(
        @PathParam("id") String id,
        @FormParam("description") String description,
        @FormParam("connectionNo") String connectionNo,
        @FormParam("completed") boolean completed) {

        reservationService.updateReservation(id, description, connectionNo, completed);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleNewReservationFormSubmission(
        @FormParam("connectionNo") String connectionNo,
        @FormParam("reservationDescription") String reservationDescription) {

        reservationService.createReservation(reservationDescription, connectionNo);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }
}
