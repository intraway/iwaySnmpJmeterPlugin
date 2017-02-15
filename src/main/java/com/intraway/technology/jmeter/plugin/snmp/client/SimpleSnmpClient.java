package com.intraway.technology.jmeter.plugin.snmp.client;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intraway.technology.jmeter.plugin.snmp.client.command.CommandFactory;
import com.intraway.technology.jmeter.plugin.snmp.client.command.CommandName;
import com.intraway.technology.jmeter.plugin.snmp.client.command.Response;
import com.intraway.technology.jmeter.plugin.snmp.client.command.SnmpCommand;
import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

/**
 * Simplest client possible
 * @author jbosnjak
 *
 */
public class SimpleSnmpClient {

  /**
   * logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(SimpleSnmpClient.class.getClass());

  public List<HashMap<String, String>> call(CommandName cmd, Integer snmpVersion, String[] oid, String community, InetAddress addr, Integer port,
      Integer timeout, Integer retries, Integer maxRepetitions, String objectType, String value) throws SnmpException {
    logger.info("SNMP COMMAND CALL", cmd, oid, community, addr, retries, timeout);
    SnmpCommand CommandToCall = CommandFactory.getCommand(cmd, snmpVersion, oid, community, addr, port, timeout, retries, maxRepetitions, objectType, value);
    Response resp = CommandToCall.execute();
    return resp.get();
  }
}
