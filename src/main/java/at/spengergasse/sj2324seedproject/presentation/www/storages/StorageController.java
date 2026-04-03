package at.spengergasse.sj2324seedproject.presentation.www.storages;

import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.service.StorageService;
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
@Path(StorageController.BASE_URL)
public class StorageController {

    protected static final String BASE_URL = "/storages";

    @Inject
    StorageService storageService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance list(List<Storage> storages);
        public static native TemplateInstance newStorage(CreateStorageForm form);
        public static native TemplateInstance edit(EditStorageForm form);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getStorages() {
        List<Storage> storages = storageService.fetchStorage(Optional.empty());
        return Templates.list(storages);
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showNewStorageForm() {
        return Templates.newStorage(CreateStorageForm.create());
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleNewStorageFormSubmission(
        @FormParam("name") String name,
        @FormParam("street") String street,
        @FormParam("number") Integer number,
        @FormParam("addressAddition") String addressAddition,
        @FormParam("zipcode") Integer zipcode,
        @FormParam("city") String city) {

        storageService.createStorage(name, street, number, addressAddition, zipcode, city);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }

    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response editStorage(@PathParam("id") Long id) {
        return storageService.getStorageById(id)
            .map(EditStorageForm::create)
            .map(form -> Response.ok(Templates.edit(form)).build())
            .orElse(Response.seeOther(URI.create(BASE_URL)).build());
    }

    @POST
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleEditStorageFormSubmission(
        @PathParam("id") Long id,
        @FormParam("name") String name,
        @FormParam("addressAddition") String addressAddition,
        @FormParam("street") String street,
        @FormParam("number") Integer number,
        @FormParam("zipcode") Integer zipcode,
        @FormParam("city") String city) {

        storageService.updateStorage(id, name, addressAddition, street, number, zipcode, city);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteStorage(@PathParam("id") Long id) {
        storageService.removeStorage(id);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }
}
