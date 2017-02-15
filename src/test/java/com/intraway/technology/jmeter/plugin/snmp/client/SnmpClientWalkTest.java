package com.intraway.technology.jmeter.plugin.snmp.client;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.snmp4j.mp.SnmpConstants;

import com.intraway.technology.jmeter.plugin.snmp.client.command.CommandName;
import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public class SnmpClientWalkTest {

  SimpleSnmpClient snmpClient;

  @Before
  public void setUp() {
    snmpClient = new SimpleSnmpClient();
  }

  @Test
  public void snmpWalkTestOK() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.2.1.2.2.1.5";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 1;
    List<HashMap<String, String>> res = snmpClient.call(CommandName.WALK, SnmpConstants.version2c, new String[] { oid }, community, InetAddress.getByName(addr),
        port, timeout, retries, null, null, null);

    Assert.assertEquals(true, res.size() > 0);
  }

  @Test
  public void snmpWalkTestTimeOut() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.2.1";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 1;
    Integer retries = 1;
    try {
      snmpClient.call(CommandName.WALK, SnmpConstants.version2c, new String[] { oid }, community, InetAddress.getByName(addr), port, timeout, retries, null,
          null, null);
      Assert.fail();
    } catch (SnmpException e) {
      Assert.assertEquals(SnmpException.snmpReqTimeout, e.getStatus());
    }
  }

  @Test
  public void snmpWalkTestOKFallbacktoGet() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.2.1.2.2.1.5.8000000";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 1;
    List<HashMap<String, String>> res = snmpClient.call(CommandName.WALK, SnmpConstants.version2c, new String[] { oid }, community, InetAddress.getByName(addr),
        port, timeout, retries, null, null, null);

    Assert.assertEquals(true, res.size() > 0);
  }

}
