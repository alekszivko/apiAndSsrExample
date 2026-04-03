package at.spengergasse.sj2324seedproject.presentation.api;

import at.spengergasse.sj2324seedproject.presentation.api.dtos.CustomerDTO;
import at.spengergasse.sj2324seedproject.service.CustomerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path(CustomerRestController.BASE_URL)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerRestController {

    protected static final String BASE_URL = "/api/customers";

    @Inject
    CustomerService customerService;

    @GET
    public Response fetchCustomerData(@QueryParam("connectionNo") String connectionNo) {
        if (connectionNo == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return customerService.retrieveCustomerData(connectionNo)
            .map(dto -> Response.ok(dto).build())
            .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
