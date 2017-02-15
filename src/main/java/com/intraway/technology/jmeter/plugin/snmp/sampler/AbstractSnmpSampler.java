package com.intraway.technology.jmeter.plugin.snmp.sampler;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSnmpSampler extends org.apache.jmeter.samplers.AbstractSampler {
  private static final long serialVersionUID = -8637635942486594464L;

  public static final String HOST = "SnmpSampler.host";
  public static final String PORT = "SnmpSampler.port";
  public static final String RETRIES = "SnmpSampler.retries";
  public static final String OID = "SnmpSampler.oid";
  public static final String COMMUNITY = "SnmpSampler.community";
  public static final String TIMEOUT = "SnmpSampler.timeout";
  public static final String REPETITIONS = "SnmpSampler.repetitions";
  public static final String SNMPVERSION = "SnmpSampler.snmpversion";
  public static final String SNMPVERSIONSELECTED = "SnmpSampler.snmpversionSelected";

  public static final List<String> snmpProtocolVersions = new ArrayList<String>();
  static {
    snmpProtocolVersions.add("V1");
    snmpProtocolVersions.add("V2.C");
    snmpProtocolVersions.add("V3");
  }

  protected AbstractSnmpSampler() {
  }

}
