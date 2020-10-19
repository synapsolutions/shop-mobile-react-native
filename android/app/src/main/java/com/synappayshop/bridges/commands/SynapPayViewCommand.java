package com.synappayshop.bridge.commands;

public class SynapPayViewCommand {

    public static final SynapPayViewCommand CREATE = new SynapPayViewCommand(1, "create");
    public static final SynapPayViewCommand CREATE_WITH_BANKS = new SynapPayViewCommand(2, "createWithBanks");
    public static final SynapPayViewCommand CONFIGURE = new SynapPayViewCommand(3, "configure");
    public static final SynapPayViewCommand PAY = new SynapPayViewCommand(4, "pay");

    private int id;
    private String name;

    public SynapPayViewCommand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
