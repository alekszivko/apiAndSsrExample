package at.spengergasse.sj2324seedproject.presentation.www.storageObjects;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
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

@ApplicationScoped
@Path(StorageObjectController.BASE_URL)
public class StorageObjectController {

    public static final String BASE_URL = "/storageObjects";

    @Inject
    StorageObjectService serviceStorageObject;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance list(List<StorageObject> storageObjects);
        public static native TemplateInstance newStorageObject(CreateStorageObjectForm form);
        public static native TemplateInstance edit(EditStorageObjectForm form);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getStorageObject() {
        List<StorageObject> storageObjects = serviceStorageObject.findAll();
        return Templates.list(storageObjects);
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showNewForm() {
        return Templates.newStorageObject(CreateStorageObjectForm.create());
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleNewFormSubmission(
        @FormParam("storage") String storage,
        @FormParam("serialNr") String serialNr,
        @FormParam("mac") String mac,
        @FormParam("remark") String remark,
        @FormParam("projectDev") String projectDev,
        @FormParam("storedAtCu") String storedAtCu) {

        serviceStorageObject.createStorageObject("", storage, serialNr, mac, remark, projectDev, storedAtCu);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }

    @GET
    @Path("/edit/{key}")
    @Produces(MediaType.TEXT_HTML)
    public Response showEditForm(@PathParam("key") String key) {
        return serviceStorageObject.getStorageObjectByKey(key)
            .map(EditStorageObjectForm::create)
            .map(form -> Response.ok(Templates.edit(form)).build())
            .orElse(Response.seeOther(URI.create(BASE_URL)).build());
    }

    @POST
    @Path("/edit/{key}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleEditFormSubmission(
        @PathParam("key") String key,
        @FormParam("storage") String storage,
        @FormParam("serialNr") String serialNr,
        @FormParam("mac") String mac,
        @FormParam("remark") String remark,
        @FormParam("projectDev") String projectDev,
        @FormParam("storedAtCu") String storedAtCu) {

        serviceStorageObject.updateStorageObject(key, storage, serialNr, mac, remark, projectDev, storedAtCu);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }

    @GET
    @Path("/delete/{key}")
    public Response deleteStorageObject(@PathParam("key") String key) {
        serviceStorageObject.delete(key);
        return Response.seeOther(URI.create(BASE_URL)).build();
    }
}
