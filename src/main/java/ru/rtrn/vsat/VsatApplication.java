package ru.rtrn.vsat;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class VsatApplication {

    public static void main(String[] args) {
// //        SpringApplication.run(VsatApplication.class, args);
//         new SpringApplicationBuilder(VsatApplication.class)
//                 .headless (false)
//                 .run (args);

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, null);
        map.merge(1,3,(a,b) -> a + b);
        map.merge(3,3,(a,b) -> a + b);
        System.out.println(map);
        List nums = new ArrayList();



    }

}
