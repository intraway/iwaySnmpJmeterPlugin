package com.intraway.technology.jmeter.plugin.snmp.client.command;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.AbstractVariable;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public class SnmpGetBulkCommand extends SnmpCommand {

  protected final static String name = "GET";
  private static final Logger log = LoggerFactory.getLogger(SnmpGetBulkCommand.class);

  public SnmpGetBulkCommand(Integer snmpVersion, String[] oid, String community, InetAddress destination, Integer port, Integer timeout, Integer retries,
      Integer maxRepetitions) {
    super(snmpVersion, oid, community, destination, port, timeout, retries, maxRepetitions);
    log.debug("Initializing SNMP GETBULK COMMAND", oid, community, destination, port, timeout, retries);
  }

  @Override
  public Response execute() throws SnmpException {
    log.info("SNMP GET BULK Called", oid, community, port, destination, timeout, retries);
    OctetString com = new OctetString(community);
    Address targetAddress = new UdpAddress(destination, port);
    TransportMapping transport;
    try {
      transport = new DefaultUdpTransportMapping();
      transport.listen();
    } catch (IOException e) {
      log.error("cant open transport", e);
      throw new SnmpException("Cant open transport");
    }

    CommunityTarget comtarget = new CommunityTarget();
    comtarget.setCommunity(com);
    comtarget.setVersion(snmpVersion);
    comtarget.setAddress(targetAddress);
    comtarget.setRetries(retries);
    comtarget.setTimeout(timeout);

    PDU pdu = new PDU();
    ResponseEvent response;
    Snmp snmp;
    for (String sOid : oid) {
      pdu.addOID(new VariableBinding(new OID(sOid)));
    }
    pdu.setType(PDU.GETBULK);
    pdu.setMaxRepetitions(maxRepetition);
    if (oid.length > 1) {
      pdu.setNonRepeaters(1);
    } else {
      pdu.setNonRepeaters(0);
    }

    log.debug("SNMP GETBULK PDU", pdu);

    snmp = new Snmp(transport);
    try {
      response = snmp.getBulk(pdu, comtarget);
    } catch (IOException e) {
      log.error("cant get response from trap", e);
      throw new SnmpException("Cant get response from trap");
    }

    log.debug("Response", response);
    Response resp = new Response();
    if (response.getResponse() != null) {
      log.info("Got a response", response.getResponse());
      if (response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
        PDU pduresponse = response.getResponse();
        Iterator it = pduresponse.getVariableBindings().iterator();
        while (it.hasNext()) {
          HashMap<String, String> item = new HashMap<String, String>();
          VariableBinding vb = (VariableBinding) it.next();
          AbstractVariable v = (AbstractVariable) vb.getVariable();
          item.put("oid", vb.getOid().toString());
          item.put("value", v.toString());
          item.put("type", v.getSyntaxString());
          resp.add(item);
        }
      }
    } else {
      log.error("Timeout");
      throw new SnmpException(SnmpException.snmpReqTimeout);
    }
    try {
      snmp.close();
    } catch (IOException e) {
      log.error("cant close snmp", e);
      throw new SnmpException("Cant close snmp");
    }
    return resp;
  }

}
