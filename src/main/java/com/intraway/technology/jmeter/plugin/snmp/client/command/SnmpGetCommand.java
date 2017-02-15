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
import org.snmp4j.smi.AbstractVariable;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public class SnmpGetCommand extends SnmpCommand {

  protected final static String name = "GET";
  private static final Logger log = LoggerFactory.getLogger(SnmpGetCommand.class);

  public SnmpGetCommand(Integer snmpVersion, String[] oid, String community, InetAddress destination, Integer port, Integer timeout, Integer retries) {
    super(snmpVersion, oid, community, destination, port, timeout, retries, 0);
    log.debug("Initializing SNMP GET COMMAND", oid, community, destination, port, timeout, retries);
  }

  @Override
  public Response execute() throws SnmpException {
    log.info("SNMP GET Called", oid, community, port, destination, timeout, retries);
    HashMap<String, String> item = new HashMap<String, String>();
    item.put("oid", oid[0]);
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
    pdu.addOID(new VariableBinding(new OID(oid[0])));
    pdu.setType(PDU.GET);

    log.debug("SNMP GET PDU", pdu);

    snmp = new Snmp(transport);
    try {
      response = snmp.get(pdu, comtarget);
    } catch (IOException e) {
      log.error("cant get response from trap", e);
      throw new SnmpException("Cant get response from trap");
    }

    log.debug("Response", response);

    if (response.getResponse() != null) {
      log.info("Got a response", response.getResponse());
      if (response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
        PDU pduresponse = response.getResponse();
        VariableBinding vb = (VariableBinding) pduresponse.getVariableBindings().firstElement();
        AbstractVariable v = (AbstractVariable) vb.getVariable();
        item.put("oid", vb.getOid().toString());
        item.put("value", v.toString());
        item.put("type", v.getSyntaxString());
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
    return new Response(item);
  }

}
