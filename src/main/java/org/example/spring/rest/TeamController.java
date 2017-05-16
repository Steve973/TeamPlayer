package org.example.spring.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the {@link RestController} for serving up Team resources.
 */
@RestController
public class TeamController {
    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public String greeting() {
        return "This is the TeamPlayer Application 'team' REST resource.";
    }
}
