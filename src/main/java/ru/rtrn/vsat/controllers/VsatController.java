package ru.rtrn.vsat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rtrn.vsat.entities.Station;
import ru.rtrn.vsat.httpReq.SimpleRequest;
import ru.rtrn.vsat.services.SnmpSevice;
import ru.rtrn.vsat.services.StationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
//@RequiredArgsConstructor
@RequestMapping("stations")
public class VsatController {
    @Autowired
    private SimpleRequest simpleRequest;
    @Autowired
    private SnmpSevice snmpSevice;
    @Autowired
    private StationService stationService;

//    private int counter = 4;
//
//
//    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
//        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
//        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
//        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
//    }};
//


//    @GetMapping("{id}")
//    public Map<String, String> getOne(@PathVariable String id) {
//        System.out.println("Method getOne");
//
//        return getMessage(id);
//    }

    private Map<String, String> getMessage(@PathVariable String id) {
        System.out.println("Method getMessage");
        return null;
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        System.out.println("Method create");
        return null;
    }

//    @PutMapping("{id}")
//    public void update(
//            @PathVariable("id") String id,
//            @RequestBody String station
//    ) throws IOException, InterruptedException {
//        System.out.println("Method update");
//
//        stationService.stopWashing(station);
//    }

    @PutMapping(path="{id}")
    public String update(
            @PathVariable("id") String id,
            @RequestBody String station
    ) throws IOException, InterruptedException {
        stationService.stopWashing(id);
        System.out.println("Method update");
        System.out.println(id);
        System.out.println(station);
        return stationService.getMapStations().get(id).toString();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        System.out.println("Method delete");
    }


    @GetMapping
    public Map<String, Station> list() {
        return stationService.getMapStations();
    }

}
