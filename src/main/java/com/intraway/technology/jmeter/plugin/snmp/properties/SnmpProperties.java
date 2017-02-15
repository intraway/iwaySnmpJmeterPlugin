package com.intraway.technology.jmeter.plugin.snmp.properties;

import java.util.Properties;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.testbeans.TestBean;

import com.intraway.technology.jmeter.plugin.snmp.sampler.AbstractSnmpSampler;

public class SnmpProperties extends ConfigTestElement implements TestBean {
  private static final long serialVersionUID = 468255622613306730L;

  /**
   * @return the host
   */
  public String getHost() {
    return getPropertyAsString(AbstractSnmpSampler.HOST);
  }

  /**
   * @param host
   *          the host to set
   */
  public void setHost(String host) {
    setProperty(AbstractSnmpSampler.HOST, host);
  }

  public void addProperties(Properties prop) {
    prop.put("SnmpSampler.host", getHost());
  }
}