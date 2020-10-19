package com.synappayshop.bridges.events;

import java.util.ArrayList;
import java.util.List;

public enum SynapPayViewEvent {

    CREATE_STARTED("onCreateStarted","message"),
    CREATE_COMPLETED("onCreateCompleted","message"),

    CONFIGURE_STARTED("onConfigureStarted","message"),
    CONFIGURE_COMPLETED("onConfigureCompleted","message"),

    PAY_STARTED("onPayStarted","message"),
    PAY_SUCCESS("onPaySuccess","response"),
    PAY_FAIL("onPayFail","response"),
    PAY_COMPLETED("onPayCompleted","message"),

    ERROR("onError","message");

    private String name;
    private List<String> parameters;

    SynapPayViewEvent(String name, String... parameters) {
        this.name = name;
        this.parameters = new ArrayList<String>();
        for (String parameter : parameters) {
            this.parameters.add(parameter);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

}
