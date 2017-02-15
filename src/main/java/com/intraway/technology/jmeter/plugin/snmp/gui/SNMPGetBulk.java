package com.intraway.technology.jmeter.plugin.snmp.gui;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intraway.technology.jmeter.plugin.snmp.sampler.SnmpGetBulkSampler;

public class SNMPGetBulk extends AbstractGUI {
  private static final long serialVersionUID = 3197090412869386190L;
  public static final String LABEL = "Snmp get bulk sampler";

  private static final Logger logger = LoggerFactory.getLogger(SNMPGetBulk.class);

  private JSpinner REPETITIONS;

  @Override
  public void configure(TestElement element) {
    logger.info("SNMPGetBulk.configure");
    super.configure(element);
    REPETITIONS.setValue(Integer.valueOf(element.getPropertyAsString(SnmpGetBulkSampler.REPETITIONS)));
  }

  @Override
  public TestElement createTestElement() {
    logger.info("SNMPGetBulk.createTestElement");
    SnmpGetBulkSampler sampler = new SnmpGetBulkSampler();
    modifyTestElement(sampler);
    sampler.setComment("Snmp get bulk sampler");
    sampler.setProperty(SnmpGetBulkSampler.COMMUNITY, COMMUNITY.getText());
    sampler.setProperty(SnmpGetBulkSampler.OID, OID.getText());
    //sampler.setProperty(SnmpGetBulkSampler.OIDS, OIDS.getText());
    sampler.setProperty(SnmpGetBulkSampler.PORT, PORT.getText());
    sampler.setProperty(SnmpGetBulkSampler.HOST, HOST.getText());
    sampler.setProperty(SnmpGetBulkSampler.TIMEOUT, TIMEOUT.getText());
    sampler.setProperty(SnmpGetBulkSampler.REPETITIONS, REPETITIONS.getValue().toString());
    sampler.setProperty(SnmpGetBulkSampler.RETRIES, RETRIES.getValue().toString());
    sampler.setProperty(SnmpGetBulkSampler.SNMPVERSION, SNMPVERSION.getSelectedItem().toString());
    sampler.setProperty(SnmpGetBulkSampler.SNMPVERSIONSELECTED, SNMPVERSION.getSelectedIndex());
    return sampler;
  }

  public void modifyTestElement(TestElement sampler) {
    logger.info("SNMPGetBulk.modifyTestElement");
    super.configureTestElement(sampler);
    if (sampler instanceof SnmpGetBulkSampler) {
      sampler.setProperty(SnmpGetBulkSampler.COMMUNITY, COMMUNITY.getText());
      sampler.setProperty(SnmpGetBulkSampler.OID, OID.getText());
      sampler.setProperty(SnmpGetBulkSampler.PORT, PORT.getText());
      sampler.setProperty(SnmpGetBulkSampler.HOST, HOST.getText());
      sampler.setProperty(SnmpGetBulkSampler.TIMEOUT, TIMEOUT.getText());
      sampler.setProperty(SnmpGetBulkSampler.REPETITIONS, REPETITIONS.getValue().toString());
      sampler.setProperty(SnmpGetBulkSampler.RETRIES, RETRIES.getValue().toString());
      sampler.setProperty(SnmpGetBulkSampler.SNMPVERSION, SNMPVERSION.getSelectedItem().toString());
      sampler.setProperty(SnmpGetBulkSampler.SNMPVERSIONSELECTED, SNMPVERSION.getSelectedIndex());
    }
  }

  public void initFields() {
    logger.info("SNMPGetBulk.initFields");
    REPETITIONS.setValue(10);
    PORT.setText("161");
  }

  public void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints) {
    logger.info("SNMPGetBulk.init");
    addToPanel(mainPanel, labelConstraints, 0, 15, new JLabel("Max Repetitions: ", JLabel.RIGHT));
    SpinnerNumberModel model = new SpinnerNumberModel(10, 1, 100, 1);
    addToPanel(mainPanel, editConstraints, 1, 15, REPETITIONS = new JSpinner(model));
  }

  @Override
  public String getLable() {
    return LABEL;
  }
}
