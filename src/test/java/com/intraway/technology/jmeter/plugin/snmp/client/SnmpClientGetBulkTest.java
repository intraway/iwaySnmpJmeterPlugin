package com.intraway.technology.jmeter.plugin.snmp.client;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.snmp4j.mp.SnmpConstants;

import com.intraway.technology.jmeter.plugin.snmp.client.command.CommandName;

public class SnmpClientGetBulkTest {

  SimpleSnmpClient snmpClient;

  @Before
  public void setUp() {
    snmpClient = new SimpleSnmpClient();
  }

  @Test
  public void snmpGetBulkTestOK() throws Exception {
    // CPU 1 minute Load
    String[] oids = new String[] { ".1.3.6.1.2.1.2.2.1.11.5000064", ".1.3.6.1.2.1.2.2.1.11.5000065" };

    String community = "public";
    String addr = System.getProperty("SERVER_IP_WITH_SNMP", "172.16.136.6");

    Integer port = 161;
    Integer timeout = 4000;
    Integer retries = 1;
    Integer maxRepetitions = 5;
    List<HashMap<String, String>> res = snmpClient.call(CommandName.GETBULK, SnmpConstants.version2c, oids, community, InetAddress.getByName(addr), port,
        timeout, retries, maxRepetitions, null, null);

    for (HashMap<String, String> item : res) {
      System.out.println(item.get("oid") + " - " + item.get("value"));
      Assert.assertNotNull(item.get("value"));
    }
  }

}
