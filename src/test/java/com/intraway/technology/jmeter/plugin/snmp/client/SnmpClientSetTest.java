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

public class SnmpClientSetTest {

  SimpleSnmpClient snmpClient;

  @Before
  public void setUp() {
    snmpClient = new SimpleSnmpClient();
  }

  @Test
  public void snmpSetTestOK() throws Exception {
    String oid = ".1.3.6.1.4.1.2021.10.1.3.1";
    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "216.228.63.200");
    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 1;
    try {
      List<HashMap<String, String>> res = snmpClient.call(CommandName.SET, SnmpConstants.version2c, new String[] { oid }, community,
          InetAddress.getByName(addr), port, timeout, retries, null, "Integer32", "1");
    } catch (SnmpException e) {
      Assert.assertEquals(SnmpException.noAccess, e.getStatus());
    }

  }

  @Test
  public void snmpSetTestTimeout() throws Exception {
    String oid = ".1.3.6.1.4.1.2021.10.1.3.1";
    String community = "public";
    String addr = "172.16.136.6";
    Integer port = 161;
    Integer timeout = 0;
    Integer retries = 0;
    try {
      List<HashMap<String, String>> res = snmpClient.call(CommandName.SET, SnmpConstants.version2c, new String[] { oid }, community,
          InetAddress.getByName(addr), port, timeout, retries, null, "Integer32", "1");
    } catch (SnmpException e) {
      Assert.assertEquals(SnmpException.snmpReqTimeout, e.getStatus());
    }

  }
}
