package com.intraway.technology.jmeter.plugin.snmp.client.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Response {

  private final List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

  public Response() {
  }

  public Response(HashMap<String, String> item) {
    data.add(item);
  }

  public void add(HashMap<String, String> item) {
    data.add(item);
  }

  public List<HashMap<String, String>> get() {
    return data;
  }
  
  public boolean isEmpty() {
    if (data!=null && !data.isEmpty()) {
      return false;
    }
    return true;
  }

}
