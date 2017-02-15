package com.intraway.technology.jmeter.plugin.snmp.sampler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.mp.SnmpConstants;

import com.intraway.technology.jmeter.plugin.snmp.client.SimpleSnmpClient;
import com.intraway.technology.jmeter.plugin.snmp.client.command.CommandName;
import com.intraway.technology.jmeter.plugin.snmp.exception.SnmpException;

public class SnmpGetBulkSampler extends AbstractSnmpSampler {
  private static final long serialVersionUID = 6393722552275749483L;
  private static final Logger logger = LoggerFactory.getLogger(SnmpGetBulkSampler.class);

  @Override
  public SampleResult sample(Entry arg0) {
    // TODO Auto-generated method stub
    SampleResult sr = new SampleResult();
    sr.setSampleLabel(getName());
    sr.setDataType(SampleResult.TEXT);

    Integer port = Integer.valueOf(getPropertyAsString(PORT).trim());
    String oid = getPropertyAsString(OID).trim();
    String community = getPropertyAsString(COMMUNITY).trim();
    Integer maxRepetitions = Integer.valueOf(getPropertyAsString(REPETITIONS).trim());

    Integer timeout;

    try {
      timeout = Integer.valueOf(getPropertyAsString(TIMEOUT).trim());
    } catch (NumberFormatException e) {
      sr.setErrorCount(1);
      sr.setResponseCode("404");
      sr.setResponseMessage("Invalid Timeout set - " + e.getMessage());
      return sr;
    }

    String snmpVersion = getPropertyAsString(SNMPVERSION).trim();
    String host = getPropertyAsString(HOST).trim();

    Integer version = SnmpConstants.version2c;
    if ("V1".equals(snmpVersion)) {
      version = SnmpConstants.version1;
    } else if ("V2.C".equals(snmpVersion)) {
      version = SnmpConstants.version2c;
    } else {
      version = SnmpConstants.version3;
    }

    Integer retries = getPropertyAsInt(RETRIES);
    String msg = "SNMP GetBulk - snmpVersion [" + snmpVersion + "] - host[" + host + "] - port [" + port + "] - community [" + community + "] - oid [" + oid
        + "] - retries [" + retries + "] - repetitions [" + maxRepetitions + "] - timeout [" + timeout + "]";
    sr.setSamplerData(msg);

    sr.sampleStart();

    SimpleSnmpClient client = new SimpleSnmpClient();

    List<HashMap<String, String>> result;
    try {
      result = client.call(CommandName.GETBULK, version, new String[] { oid }, community, InetAddress.getByName(host), port, timeout, retries, maxRepetitions,
          null, null);
    } catch (UnknownHostException uh) {
      logger.error("host resolution error", uh);
      sr.setErrorCount(1);
      sr.setResponseCode("500");
      sr.setResponseMessage(uh.getMessage());
      return sr;
    } catch (SnmpException e) {
      logger.error("Error al ejecutar get", e);
      sr.setErrorCount(1);
      sr.setResponseCode("504");
      sr.setResponseMessage("Operation Timeout");
      return sr;
    }

    sr.setResponseCodeOK();
    StringBuilder sb = new StringBuilder(100);
    for (HashMap<String, String> item : result) {
      sb.append("[OID: ");
      sb.append(item.get("oid"));
      sb.append("] - [Type: ");
      sb.append(item.get("type"));
      sb.append("] - [Value: ");
      sb.append(item.get("value"));
      sb.append("]\n");
    }
    sr.setResponseData(sb.toString(), "UTF-8");

    sr.sampleEnd();
    sr.setSuccessful(true);

    return sr;
  }
}
