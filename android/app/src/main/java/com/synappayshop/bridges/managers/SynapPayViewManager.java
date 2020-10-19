package com.synappayshop.bridges.managers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.synappayshop.bridges.commands.SynapPayViewCommand;
import com.synappayshop.bridges.events.SynapPayViewEvent;
import com.synappayshop.bridges.packages.SynapPayViewPackage;
import com.synappayshop.bridges.views.SynapPayView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynapPayViewManager extends SimpleViewManager<SynapPayView> {
    private static final String RCT_MODULE_NAME = "SynapPayView";
    private static final List<SynapPayViewCommand> COMMANDS = Arrays.asList(
            SynapPayViewCommand.CREATE,
            SynapPayViewCommand.CREATE_WITH_BANKS,
            SynapPayViewCommand.CONFIGURE,
            SynapPayViewCommand.PAY
    );
    private static final List<SynapPayViewEvent> EVENTS = Arrays.asList(
            SynapPayViewEvent.CREATE_STARTED,
            SynapPayViewEvent.CREATE_COMPLETED,
            SynapPayViewEvent.CONFIGURE_STARTED,
            SynapPayViewEvent.CONFIGURE_COMPLETED,
            SynapPayViewEvent.PAY_STARTED,
            SynapPayViewEvent.PAY_SUCCESS,
            SynapPayViewEvent.PAY_FAIL,
            SynapPayViewEvent.PAY_COMPLETED,
            SynapPayViewEvent.ERROR
    );

    @NonNull
    @Override
    public String getName() {
        return RCT_MODULE_NAME;
    }

    @NonNull
    @Override
    protected SynapPayView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new SynapPayView(reactContext);
    }

    @ReactProp(name = "themeName")
    public void setThemeName(SynapPayView synapPayView, String themeName) {
        synapPayView.setThemeName(themeName);
    }

    @ReactProp(name = "environmentName")
    public void setEnvironmentName(SynapPayView synapPayView, String environmentName) {
        synapPayView.setEnvironmentName(environmentName);
    }

    @ReactProp(name = "identifier")
    public void setIdentifier(SynapPayView synapPayView, String identifier) {
        synapPayView.setIdentifier(identifier);
    }

    @ReactProp(name = "onBehalf")
    public void setOnBehalf(SynapPayView synapPayView, String onBehalf) {
        synapPayView.setOnBehalf(onBehalf);
    }

    @ReactProp(name = "signature")
    public void setSignature(SynapPayView synapPayView, String signature) {
        synapPayView.setSignature(signature);
    }

    @ReactProp(name = "transaction")
    public void setTransaction(SynapPayView synapPayView, String transaction) {
        synapPayView.setTransaction(transaction);
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        Map<String, Integer> commandsMap = new HashMap<>();
        for (SynapPayViewCommand command : COMMANDS) {
            commandsMap.put(command.getName(), command.getId());
        }
        return commandsMap;
    }

    @Override
    public void receiveCommand(
            @NonNull SynapPayView view,
            int commandId,
            @Nullable ReadableArray args) {
        Assertions.assertNotNull(view);
        SynapPayViewCommand calledCommand = null;
        for (SynapPayViewCommand command : COMMANDS) {
            if (command.getId() == commandId) {
                calledCommand = command;
                break;
            }
        }
        if (calledCommand == null) {
            throw new IllegalArgumentException(String.format(
                    "Unsupported command %d received by %s.",
                    commandId,
                    getClass().getSimpleName()));
        }

        switch (calledCommand) {
            case CREATE:
                this.create(view);
                break;
            case CONFIGURE:
                this.configure(view);
                break;
            case PAY:
                this.pay(view);
                break;
        }
    }

    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        Map commandsMap = new HashMap<>();
        for (SynapPayViewEvent event : EVENTS) {
            commandsMap.put(event.getName(), MapBuilder.of("registrationName", event.getName()));
        }
        return commandsMap;
    }

    private void create(SynapPayView synapPayView) {
        synapPayView.create();
    }

    private void configure(SynapPayView synapPayView) {
        synapPayView.configure();
    }


    private void pay(SynapPayView synapPayView) {
        synapPayView.pay();
    }
}
