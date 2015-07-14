package restAPI;

import application.SecretPalSystem;
import model.SecretPalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/secretpalevent")
public class SecretPalEventController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<SecretPalEvent> secretPalEvents(){
        return system.retrieveAllSecretPalEvents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SecretPalEvent secretPalEvent(@PathVariable int id){
        return system.retrieveASecretPalEvent( id );
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestfulException handleException(RestfulException e){
        return e;
    }

}
