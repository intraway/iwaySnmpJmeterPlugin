package com.intraway.technology.jmeter.plugin.snmp.client.command;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public class SnmpWalkCommand extends SnmpCommand {

  protected final static String name = "WALK";
  private static final Logger log = LoggerFactory.getLogger(SnmpWalkCommand.class);

  public SnmpWalkCommand(Integer snmpVersion, String[] oid, String community, InetAddress destination, Integer port, Integer timeout,
      Integer retries) {
    super(snmpVersion, oid, community, destination, port, timeout, retries, 0);
    log.debug("Initializing SNMP WALK COMMAND", oid, community, destination, timeout, retries);
  }

  @Override
  @SuppressWarnings(value = "unchecked")
  public Response execute() throws SnmpException {

    log.info("SNMP WALK Called", oid, community, destination, timeout, retries);

    OctetString com = new OctetString(community);
    Address targetAddress = new UdpAddress(destination, port);
    TransportMapping transport;
    Snmp snmp;
    try {
      transport = new DefaultUdpTransportMapping();
      snmp = new Snmp(transport);
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

    Response resp = new Response();

    OID ooid = null;
    try {
      ooid = new OID(oid[0]);
    } catch (RuntimeException e) {
      log.error("OID is not specified correctly.", e);
      e.printStackTrace();
      throw new SnmpException(SnmpException.badValue);
    }

    TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
    List<TreeEvent> events = treeUtils.getSubtree(comtarget, ooid);

    // Get snmpwalk result.
    for (TreeEvent event : events) {
      if (event != null) {

        if (TreeEvent.STATUS_TIMEOUT == event.getStatus()) {
          throw new SnmpException(SnmpException.snmpReqTimeout);
        }
        if (event.isError()) {
          log.error("oid [" + oid[0] + "] " + event.getErrorMessage());
          throw new SnmpException(event.getStatus());
        }

        VariableBinding[] varBindings = event.getVariableBindings();
        if ((varBindings == null || varBindings.length == 0) && resp.isEmpty()) {
          log.debug("No result returned for event: {}", event);
          log.info("No result returned. Calling get");
          SnmpGetCommand get = new SnmpGetCommand(snmpVersion, oid, community, destination, port, timeout, retries);
          return get.execute();
        }
        if (varBindings != null) {
          for (VariableBinding varBinding : varBindings) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("oid", varBinding.getOid().toString());
            item.put("value", varBinding.getVariable().toString());
            item.put("type", varBinding.getVariable().getSyntaxString());
            resp.add(item);
          }
        }
      }
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
