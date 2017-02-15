package com.intraway.technology.jmeter.plugin.snmp.client.command;

import java.net.InetAddress;

import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public abstract class SnmpCommand {

  protected String[] oid;
  protected String community;
  protected Integer snmpVersion;
  protected Integer maxRepetition;
  
  protected Integer retries;
  protected Integer timeout;
  protected Integer port;
  protected InetAddress destination;

  public abstract Response execute() throws SnmpException;

  public SnmpCommand(Integer snmpVersion, String[] oid, String community, InetAddress destination, Integer port, Integer timeout, Integer retries,
      Integer maxRepetition) {
    this.oid = oid;
    this.community = community;
    this.snmpVersion = snmpVersion;
    this.maxRepetition = maxRepetition;
    this.port = port;
    this.retries = retries;
    this.timeout = timeout;
    this.destination = destination;
  }

  public String[] getOid() {
    return oid;
  }

  public String getCommunity() {
    return community;
  }

  /**
   * @return the snmpVersion
   */
  public Integer getSnmpVersion() {
    return snmpVersion;
  }

  /**
   * @return the maxRepetition
   */
  public Integer getMaxRepetition() {
    return maxRepetition;
  }
  
  public Integer getRetries() {
    return retries;
  }

  public Integer getTimeout() {
    return timeout;
  }

  public InetAddress getDestination() {
    return destination;
  }

  /**
   * @return the port
   */
  public Integer getPort() {
    return port;
  }
}
