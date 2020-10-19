package com.synappayshop.bridge.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.synap.pay.SynapPayButton;
import com.synap.pay.handler.payment.SynapAuthorizeHandler;
import com.synap.pay.model.payment.SynapTransaction;
import com.synap.pay.model.payment.response.SynapAuthorizeResponse;
import com.synap.pay.model.security.SynapAuthenticator;
import com.synap.pay.theming.SynapDarkTheme;
import com.synap.pay.theming.SynapLightTheme;
import com.synap.pay.theming.SynapTheme;
import com.synap.pay.util.json.JSONDecoder;
import com.synap.pay.util.json.JSONEncoder;

public class SynapPayView extends ConstraintLayout {

    private SynapPayButton payButton;
    private String themeName;
    private String environmentName;
    private SynapTransaction transaction;
    private String identifier;
    private String onBehalf;
    private String signature;

    public SynapPayView(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setOnBehalf(String onBehalf) {
        this.onBehalf = onBehalf;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setTransaction(String transaction) {
        this.transaction = JSONDecoder.decode(SynapTransaction.class, transaction);
    }

    public void create() {
        String message = "OK";
        try {
            SynapTheme theme = new SynapLightTheme();
            if ("dark".equals(this.themeName)) {
                theme = new SynapDarkTheme();
            }
            SynapPayButton.setTheme(theme);
            if (this.environmentName != null) {
                switch (this.environmentName) {
                    case "SANDBOX":
                        SynapPayButton.setEnvironment(SynapPayButton.Environment.SANDBOX);
                        break;
                    case "DEVELOPMENT":
                        SynapPayButton.setEnvironment(SynapPayButton.Environment.DEVELOPMENT);
                        break;
                    case "PRODUCTION":
                        SynapPayButton.setEnvironment(SynapPayButton.Environment.PRODUCTION);
                        break;
                    case "LOCAL":
                        SynapPayButton.setEnvironment(SynapPayButton.Environment.LOCAL);
                        break;
                }
            }
            this.payButton = SynapPayButton.create(this);
            this.refreshLayout(this);
            this.dispatchOnCreateEnd(message);
        } catch (Exception e) {
            this.dispatchOnError(e.getMessage());
        }
    }

    public void configure() {
        String message = "OK";
        try {
            if (this.identifier == null) {
                throw new RuntimeException("[identifier] es requerido");
            }
            if (this.signature == null) {
                throw new RuntimeException("[signature] es requerido");
            }
            if (this.transaction == null) {
                throw new RuntimeException("[transaction] es requerido");
            }
            SynapAuthenticator authenticator = new SynapAuthenticator();
            authenticator.setIdentifier(this.identifier);
            authenticator.setOnBehalf(this.onBehalf);
            authenticator.setSignature(this.signature);
            this.refreshLayout(this);
            this.payButton.configure(
                    authenticator,
                    this.transaction,
                    new SynapAuthorizeHandler() {
                        @Override
                        public void success(SynapAuthorizeResponse response) {
                            String responseString = JSONEncoder.encode(response);
                            dispatchOnPaySuccess(responseString);
                        }

                        @Override
                        public void failed(SynapAuthorizeResponse response) {
                            String responseString = JSONEncoder.encode(response);
                            dispatchOnPayFailed(responseString);
                        }
                    });
            this.refreshLayout(this);
            this.dispatchOnConfigureEnd(message);
        } catch (Exception e) {
            this.dispatchOnError(e.getMessage());
        }
    }

    private void refreshLayout(View view) {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public void pay() {
        this.payButton.pay();
        this.refreshLayout(this);
    }


    private void dispatchOnCreateEnd(String message) {
        this.notifyEvent("onCreateEnd", message);
    }

    private void dispatchOnError(String errorMessage) {
        this.notifyEvent("onError", errorMessage);
    }

    private void dispatchOnConfigureEnd(String message) {
        this.notifyEvent("onConfigureEnd", message);
    }

    private void dispatchOnPaySuccess(String message) {
        this.notifyEvent("onPaySuccess", message);
    }

    private void dispatchOnPayFailed(String message) {
        this.notifyEvent("onPayFailed", message);
    }

    private void notifyEvent(String eventName, String message) {
        WritableMap event = Arguments.createMap();
        event.putString("message", message);
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                eventName,
                event);
    }
}
