package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Wish> wishes() {
        return system.retrieveAllWishes();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody @Valid Wish wish, BindingResult result) throws Exception {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        system.saveWish(wish);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        Wish wish = system.retrieveAWish(id);
        system.deleteAWish(wish);
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Wish> personalWish(@PathVariable Long id) {
        Worker worker = system.retrieveAWorker(id);
        return system.retrievePersonalGiftsFor(worker);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestfulException handleException(RestfulException e) {
        return e;
    }

}
