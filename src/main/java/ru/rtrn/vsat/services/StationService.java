package ru.rtrn.vsat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rtrn.vsat.entities.Device;
import ru.rtrn.vsat.entities.Sdk;
import ru.rtrn.vsat.entities.Station;
import ru.rtrn.vsat.entities.Vsat;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StationService {

    ArrayList<Station> stations;

    @Autowired
    SnmpSevice snmpSevice;

    public StationService() throws IOException {
        stations = new ArrayList<>();
        loadStations();
        startUpdate();
    }

    public void loadStations() throws IOException {
        File file = new File("Stations.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(" ");
            stations.add(new Station(split[0], split[1], " ", " ", " "));
            line = reader.readLine();
        }
    }

    public void startUpdate() throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(11);
        threadPool.submit(() -> {
            updateValue(0, 20);
        });
        threadPool.submit(() -> {
            updateValue(20, 40);
        });
        threadPool.submit(() -> {
            updateValue(40, 60);
        });
        threadPool.submit(() -> {
            updateValue(60, 80);
        });
        threadPool.submit(() -> {
            updateValue(80, 100);
        });
        threadPool.submit(() -> {
            updateValue(100, 120);
        });
        threadPool.submit(() -> {
            updateValue(120, 140);
        });
        threadPool.submit(() -> {
            updateValue(140, 160);
        });
        threadPool.submit(() -> {
            updateValue(160, 180);
        });
        threadPool.submit(() -> {
            updateValue(180, 200);
        });
        threadPool.submit(() -> {
            updateValue(200, 216);
        });
    }

    private void updateValue(int start, int stop) {
        try {
            snmpSevice = new SnmpSevice();
            Device vsat = new Vsat();
            while (true) {
                for (int i = start; i < stop; i++) {
                    String val = snmpSevice.snmpGet(vsat, stations.get(i).getIp());
                    stations.get(i).setValue(val);
                    if (snmpSevice.checkValue(val)) {
                        if (Double.parseDouble(val) >= 12) stations.get(i).setStatus("Ok");
                        if (Double.parseDouble(val) < 12) {
                            stations.get(i).setStatus("Low level");
                            if (stations.get(i).getStatus().equals("Очистка")) continue;
                            //Убрать if "10.2.27.1"
                            else if (stations.get(i).getIp().equals("10.2.27.1")) {
                                stations.get(i).setStatus("Очистка");
                                startWashing(stations.get(i));
                            }
                        }
                    } else stations.get(i).setStatus("Time Out");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void startWashing(Station alarmStation) {
        Thread washProcess = new Thread(() -> {
            try {
                SnmpSevice snmpSeviceSdk = new SnmpSevice();
                Device sdk = new Sdk();
                String newIp = alarmStation.getIp().substring(0, alarmStation.getIp().length() - 1) + "2";
                String releStatus = snmpSeviceSdk.snmpGet(sdk, newIp);
                if (releStatus.equals("0")) {
                    releStatus = snmpSeviceSdk.snmpSet(sdk,newIp,1);
                    Thread.sleep(20000);
                    releStatus = snmpSeviceSdk.snmpSet(sdk,newIp,0);
                    Thread.sleep(180000);
                }
                else {
                    releStatus = snmpSeviceSdk.snmpSet(sdk,newIp,0);
                }
                alarmStation.setRele(parseReleStatus(releStatus));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                alarmStation.setStatus("Готово");
                Thread.currentThread().stop();
            }
        });
        washProcess.start();
    }
    public String parseReleStatus(String val){
        String s = " ";
        if (val.equals("0")) s = "Разомкнуто";
        else if (val.equals("1")) s = "Замкнуто";
        else s = "Ошибка";
        return s;
    }
}
