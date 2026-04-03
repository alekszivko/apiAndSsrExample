package at.spengergasse.sj2324seedproject.presentation.api;

import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class GlobalRestControllerAdvice implements ExceptionMapper<PersistenceException> {

    @Override
    public Response toResponse(PersistenceException exception) {
        return Response.serverError()
            .type(MediaType.APPLICATION_JSON)
            .entity(Map.of(
                "title", "Persistence Error",
                "detail", exception.getMessage() != null ? exception.getMessage() : "Unknown persistence error"
            ))
            .build();
    }
}
