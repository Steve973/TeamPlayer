package org.example.spring.rest;

import org.example.spring.service.DataInitService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DataInitController {

    private final DataInitService dataInitService;

    public DataInitController(DataInitService dataInitService) {
        this.dataInitService = dataInitService;
    }

    /**
     * Drops and recreates the data store, then populates it with data from the json data files.
     */
    @PutMapping(value = "/init")
    public String initializeData() throws IOException {
        return dataInitService.init();
    }
}
