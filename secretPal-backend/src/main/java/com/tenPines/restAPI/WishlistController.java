package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private Worker worker;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Wish> wishes() {
        return system.retrieveAllWishes();
    }

    @RequestMapping(value = "/worker/{workerID}", method = RequestMethod.GET)
    @ResponseBody
    public List<Wish> getWorkersWishes(@PathVariable Long workerID) {
        Worker worker = system.retrieveAWorker(workerID);
        return system.retrievallWishesForWorker(worker);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Wish save(@RequestBody @Valid Wish wish, BindingResult result) throws RestfulException {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        return system.saveWish(wish);
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
