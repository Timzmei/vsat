package ru.rtrn.vsat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.rtrn.vsat.entities.Station;
import ru.rtrn.vsat.httpReq.SimpleRequest;
import ru.rtrn.vsat.services.SnmpSevice;
import ru.rtrn.vsat.services.StationService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class VsatController {
    @Autowired
    private SimpleRequest simpleRequest;
    @Autowired
    private SnmpSevice snmpSevice;
    @Autowired
    private StationService stationService;

    @GetMapping
//    public List<Station> list() {
//        return stationService.getStations();
//    }

    public Station list() {
        return stationService.getStations().get(0);
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String getStations(Model uiModel) throws IOException {
//        uiModel.addAttribute("stations", stationService.getStations());
//        return "index";
//    }

}
