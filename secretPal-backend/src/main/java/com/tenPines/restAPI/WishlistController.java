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
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@RequestBody @Valid Wish wish, BindingResult result) throws Exception {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        system.saveWish(wish);
    }

    @RequestMapping(value = "/person/{person_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Wish> personalWish(@PathVariable Long person_id) {
        Worker worker = system.retrieveAWorker(person_id);
        return system.retrievePersonalGiftsFor(worker);
    }

    @RequestMapping(value = "/person/{person_id}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveWish(@PathVariable Long person_id, @RequestBody String gift) {
        Worker worker = system.retrieveAWorker(person_id);
        system.saveWish(new Wish(worker, gift));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateWish(@PathVariable Long id, @RequestBody String gift) {
        Wish wish = system.retrieveAWish(id);
        wish.setGift(gift);
        system.updateWish(wish);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateWish(@PathVariable Long id) {
        Wish wish = system.retrieveAWish(id);
        system.deleteAWish(wish);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestfulException handleException(RestfulException e) {
        return e;
    }

}
