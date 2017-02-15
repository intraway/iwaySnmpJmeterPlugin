package com.intraway.technology.jmeter.plugin.snmp.client.command;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;
import com.sun.jmx.snmp.SnmpStatusException;

public class SnmpSetCommand extends SnmpCommand {

  protected final static String name = "SET";
  private final static Logger log = LoggerFactory.getLogger(SnmpSetCommand.class);
  private final String objectType;
  private final String value;

  public SnmpSetCommand(Integer snmpVersion, String[] oid, String community, InetAddress destination, Integer port, Integer timeout, Integer retries,
      String objectType, String value) {
    super(snmpVersion, oid, community, destination, port, timeout, retries, 0);
    log.debug("Initializing SNMP SET COMMAND", oid, community, destination, timeout, retries, objectType, value);
    this.value = value;
    this.objectType = objectType;
  }

  @Override
  public Response execute() throws SnmpException {

    log.info("SNMP SET Called: {} {} {} {} {} {}", oid, community, destination, timeout, retries, value);
    String str = "";
    HashMap<String, String> item = new HashMap<String, String>();
    Snmp snmp = null;
    item.put("oid", oid[0]);
    OctetString com = new OctetString(community);
    Address targetAddress = new UdpAddress(destination, port);
    TransportMapping transport;
    try {
      transport = new DefaultUdpTransportMapping();
      transport.listen();

      CommunityTarget comtarget = new CommunityTarget();
      comtarget.setCommunity(com);
      comtarget.setVersion(snmpVersion);
      comtarget.setAddress(targetAddress);
      comtarget.setRetries(retries);
      comtarget.setTimeout(timeout);

      PDU pdu = new PDU();
      ResponseEvent response;
      // TODO debo setear lo que hay en objectType
      try {
        Class.forName(objectType);
      } catch (ClassNotFoundException ce) {
        // throw new
        // SnmpStatusException(SnmpStatusException.snmpRspInconsistentName);
      }

      pdu.add(new VariableBinding(new OID(oid[0]), new OctetString(value)));
      pdu.setType(PDU.SET);

      log.debug("SNMP SET PDU: {}", pdu);

      snmp = new Snmp(transport);
      response = snmp.set(pdu, comtarget);

      log.debug("Response", response);

      if (response != null && response.getResponse() != null) {
        log.info("Got a response: {}", response.getResponse());
        if (response.getResponse().getErrorStatus() > 0) {
          throw new SnmpException(response.getResponse().getErrorStatus());
        }
        if (response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
          PDU pduresponse = response.getResponse();
          str = pduresponse.getVariableBindings().firstElement().toString();
          if (str.contains("=")) {
            int len = str.indexOf('=');
            str = str.substring(len + 1, str.length());
          }
        }
      } else {
        log.error("TIMEOUT");
        throw new SnmpException(SnmpStatusException.snmpReqTimeout);
      }

      snmp.close();
    } catch (IOException ex) {
      log.error("error closing snmp connection", ex);
      throw new SnmpException("Cant close snmp");
    }

    log.info("RETRUNED RESPONSE", str);
    item.put("value", str);
    return new Response(item);
  }

}
