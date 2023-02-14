package ru.rtrn.vsat.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Station {
    String id;
    String name;
    String ip;
    String value;
    String rele;
    String status;
//    String wash;

}
