package ru.rtrn.vsat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
@RequiredArgsConstructor
public class VsatController {
    @Autowired
    private SimpleRequest simpleRequest;
    @Autowired
    private SnmpSevice snmpSevice;
    @Autowired
    private StationService stationService;

    public List<Station> list() {
        return stationService.getStations();
    }

}
