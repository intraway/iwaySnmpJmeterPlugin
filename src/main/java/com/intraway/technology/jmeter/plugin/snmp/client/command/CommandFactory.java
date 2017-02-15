package com.intraway.technology.jmeter.plugin.snmp.client.command;

import java.net.InetAddress;

import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public final class CommandFactory {

  private CommandFactory() {
  }

  public static SnmpCommand getCommand(CommandName name, Integer snmpVersion, String[] oid, String community, InetAddress destination, Integer port,
      Integer timeout, Integer retries, Integer maxRepetitions, String objectType, String value) throws SnmpException {

    SnmpCommand cmd;

    switch (name) {
    case GET:
      cmd = new SnmpGetCommand(snmpVersion, oid, community, destination, port, timeout, retries);
      break;
    case WALK:
      cmd = new SnmpWalkCommand(snmpVersion, oid, community, destination, port, timeout, retries);
      break;
    case GETBULK:
      cmd = new SnmpGetBulkCommand(snmpVersion, oid, community, destination, port, timeout, retries, maxRepetitions);
      break;
    case SET:
      cmd = new SnmpSetCommand(snmpVersion, oid, community, destination, port, timeout, retries, objectType, value);
      break;
    default:
      throw new SnmpException(SnmpException.badValue);
    }

    return cmd;
  }
}
