package ru.rtrn.vsat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.rtrn.vsat.services.StationService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class VsatController {

    @Autowired
    private StationService stationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStations(Model uiModel) throws IOException {
        uiModel.addAttribute("stations", stationService.getStations());
        return "index";
    }

}
