package com.synappayshop.bridges.packages;


import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.synappayshop.bridges.managers.SynapPayViewManager;

import java.util.Collections;
import java.util.List;

public class SynapPayViewPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.<ViewManager>singletonList(
                new SynapPayViewManager()
        );
    }
}
