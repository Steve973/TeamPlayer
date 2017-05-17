package org.example.spring.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the {@link RestController} for serving up Person resources.
 */
@RestController
public class PlayerController {
    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public String greeting() {
        return "This is the TeamPlayer Application 'player' REST resource.";
    }
}
