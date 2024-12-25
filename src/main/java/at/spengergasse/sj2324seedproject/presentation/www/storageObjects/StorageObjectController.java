package at.spengergasse.sj2324seedproject.presentation.www.storageObjects;

import static at.spengergasse.sj2324seedproject.presentation.www.storageObjects.StorageObjectController.BASE_URL;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class StorageObjectController implements RedirectForwardSupport {

  public static final String BASE_URL = "/storageObjects";

  private final StorageObjectService serviceStorageObject;

  @GetMapping
  public String getStorageObject(Model model) {
    List<StorageObject> storageObjects = serviceStorageObject.findAll();
    model.addAttribute("storageObjects",
        storageObjects
    );

    return "storageObjects/list";
  }

  @GetMapping("/new")
  public ModelAndView showNewForm() {
    var mav = new ModelAndView();
    mav.addObject("form",
        CreateStorageObjectForm.create()
    );
    mav.setViewName("storageObjects/new");
    return mav;
  }

  @PostMapping("/new")
  public String handleNewFormSubmisson(@Valid
      @ModelAttribute(name = "form")
      CreateStorageObjectForm form,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "storageObjects/new";
    }

    serviceStorageObject.createStorageObject(form.randomKey(),
        form.storage(),
        form.serialNr(),
        form.mac(),
        form.remark(),
        form.projectDev(),
        form.storedAtCu()
    );

    // Redirect after post pattern / PRG pattern
    //        List<StorageObject> storageObjects = serviceStorageObject.fetchStorageObjectsList();

    return redirect(BASE_URL);
  }

  @GetMapping("/edit/{key}")
  public String showEditForm(
      @PathVariable
      String key,
      Model model) {
    return serviceStorageObject.getStorageObjectByKey(key)
        .map(EditStorageObjectForm::create)
        .map(form -> model.addAttribute("form",
            form
        ))
        .map(_ -> "storageObjects/edit")
        .orElse(redirect(BASE_URL));

  }

  @PostMapping("/edit/{key}")
  public String handleEditFormSubmisson(
      @PathVariable
      String key,
      @Valid
      @ModelAttribute(name = "form")
      EditStorageObjectForm form,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "storageObjects/new";
    }
    serviceStorageObject.updateStorageObject(key,
        form.storage(),
        form.serialNr(),
        form.mac(),
        form.remark(),
        form.projectDev(),
        form.storedAtCu()
    );
    return redirect(BASE_URL);
  }

  @GetMapping("/delete/{key}")
  public String deleteStorageObject(
      @PathVariable
      String key) {
    serviceStorageObject.delete(key);
    return redirect(BASE_URL);
  }
}
