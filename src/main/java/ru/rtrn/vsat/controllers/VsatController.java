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
//    @GetMapping
//    public List<Map<String, String>> list() {
//        return messages;
//    }
//
//
//    @GetMapping("{id}")
//    public Map<String, String> getOne(@PathVariable String id) {
//        return getMessage(id);
//    }
//
//    private Map<String, String> getMessage(@PathVariable String id) {
//        return messages.stream()
//                .filter(message -> message.get("id").equals(id)).findFirst().get();
//    }
//
//    @PostMapping
//    public Map<String, String> create(@RequestBody Map<String, String> message) {
//        message.put("id", String.valueOf(counter++));
//
//        messages.add(message);
//
//        return message;
//    }
//
    @GetMapping("{id}")
    public void update(
            @RequestBody String station
    ) throws IOException, InterruptedException {
        stationService.stopWashing(station);
    }
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable String id) {
//        Map<String, String> message = getMessage(id);
//
//        messages.remove(message);
//    }


//    @GetMapping
//    public List<Station> list() {
//        return stationService.getStations();
//    }


    @GetMapping
    public Map<String, Station> list() {
        return stationService.getMapStations();
    }

}
