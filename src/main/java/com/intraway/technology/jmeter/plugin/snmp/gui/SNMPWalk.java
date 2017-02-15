package com.intraway.technology.jmeter.plugin.snmp.gui;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intraway.technology.jmeter.plugin.snmp.sampler.SnmpWalkSampler;

public class SNMPWalk extends AbstractGUI {
  private static final long serialVersionUID = 3197090412869386190L;
  public static final String LABEL = "Snmp walk sampler";

  private static final Logger logger = LoggerFactory.getLogger(SNMPWalk.class);

  @Override
  public void configure(TestElement element) {
    logger.info("SNMPWalk.configure");
    super.configure(element);
  }

  @Override
  public TestElement createTestElement() {
    logger.info("SNMPWalk.createTestElement");
    SnmpWalkSampler sampler = new SnmpWalkSampler();
    modifyTestElement(sampler);
    sampler.setComment("Snmp get sampler");
    sampler.setProperty(SnmpWalkSampler.COMMUNITY, COMMUNITY.getText());
    sampler.setProperty(SnmpWalkSampler.OID, OID.getText());
    sampler.setProperty(SnmpWalkSampler.PORT, PORT.getText());
    sampler.setProperty(SnmpWalkSampler.HOST, HOST.getText());
    sampler.setProperty(SnmpWalkSampler.TIMEOUT, TIMEOUT.getText());
    sampler.setProperty(SnmpWalkSampler.RETRIES, RETRIES.getValue().toString());
    sampler.setProperty(SnmpWalkSampler.SNMPVERSION, SNMPVERSION.getSelectedItem().toString());
    sampler.setProperty(SnmpWalkSampler.SNMPVERSIONSELECTED, SNMPVERSION.getSelectedIndex());
    return sampler;
  }

  public void modifyTestElement(TestElement sampler) {
    logger.info("SNMPWalk.modifyTestElement");
    super.configureTestElement(sampler);
    if (sampler instanceof SnmpWalkSampler) {
      sampler.setProperty(SnmpWalkSampler.COMMUNITY, COMMUNITY.getText());
      sampler.setProperty(SnmpWalkSampler.OID, OID.getText());
      sampler.setProperty(SnmpWalkSampler.PORT, PORT.getText());
      sampler.setProperty(SnmpWalkSampler.HOST, HOST.getText());
      sampler.setProperty(SnmpWalkSampler.TIMEOUT, TIMEOUT.getText());
      sampler.setProperty(SnmpWalkSampler.RETRIES, RETRIES.getValue().toString());
      sampler.setProperty(SnmpWalkSampler.SNMPVERSION, SNMPVERSION.getSelectedItem().toString());
      sampler.setProperty(SnmpWalkSampler.SNMPVERSIONSELECTED, SNMPVERSION.getSelectedIndex());
    }
  }

  public void initFields() {
    logger.info("SnmpWalk.initFields");
    PORT.setText("161");
  }

  public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints) {
    logger.info("SNMPWalk.init");
  }

  @Override
  public String getLable() {
    return LABEL;
  }
}
