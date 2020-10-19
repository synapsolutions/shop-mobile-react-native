package com.synappayshop.bridge.uimanagers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.synappayshop.bridge.views.SynapPayView;

import java.util.Map;

public class SynapPayViewManager extends SimpleViewManager<SynapPayView> {
    private static final String RCT_MODULE_NAME = "SynapPayView";

    public static final int COMMAND_CREATE = 1;//"create";
    public static final int COMMAND_CREATE_WITH_BANKS = 2;//"createWithBanks";
    public static final int COMMAND_CONFIGURE = 3;//"configure";
    public static final int COMMAND_PAY = 4;//"pay";

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
        return MapBuilder.of(
                "create",
                COMMAND_CREATE,
                "createWithBanks",
                COMMAND_CREATE_WITH_BANKS,
                "configure",
                COMMAND_CONFIGURE,
                "pay",
                COMMAND_PAY
        );
    }

    @Override
    public void receiveCommand(
            @NonNull SynapPayView view,
            int commandId,
            @Nullable ReadableArray args) {
        Assertions.assertNotNull(view);

        switch (commandId) {
            case COMMAND_CREATE:
                this.create(view);
                break;
            case COMMAND_CONFIGURE:
                this.configure(view);
                break;
            case COMMAND_PAY:
                this.pay(view);
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandId,
                        getClass().getSimpleName()));
        }
    }

    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "onCreateEnd",
                MapBuilder.of("registrationName", "onCreateEnd"),
                "onError",
                MapBuilder.of("registrationName", "onError"),
                "onConfigureEnd",
                MapBuilder.of("registrationName", "onConfigureEnd"),
                "onPaySuccess",
                MapBuilder.of("registrationName", "onPaySuccess"),
                "onPayFailed",
                MapBuilder.of("registrationName", "onPayFailed")
        );
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
