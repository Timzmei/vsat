package ru.rtrn.vsat.services;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.stereotype.Service;
import ru.rtrn.vsat.entities.Device;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SnmpSevice {

    private int retries = 2;
    private int timeout = 1000;
    private int version = SnmpConstants.version2c;
    private TransportMapping transport;
    private Snmp snmp;
    private CommunityTarget target;
    private PDU responsePDU;

    public SnmpSevice() throws IOException {
        transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
        target = new CommunityTarget();
        responsePDU = null;
    }

    public String snmpGet(Device device, String ip) {
        String value = "";
        try {
            Address address = new UdpAddress(ip + "/" + device.getPort());
            target.setCommunity(new OctetString(device.getGetCommunity()));
            target.setAddress(address);
            target.setRetries(retries);
            target.setTimeout(timeout);
            target.setVersion(version);
            OID oid = new OID(device.getOid());
            PDU request = new PDU();
            request.setType(PDU.GET);
            request.add(new VariableBinding(oid));
            responsePDU = null;
            ResponseEvent responseEvent = snmp.send(request, target);
            if (responseEvent != null) {
                responsePDU = responseEvent.getResponse();
                if (responsePDU != null) {
                    int errorStatus = responsePDU.getErrorStatus();
                    String errorStatusText = responsePDU.getErrorStatusText();
                    if (errorStatus == PDU.noError) {
                        value = responsePDU.getVariable(oid).toString();
                    } else {
                        value = errorStatusText;
                    }
                } else {
                    value = "Error: Response PDU is null";
                }
            } else {
                value = "Error: Agent Timeout... ";
            }
        } catch (IOException ee) {
            ee.printStackTrace();
        }

        if (checkValue(value)) {
            value = String.valueOf(Double.parseDouble(value) / 100);
        }
        return value;
    }

    public boolean checkValue(String value) {
        String regex = "^[0-9,.]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public void snmpClose() throws IOException {
        System.out.println("Сервис закрывается");
        snmp.close();
    }

    public String snmpSet(Device device, String ip, int newValue) {
        String value = "";
        try {
            Address address = new UdpAddress(ip + "/" + device.getPort());
            target.setCommunity(new OctetString(device.getSetCommunity()));
            target.setAddress(address);
            target.setRetries(retries);
            target.setTimeout(timeout);
            target.setVersion(version);
            OID oid = new OID(device.getOid());
            PDU request = new PDU();
            request.setType(PDU.SET);
            Variable var = new Integer32(newValue);
            request.add(new VariableBinding(oid, var));
            responsePDU = null;
            ResponseEvent responseEvent = snmp.send(request, target);
            if (responseEvent != null) {
                responsePDU = responseEvent.getResponse();
                if (responsePDU != null) {
                    int errorStatus = responsePDU.getErrorStatus();
                    String errorStatusText = responsePDU.getErrorStatusText();
                    if (errorStatus == PDU.noError) {
                        value = responsePDU.getVariable(oid).toString();
                    } else {
                        value = errorStatusText;
//                        log.error("SET RESPONSE: " + target.getAddress() + " - " + errorStatusText);
                    }
                } else {
                    value = "Error: Response PDU is null";
                }
            } else {
                value = "Error: Agent Timeout... ";
            }
        } catch (IOException ee) {
            ee.printStackTrace();
        }

        if (checkValue(value)) {
            value = String.valueOf(Double.parseDouble(value) / 100);
        }
        return value;
    }

}
