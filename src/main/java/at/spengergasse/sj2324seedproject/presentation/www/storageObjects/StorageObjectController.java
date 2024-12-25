package at.spengergasse.sj2324seedproject.presentation.www.storageObjects;

import at.spengergasse.sj2324seedproject.constants.ConstantsDomain;
import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(ConstantsDomain.TEMPLATE_STOO_BASE)
public class StorageObjectController implements RedirectForwardSupport{

    private final StorageObjectService serviceStorageObject;

    @GetMapping
    public String getStorageObject(Model model){
        List<StorageObject> storageObjects = serviceStorageObject.fetchStorageObjectsList();
        model.addAttribute("storageObjects",
                           storageObjects
                          );

        return "storageObjects/list";
    }

    @GetMapping(ConstantsDomain.TEMPLATE_STOO_NEW)
    public ModelAndView showNewForm(){
        var mav = new ModelAndView();
        mav.addObject("form",
                      CreateStorageObjectForm.create()
                     );
        mav.setViewName("storageObjects/new");
        return mav;
    }

    @PostMapping(ConstantsDomain.TEMPLATE_STOO_NEW)
    public String handleNewFormSubmisson(@Valid
                                         @ModelAttribute(name = "form")
                                         CreateStorageObjectForm form,
                                         BindingResult bindingResult){

        if(bindingResult.hasErrors()){
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

        return redirect(ConstantsDomain.TEMPLATE_STOO_BASE);
    }

    @GetMapping(ConstantsDomain.TEMPLATE_STOO_EDIT)
    public String showEditForm(
            @PathVariable
            String key,
            Model model){
        return serviceStorageObject.getStorageObjectByKey(key)
                                   .map(EditStorageObjectForm::create)
                                   .map(form -> model.addAttribute("form",
                                                                   form
                                                                  ))
                                   .map(_ -> "storageObjects/edit")
                                   .orElse(redirect(ConstantsDomain.TEMPLATE_STOO_BASE));

    }

    @PostMapping(ConstantsDomain.TEMPLATE_STOO_EDIT)
    public String handleEditFormSubmisson(
            @PathVariable
            String key,
            @Valid
            @ModelAttribute(name = "form")
            EditStorageObjectForm form,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
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
        return redirect(ConstantsDomain.TEMPLATE_STOO_BASE);
    }

    @GetMapping(ConstantsDomain.TEMPLATE_STOO_DELETE)
    public String deleteStorageObject(
            @PathVariable
            String key){
        serviceStorageObject.delete(key);
        return redirect(ConstantsDomain.TEMPLATE_STOO_BASE);
    }

//    @GetMapping
//    public String getSearchObject(@Valid
//                                        @ModelAttribute(name = "miniForm")
//                                        CommandSearch search,
//                                        BindingResult bindingResult){
//
//        if(bindingResult.hasErrors()){
//            return "storageObjects/list";
//        }
//
//        serviceStorageObject.searchFind(search.toString());    //TODO adding search bar into code...
//
//        return redirect(ConstantsDomain.TEMPLATE_STOO_BASE);
//    }

}
