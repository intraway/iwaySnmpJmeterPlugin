package com.intraway.technology.jmeter.plugin.snmp.gui;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intraway.technology.jmeter.plugin.snmp.sampler.SnmpGetSampler;

public class SNMPGet extends AbstractGUI {
  private static final long serialVersionUID = 3197090412869386190L;
  public static final String LABEL = "Snmp get sampler";

  private static final Logger logger = LoggerFactory.getLogger(SNMPGet.class);

  @Override
  public void configure(TestElement element) {
    logger.info("SNMPGet.configure");
    super.configure(element);
    // NAME_AND_VALUE.setText(element.getPropertyAsString(BatchPutSampler.NAME_AND_VALUE));
    // CSERIALIZER.setSelectedItem(element.getPropertyAsString(BatchPutSampler.COLUMN_SERIALIZER_TYPE));
    // VSERIALIZER.setSelectedItem(element.getPropertyAsString(BatchPutSampler.VALUE_SERIALIZER_TYPE));
    // IS_COUNTER.setSelected(element.getPropertyAsBoolean(BatchPutSampler.IS_COUNTER));
  }

  @Override
  public TestElement createTestElement() {
    logger.info("SNMPGet.createTestElement");
    SnmpGetSampler sampler = new SnmpGetSampler();
    modifyTestElement(sampler);
    sampler.setComment("Snmp get sampler");
    sampler.setProperty(SnmpGetSampler.COMMUNITY, COMMUNITY.getText());
    sampler.setProperty(SnmpGetSampler.OID, OID.getText());
    sampler.setProperty(SnmpGetSampler.PORT, PORT.getText());
    sampler.setProperty(SnmpGetSampler.HOST, HOST.getText());
    sampler.setProperty(SnmpGetSampler.TIMEOUT, TIMEOUT.getText());
    sampler.setProperty(SnmpGetSampler.RETRIES, RETRIES.getValue().toString());
    sampler.setProperty(SnmpGetSampler.SNMPVERSION, SNMPVERSION.getSelectedItem().toString());
    sampler.setProperty(SnmpGetSampler.SNMPVERSIONSELECTED, SNMPVERSION.getSelectedIndex());
    return sampler;
  }

  public void modifyTestElement(TestElement sampler) {
    logger.info("SNMPGet.modifyTestElement");
    super.configureTestElement(sampler);
    if (sampler instanceof SnmpGetSampler) {
      sampler.setProperty(SnmpGetSampler.COMMUNITY, COMMUNITY.getText());
      sampler.setProperty(SnmpGetSampler.OID, OID.getText());
      sampler.setProperty(SnmpGetSampler.PORT, PORT.getText());
      sampler.setProperty(SnmpGetSampler.HOST, HOST.getText());
      sampler.setProperty(SnmpGetSampler.TIMEOUT, TIMEOUT.getText());
      sampler.setProperty(SnmpGetSampler.RETRIES, RETRIES.getValue().toString());
      sampler.setProperty(SnmpGetSampler.SNMPVERSION, SNMPVERSION.getSelectedItem().toString());
      sampler.setProperty(SnmpGetSampler.SNMPVERSIONSELECTED, SNMPVERSION.getSelectedIndex());
    }
  }

  public void initFields() {
    logger.info("SNMPGet.initFields");
    // NAME_AND_VALUE.setText("${__Random(1,1000)}:${__Random(1,1000)}\n${__Random(1,1000)}:${__Random(1,1000)}");
    // CSERIALIZER.setSelectedItem("Column Serializer");
    // VSERIALIZER.setSelectedItem("Value Serializer");
    // IS_COUNTER.setSelected(false);

    PORT.setText("161");
  }

  public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints) {
    logger.info("SNMPGet.init");
  }

  @Override
  public String getLable() {
    return LABEL;
  }
}
