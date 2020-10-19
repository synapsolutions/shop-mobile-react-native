package com.synappayshop.bridges.models;

import java.util.ArrayList;
import java.util.List;

public class EventModel {
    private String name;
    private List<String> parameters;

    public EventModel(String name, String... parameters) {
        this.name = name;
        this.parameters = new ArrayList<String>();
        for (String parameter : parameters) {
            this.parameters.add(parameter);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
