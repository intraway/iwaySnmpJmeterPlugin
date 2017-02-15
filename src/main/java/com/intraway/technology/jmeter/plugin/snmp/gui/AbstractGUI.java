package com.intraway.technology.jmeter.plugin.snmp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intraway.technology.jmeter.plugin.snmp.sampler.AbstractSnmpSampler;

public abstract class AbstractGUI extends AbstractSamplerGui {
  private static final long serialVersionUID = -1372154378991423872L;
  private static final String WIKI = "http://gitlab.intraway.com/Testing/snmpJmeterPlugin/wikis/home";
  private static final Logger logger = LoggerFactory.getLogger(AbstractGUI.class);

  protected final JComboBox SNMPVERSION;

  protected final JTextField COMMUNITY;
  protected final JTextField OID;

  protected final JSpinner RETRIES;
  protected final JTextField TIMEOUT;

  protected final JTextField PORT;
  protected final JTextField HOST;

  public AbstractGUI() {
    logger.info("AbstractGUI.constructor");
    setLayout(new BorderLayout(0, 5));
    setBorder(makeBorder());
    add(addHelpLinkToPanel(makeTitlePanel(), WIKI), BorderLayout.NORTH);
    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints labelConstraints = new GridBagConstraints();
    labelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;

    GridBagConstraints editConstraints = new GridBagConstraints();
    editConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
    editConstraints.weightx = 1.0;
    editConstraints.fill = GridBagConstraints.HORIZONTAL;

    addToPanel(mainPanel, labelConstraints, 0, 1, new JLabel("Host: ", JLabel.RIGHT));
    addToPanel(mainPanel, editConstraints, 1, 1, HOST = new JTextField());
    addToPanel(mainPanel, labelConstraints, 2, 1, new JLabel("Port: ", JLabel.RIGHT));
    addToPanel(mainPanel, editConstraints, 3, 1, PORT = new JTextField());

    addToPanel(mainPanel, labelConstraints, 0, 10, new JLabel("SNMP Protocol Version: ", JLabel.RIGHT));
    addToPanel(mainPanel, editConstraints, 1, 10, SNMPVERSION = new JComboBox(AbstractSnmpSampler.snmpProtocolVersions.toArray()));

    addToPanel(mainPanel, labelConstraints, 0, 11, new JLabel("Community: ", JLabel.RIGHT));
    addToPanel(mainPanel, editConstraints, 1, 11, COMMUNITY = new JTextField());
    addToPanel(mainPanel, labelConstraints, 0, 12, new JLabel("Oid: ", JLabel.RIGHT));
    addToPanel(mainPanel, editConstraints, 1, 12, OID = new JTextField());

    addToPanel(mainPanel, labelConstraints, 0, 13, new JLabel("Retries: ", JLabel.RIGHT));
    SpinnerNumberModel model = new SpinnerNumberModel(3, 0, 10, 1);
    addToPanel(mainPanel, editConstraints, 1, 13, RETRIES = new JSpinner(model));

    addToPanel(mainPanel, labelConstraints, 0, 14, new JLabel("Timeout: ", JLabel.RIGHT));
    addToPanel(mainPanel, editConstraints, 1, 14, TIMEOUT = new JTextField());

    init(mainPanel, labelConstraints, editConstraints);
    JPanel container = new JPanel(new BorderLayout());
    container.add(mainPanel, BorderLayout.NORTH);
    add(container, BorderLayout.CENTER);
  }

  @Override
  public void clearGui() {
    logger.info("AbstractGUI.clearGui");
    super.clearGui();
    HOST.setText("");
    PORT.setText("161");
    TIMEOUT.setText("1000");
    RETRIES.setValue(3);
    OID.setText("");
    COMMUNITY.setText("");
    SNMPVERSION.setSelectedIndex(1);

    initFields();
  }

  @Override
  public void configure(TestElement element) {
    logger.info("AbstractGUI.configure");
    super.configure(element);
    logger.info("port: " + element.getPropertyAsString(AbstractSnmpSampler.PORT));
    PORT.setText(element.getPropertyAsString(AbstractSnmpSampler.PORT));
    logger.info("host: " + element.getPropertyAsString(AbstractSnmpSampler.HOST));
    HOST.setText(element.getPropertyAsString(AbstractSnmpSampler.HOST));
    logger.info("timeout: " + element.getPropertyAsString(AbstractSnmpSampler.TIMEOUT));
    TIMEOUT.setText(element.getPropertyAsString(AbstractSnmpSampler.TIMEOUT));
    logger.info("retries: " + element.getPropertyAsString(AbstractSnmpSampler.RETRIES));
    RETRIES.setValue(Integer.valueOf(element.getPropertyAsString(AbstractSnmpSampler.RETRIES)));
    logger.info("oit: " + element.getPropertyAsString(AbstractSnmpSampler.OID));
    OID.setText(element.getPropertyAsString(AbstractSnmpSampler.OID));
    logger.info("community: " + element.getPropertyAsString(AbstractSnmpSampler.COMMUNITY));
    COMMUNITY.setText(element.getPropertyAsString(AbstractSnmpSampler.COMMUNITY));
    logger.info("snmpversionSelected: " + element.getPropertyAsInt(AbstractSnmpSampler.SNMPVERSIONSELECTED));
    SNMPVERSION.setSelectedIndex(element.getPropertyAsInt(AbstractSnmpSampler.SNMPVERSIONSELECTED));

  }

//  protected void configureTestElement(TestElement mc) {
//    super.configureTestElement(mc);
//  }

  public static Component addHelpLinkToPanel(Container panel, String helpPage) {
    if (!java.awt.Desktop.isDesktopSupported()){
      return panel;
    }

    JLabel icon = new JLabel();
    JLabel link = new JLabel("Help on this plugin");
    link.setForeground(Color.blue);
    link.setFont(link.getFont().deriveFont(Font.PLAIN));
    link.setCursor(new Cursor(Cursor.HAND_CURSOR));
    Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue);
    link.setBorder(border);

    JLabel version = new JLabel("v" + 123);
    version.setFont(version.getFont().deriveFont(Font.PLAIN).deriveFont(11F));
    version.setForeground(Color.GRAY);

    JPanel panelLink = new JPanel(new GridBagLayout());

    GridBagConstraints gridBagConstraints;

    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new Insets(0, 1, 0, 0);
    panelLink.add(icon, gridBagConstraints);

    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new Insets(0, 2, 3, 0);
    panelLink.add(link, gridBagConstraints);

    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new Insets(0, 0, 0, 4);
    panelLink.add(version, gridBagConstraints);
    panel.add(panelLink);
    return panel;
  }

  public void addToPanel(JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
    constraints.gridx = col;
    constraints.gridy = row;
    panel.add(component, constraints);
  }

  @Override
  public String getStaticLabel() {
    return getLable();
  }

  @Override
  public String getLabelResource() {
    return getLable();
  }

  public abstract String getLable();

  public abstract void initFields();

  public abstract void init(JPanel mainPanel, GridBagConstraints labelConstraints, GridBagConstraints editConstraints);
}
