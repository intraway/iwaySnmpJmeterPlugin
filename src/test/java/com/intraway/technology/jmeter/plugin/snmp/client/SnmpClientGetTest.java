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

public class SnmpClientGetTest {

  SimpleSnmpClient snmpClient;

  @Before
  public void setUp() {
    snmpClient = new SimpleSnmpClient();
  }

  @Test
  public void snmpGetTestOK() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.4.1.2021.10.1.3.1";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 1;
    List<HashMap<String, String>> res = snmpClient.call(CommandName.GET, SnmpConstants.version2c, new String[] { oid }, community, InetAddress.getByName(addr),
        port, timeout, retries, null, null, null);

    for (HashMap<String, String> item : res) {
      Assert.assertNotNull(item.get("value"));
    }
  }

  @Test
  public void snmpGetTestInvalidOID() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.1.1.4";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 5;

    List<HashMap<String, String>> res = snmpClient.call(CommandName.GET, SnmpConstants.version2c, new String[] { oid }, community, InetAddress.getByName(addr),
        port, timeout, retries, null, null, null);

    for (HashMap<String, String> item : res) {
      Assert.assertEquals("noSuchObject", item.get("value"));
    }

  }

  @Test
  public void snmpGetTestInvalidHost() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.4.1.2021.10.1.3.1";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 2161;
    Integer timeout = 1000;
    Integer retries = 1;
    try {
      List<HashMap<String, String>> res = snmpClient.call(CommandName.GET, SnmpConstants.version2c, new String[] { oid }, community,
          InetAddress.getByName(addr), port, timeout, retries, null, null, null);
      Assert.fail();
    } catch (SnmpException e) {
      Assert.assertEquals(SnmpException.snmpReqTimeout, e.getStatus());
    }
  }

  @Test
  public void snmpGetTestOKGauge32() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.2.1.2.2.1.5.4000038";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 1;
    List<HashMap<String, String>> res = snmpClient.call(CommandName.GET, SnmpConstants.version2c, new String[] { oid }, community, InetAddress.getByName(addr),
        port, timeout, retries, null, null, null);

    for (HashMap<String, String> item : res) {
      Assert.assertNotNull(item.get("value"));
    }
  }

  @Test
  public void snmpGetTestOKGauge32Timeout1() throws Exception {
    // CPU 1 minute Load
    String oid = ".1.3.6.1.2.1.2.2.1.5.4000038";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 0;
    Integer retries = 0;
    try {
      List<HashMap<String, String>> res = snmpClient.call(CommandName.GET, SnmpConstants.version2c, new String[] { oid }, community,
          InetAddress.getByName(addr), port, timeout, retries, null, null, null);
      Assert.fail();
    } catch (SnmpException e) {
      Assert.assertEquals(SnmpException.snmpReqTimeout, e.getStatus());
    }
  }

}
