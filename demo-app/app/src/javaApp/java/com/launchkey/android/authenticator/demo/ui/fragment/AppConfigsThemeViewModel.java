package com.launchkey.android.authenticator.demo.ui.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;
import com.launchkey.android.authenticator.sdk.ui.theme.AppBarUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.AuthContentBgUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.AuthMethodsBusyIcons;
import com.launchkey.android.authenticator.sdk.ui.theme.AuthMethodsSecurityIcons;
import com.launchkey.android.authenticator.sdk.ui.theme.AuthenticatorTheme;
import com.launchkey.android.authenticator.sdk.ui.theme.BackgroundOverlayUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.BackgroundUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.ButtonUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.CircleCodeUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.DenialOptionsUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.EditTextUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.ExpirationTimerProp;
import com.launchkey.android.authenticator.sdk.ui.theme.HelpMenuUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.ListHeadersUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.NegativeButtonUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.PinCodeUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.SettingsHeadersUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.TarbNegativeUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.TarbUiProp;
import com.launchkey.android.authenticator.sdk.ui.theme.UiProp;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AppConfigsThemeViewModel extends ViewModel {
    // App Bar
    private static final String APP_BAR_BACKGROUND = "AppBarBackground";
    private static final String APP_BAR_ITEM_COLOR = "AppBarItemColor";

    // Background
    private static final String BACKGROUND = "Background";

    // Background Overlay
    private static final String BACKGROUND_OVERLAY = "BackgroundOverlay";

    // Button
    private static final String BUTTON = "Button";
    private static final String BUTTON_TEXT = "ButtonText";

    // Button Negative
    private static final String BUTTON_NEGATIVE = "ButtonNegative";
    private static final String BUTTON_NEGATIVE_TEXT = "ButtonNegativeText";

    // List Headers
    private static final String LIST_HEADER_VISIBILITY = "ListHeaderVisibility";
    private static final String LIST_HEADER_COLOR_BACKGROUND = "ListHeaderColorBackground";
    private static final String LIST_HEADER_COLOR_TEXT = "ListHeaderColorText";

    // List Headers
    private static final String AUTH_METHODS_SECURITY_VISIBILITY = "AuthMethodsSecurityVisibility";
    private static final String AUTH_METHODS_SECURITY_PIN_CODE_ICON = "AuthMethodsSecurityPINCodeIcon";
    private static final String AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON = "AuthMethodsSecurityCircleCodeIcon";
    private static final String AUTH_METHODS_SECURITY_GEOFENCES_ICON = "AuthMethodsSecurityGeofencesIcon";
    private static final String AUTH_METHODS_SECURITY_WEARABLES_ICON = "AuthMethodsSecurityWearablesIcon";
    private static final String AUTH_METHODS_SECURITY_BIOMETRIC_ICON = "AuthMethodsSecurityBiometricIcon";

    // Help Menu Items
    private static final String HELP_MENU_ITEMS_VISIBILITY = "HelpMenuItemsVisibility";

    // PIN Code
    private static final String PIN_CODE_BACKGROUND = "PINCodeBackground";
    private static final String PIN_CODE_TEXT_COLOR = "PINCodeTextColor";

    // Circle Code
    private static final String CIRCLE_CODE_HIGHLIGHT_COLOR = "CircleCodeHighlightColor";
    private static final String CIRCLE_CODE_TICK_COLOR = "CircleCodeTickColor";

    // Factors Busy Icons
    private static final String AUTH_METHODS_BUSY_GEOFENCES_ICON = "AuthMethodBusyGeofencesIcon";
    private static final String AUTH_METHODS_BUSY_WEARABLES_ICON = "AuthMethodBusyWearablesIcon";
    private static final String AUTH_METHODS_BUSY_BIOMETRIC_ICON = "AuthMethodBusyBiometricIcon";

    // Settings Headers
    private static final String SETTINGS_HEADERS_BACKGROUND_COLOR = "SettingsHeadersBackgroundColor";
    private static final String SETTINGS_HEADERS_TEXT_COLOR = "SettingsHeadersTextColor";

    // Edit Text
    private static final String EDIT_TEXT_HINT_COLOR = "EditTextHintColor";
    private static final String EDIT_TEXT_TEXT_COLOR = "EditTextTextColor";

    // Expiration Timer
    private static final String EXPIRATION_TIMER_BACKGROUND_COLOR = "ExpirationTimerBackgroundColor";
    private static final String EXPIRATION_TIMER_FILL_COLOR = "ExpirationTimerFillColor";
    private static final String EXPIRATION_TIMER_WARNING_COLOR = "ExpirationTimerWarningColor";

    // Denial Reasons
    private static final String DENIAL_REASONS_OPTION_NORMAL = "DenialReasonsOptionNormal";
    private static final String DENIAL_REASONS_OPTION_CHECKED = "DenialReasonsOptionChecked";

    // Auth Response Button
    private static final String AUTH_RESPONSE_BUTTON_BACKGROUND = "AuthResponseButtonBackground";
    private static final String AUTH_RESPONSE_BUTTON_TEXT_COLOR = "AuthResponseButtonTextColor";
    private static final String AUTH_RESPONSE_BUTTON_FILL_COLOR = "AuthResponseButtonFillColor";

    // Negative Auth Response Button
    private static final String NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND = "NegativeAuthResponseButtonBackground";
    private static final String NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR = "NegativeAuthResponseButtonTextColor";
    private static final String NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR = "NegativeAuthResponseButtonFillColor";

    // Auth Response Authorized Color
    private static final String AUTH_RESPONSE_AUTHORIZED_COLOR = "AuthResponseAuthorizedColor";

    // Auth Response Denied Color
    private static final String AUTH_RESPONSE_DENIED_COLOR = "AuthResponseDeniedColor";

    // Auth Response Failed Color
    private static final String AUTH_RESPONSE_FAILED_COLOR = "AuthResponseFailedColor";

    // Auth Content View Background
    private static final String AUTH_CONTENT_VIEW_BACKGROUND = "AuthContentViewBackground";

    @NonNull
    private final SavedStateHandle savedStateHandle;

    /*
        App Bar
    */
    @ColorInt
    private int appBarBackground;
    @ColorInt
    public int getAppBarBackground() { return appBarBackground; }
    public void setAppBarBackground(@ColorInt final int appBarBackground) {
        savedStateHandle.set(APP_BAR_BACKGROUND, this.appBarBackground);
        this.appBarBackground = appBarBackground;
    }
    @ColorInt
    private int appBarItemColor;
    @ColorInt
    public int getAppBarItemColor() { return appBarItemColor; }
    public void setAppBarItemColor(@ColorInt final int appBarItemColor) {
        savedStateHandle.set(APP_BAR_ITEM_COLOR, this.appBarItemColor);
        this.appBarItemColor = appBarItemColor;
    }

    /*
        Background
    */
    private Drawable background;
    public Drawable getBackground() { return background; }
    public void setBackground(@ColorInt final int background) {
        savedStateHandle.set(BACKGROUND, background);
        this.background = new ColorDrawable(background);
    }

    /*
        Background Overlay
    */
    @ColorInt
    private int backgroundOverlay;
    @ColorInt
    public int getBackgroundOverlay() { return backgroundOverlay; }
    public void setBackgroundOverlay(@ColorInt final int backgroundOverlay) {
        savedStateHandle.set(BACKGROUND_OVERLAY, backgroundOverlay);
        this.backgroundOverlay = backgroundOverlay;
    }

    /*
        Button
    */
    private Drawable button;
    public Drawable getButton() { return button; }
    public void setButton(@ColorInt final int button) {
        savedStateHandle.set(BUTTON, button);
        this.button = new ColorDrawable(button);
    }
    @ColorInt
    private int buttonText;
    @ColorInt
    public int getButtonText() { return buttonText; }
    public void setButtonText(@ColorInt final int buttonText) {
        savedStateHandle.set(BUTTON, buttonText);
        this.buttonText = buttonText;
    }

    /*
        Button Negative
    */
    private Drawable buttonNegative;
    public Drawable getButtonNegative() { return buttonNegative; }
    public void setButtonNegative(@ColorInt final int buttonNegative) {
        savedStateHandle.set(BUTTON_NEGATIVE, buttonNegative);
        this.buttonNegative = new ColorDrawable(buttonNegative);
    }
    @ColorInt
    private int buttonNegativeText;
    @ColorInt
    public int getButtonNegativeText() { return buttonNegativeText; }
    public void setButtonNegativeText(@ColorInt final int buttonNegativeText) {
        savedStateHandle.set(BUTTON, buttonNegativeText);
        this.buttonNegativeText = buttonNegativeText;
    }

    /*
        List Headers
    */
    private int listHeadersVisibility;
    public int getListHeadersVisibility() { return listHeadersVisibility; }
    public void setListHeadersVisibility(int listHeadersVisibility) {
        savedStateHandle.set(LIST_HEADER_VISIBILITY, listHeadersVisibility);
        this.listHeadersVisibility = listHeadersVisibility;
    }
    @ColorInt
    private int listHeadersColorBackground;
    @ColorInt
    public int getListHeadersColorBackground() { return listHeadersColorBackground; }
    public void setListHeadersColorBackground(@ColorInt final int listHeadersColorBackground) {
        savedStateHandle.set(LIST_HEADER_COLOR_BACKGROUND, listHeadersColorBackground);
        this.listHeadersColorBackground = listHeadersColorBackground;
    }
    @ColorInt
    private int listHeadersColorText;
    @ColorInt
    public int getListHeadersColorText() { return listHeadersColorText; }
    public void setListHeadersColorText(@ColorInt final int listHeadersColorText) {
        savedStateHandle.set(LIST_HEADER_COLOR_TEXT, listHeadersColorText);
        this.listHeadersColorText = listHeadersColorText;
    }

    /*
        List Headers
    */
    private int factorsSecurityIconsVisibility;
    public int getFactorsSecurityIconsVisibility() { return factorsSecurityIconsVisibility; }
    public void setFactorsSecurityIconsVisibility(int factorsSecurityIconsVisibility) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_VISIBILITY, factorsSecurityIconsVisibility);
        this.factorsSecurityIconsVisibility = factorsSecurityIconsVisibility;
    }
    private Drawable factorsSecurityIconsPINCodeIcon;
    public Drawable getFactorsSecurityIconsPINCodeIcon() { return factorsSecurityIconsPINCodeIcon; }
    public void setFactorsSecurityIconsPINCodeIcon(@ColorInt final int factorsSecurityIconsPINCodeIcon) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_PIN_CODE_ICON, factorsSecurityIconsPINCodeIcon);
        this.factorsSecurityIconsPINCodeIcon = new ColorDrawable(factorsSecurityIconsPINCodeIcon);
    }
    private Drawable factorsSecurityIconsCircleCodeIcon;
    public Drawable getFactorsSecurityIconsCircleCodeIcon() { return factorsSecurityIconsCircleCodeIcon; }
    public void setFactorsSecurityIconsCircleCodeIcon(@ColorInt final int factorsSecurityIconsCircleCodeIcon) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON, factorsSecurityIconsCircleCodeIcon);
        this.factorsSecurityIconsCircleCodeIcon = new ColorDrawable(factorsSecurityIconsCircleCodeIcon);
    }
    private Drawable factorsSecurityIconsGeofencesIcon;
    public Drawable getFactorsSecurityIconsGeofencesIcon() { return factorsSecurityIconsGeofencesIcon; }
    public void setFactorsSecurityIconsGeofencesIcon(@ColorInt final int factorsSecurityIconsGeofencesIcon) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_GEOFENCES_ICON, factorsSecurityIconsGeofencesIcon);
        this.factorsSecurityIconsGeofencesIcon = new ColorDrawable(factorsSecurityIconsGeofencesIcon);
    }
    private Drawable factorsSecurityIconsWearablesIcon;
    public Drawable getFactorsSecurityIconsWearablesIcon() { return factorsSecurityIconsWearablesIcon; }
    public void setFactorsSecurityIconsWearablesIcon(@ColorInt final int factorsSecurityIconsWearablesIcon) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_WEARABLES_ICON, factorsSecurityIconsWearablesIcon);
        this.factorsSecurityIconsWearablesIcon = new ColorDrawable(factorsSecurityIconsWearablesIcon);
    }
    private Drawable factorsSecurityIconsBiometricIcon;
    public Drawable getFactorsSecurityIconsBiometricIcon() { return factorsSecurityIconsBiometricIcon; }
    public void setFactorsSecurityIconsBiometricIcon(@ColorInt final int factorsSecurityIconsBiometricIcon) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_BIOMETRIC_ICON, factorsSecurityIconsBiometricIcon);
        this.factorsSecurityIconsBiometricIcon = new ColorDrawable(factorsSecurityIconsBiometricIcon);
    }

    /*
        Help Menu Items
    */
    private int helpMenuItemsVisibility;
    public int getHelpMenuItemsVisibility() { return helpMenuItemsVisibility; }
    public void setHelpMenuItemsVisibility(int helpMenuItemsVisibility) {
        savedStateHandle.set(HELP_MENU_ITEMS_VISIBILITY, helpMenuItemsVisibility);
        this.helpMenuItemsVisibility = helpMenuItemsVisibility;
    }

    /*
        PIN Code
     */
    private Drawable pinCodeColorBackground;
    public Drawable getPINCodeColorBackground() { return pinCodeColorBackground; }
    public void setPINCodeColorBackground(@ColorInt final int pinCodeColorBackground) {
        savedStateHandle.set(PIN_CODE_BACKGROUND, pinCodeColorBackground);
        this.pinCodeColorBackground = new ColorDrawable(pinCodeColorBackground);
    }
    @ColorInt
    private int pinCodeColorText;
    @ColorInt
    public int getPINCodeColorText() { return pinCodeColorText; }
    public void setPINCodeColorText(@ColorInt final int pinCodeColorText) {
        savedStateHandle.set(PIN_CODE_TEXT_COLOR, pinCodeColorText);
        this.pinCodeColorText = pinCodeColorText;
    }

    /*
        Circle Code
     */
    @ColorInt
    private int circleCodeHighlightColor;
    @ColorInt
    public int getCircleCodeHighlightColor() { return circleCodeHighlightColor; }
    public void setCircleCodeHighlightColor(@ColorInt final int circleCodeHighlightColor) {
        savedStateHandle.set(CIRCLE_CODE_HIGHLIGHT_COLOR, circleCodeHighlightColor);
        this.circleCodeHighlightColor = circleCodeHighlightColor;
    }
    @ColorInt
    private int circleCodeTickColor;
    @ColorInt
    public int getCircleCodeTickColor() { return circleCodeTickColor; }
    public void setCircleCodeTickColor(@ColorInt final int circleCodeTickColor) {
        savedStateHandle.set(CIRCLE_CODE_TICK_COLOR, circleCodeTickColor);
        this.circleCodeTickColor = circleCodeTickColor;
    }

    /*
        Factors Busy Icons
    */
    private Drawable factorsBusyIconsGeofencingIcon;
    public Drawable getFactorsBusyIconsGeofencingIcon() { return factorsBusyIconsGeofencingIcon; }
    public void setFactorsBusyIconsGeofencingIcon(@ColorInt final int factorsBusyIconsGeofencingIcon) {
        savedStateHandle.set(AUTH_METHODS_BUSY_GEOFENCES_ICON, factorsBusyIconsGeofencingIcon);
        this.factorsBusyIconsGeofencingIcon = new ColorDrawable(factorsBusyIconsGeofencingIcon);
    }
    private Drawable factorsBusyIconsWearablesIcon;
    public Drawable getFactorsBusyIconsWearablesIcon() { return factorsBusyIconsWearablesIcon; }
    public void setFactorsBusyIconsWearablesIcon(@ColorInt final int factorsBusyIconsWearablesIcon) {
        savedStateHandle.set(AUTH_METHODS_BUSY_WEARABLES_ICON, factorsBusyIconsWearablesIcon);
        this.factorsBusyIconsWearablesIcon = new ColorDrawable(factorsBusyIconsWearablesIcon);
    }
    private Drawable factorsBusyIconsBiometricIcon;
    public Drawable getFactorsBusyIconsBiometricIcon() { return factorsBusyIconsBiometricIcon; }
    public void setFactorsBusyIconsBiometricIcon(@ColorInt final int factorsBusyIconsBiometricIcon) {
        savedStateHandle.set(AUTH_METHODS_BUSY_BIOMETRIC_ICON, factorsBusyIconsBiometricIcon);
        this.factorsBusyIconsBiometricIcon = new ColorDrawable(factorsBusyIconsBiometricIcon);
    }

    /*
        Settings Headers
    */
    private Drawable settingsHeadersBackground;
    public Drawable getSettingsHeadersBackground() { return settingsHeadersBackground; }
    public void setSettingsHeadersBackground(@ColorInt final int settingsHeadersBackground) {
        savedStateHandle.set(SETTINGS_HEADERS_BACKGROUND_COLOR, settingsHeadersBackground);
        this.settingsHeadersBackground = new ColorDrawable(settingsHeadersBackground);
    }
    @ColorInt
    private int settingsHeadersText;
    @ColorInt
    public int getSettingsHeadersText() { return settingsHeadersText; }
    public void setSettingsHeadersText(@ColorInt final int settingsHeadersText) {
        savedStateHandle.set(SETTINGS_HEADERS_TEXT_COLOR, settingsHeadersText);
        this.settingsHeadersText = settingsHeadersText;
    }

    /*
        Edit Text
    */
    @ColorInt
    private int editTextHint;
    @ColorInt
    public int getEditTextHint() { return editTextHint; }
    public void setEditTextHint(@ColorInt final int editTextHint) {
        savedStateHandle.set(EDIT_TEXT_HINT_COLOR, editTextHint);
        this.editTextHint = editTextHint;
    }
    @ColorInt
    private int editTextText;
    @ColorInt
    public int getEditTextText() { return editTextText; }
    public void setEditTextText(@ColorInt final int editTextText) {
        savedStateHandle.set(EDIT_TEXT_TEXT_COLOR, editTextText);
        this.editTextText = editTextText;
    }

    /*
        Expiration Timer
    */
    @ColorInt
    private int expirationTimerBackground;
    @ColorInt
    public int getExpirationTimerBackground() { return expirationTimerBackground; }
    public void setExpirationTimerBackground(@ColorInt final int expirationTimerBackground) {
        savedStateHandle.set(EXPIRATION_TIMER_BACKGROUND_COLOR, expirationTimerBackground);
        this.expirationTimerBackground = expirationTimerBackground;
    }
    @ColorInt
    private int expirationTimerFill;
    @ColorInt
    public int getExpirationTimerFill() { return expirationTimerFill; }
    public void setExpirationTimerFill(@ColorInt final int expirationTimerFill) {
        savedStateHandle.set(EXPIRATION_TIMER_FILL_COLOR, expirationTimerFill);
        this.expirationTimerFill = expirationTimerFill;
    }
    @ColorInt
    private int expirationTimerWarningColor;
    @ColorInt
    public int getExpirationTimerWarningColor() { return expirationTimerWarningColor; }
    public void setExpirationTimerWarningColor(@ColorInt final int expirationTimerWarningColor) {
        savedStateHandle.set(EXPIRATION_TIMER_WARNING_COLOR, expirationTimerWarningColor);
        this.expirationTimerWarningColor = expirationTimerWarningColor;
    }

    /*
        Denial Reasons
    */
    @ColorInt
    private int denialReasonsOptionNormal;
    @ColorInt
    public int getDenialReasonsOptionNormal() { return denialReasonsOptionNormal; }
    public void setDenialReasonsOptionNormal(@ColorInt final int denialReasonsOptionNormal) {
        savedStateHandle.set(DENIAL_REASONS_OPTION_NORMAL, denialReasonsOptionNormal);
        this.denialReasonsOptionNormal = denialReasonsOptionNormal;
    }
    @ColorInt
    private int denialReasonsOptionChecked;
    @ColorInt
    public int getDenialReasonsOptionChecked() { return denialReasonsOptionChecked; }
    public void setDenialReasonsOptionChecked(@ColorInt final int denialReasonsOptionChecked) {
        savedStateHandle.set(DENIAL_REASONS_OPTION_CHECKED, denialReasonsOptionChecked);
        this.denialReasonsOptionChecked = denialReasonsOptionChecked;
    }

    /*
        Auth Response button
    */
    private Drawable authResponseButtonBackground;
    public Drawable getAuthResponseButtonBackground() { return authResponseButtonBackground; }
    public void setAuthResponseButtonBackground(@ColorInt final int authResponseButtonBackground) {
        savedStateHandle.set(AUTH_RESPONSE_BUTTON_BACKGROUND, authResponseButtonBackground);
        this.authResponseButtonBackground = new ColorDrawable(authResponseButtonBackground);
    }
    @ColorInt
    private int authResponseButtonTextColor;
    @ColorInt
    public int getAuthResponseButtonTextColor() { return authResponseButtonTextColor; }
    public void setAuthResponseButtonTextColor(@ColorInt final int authResponseButtonTextColor) {
        savedStateHandle.set(AUTH_RESPONSE_BUTTON_TEXT_COLOR, authResponseButtonTextColor);
        this.authResponseButtonTextColor = authResponseButtonTextColor;
    }
    @ColorInt
    private int authResponseButtonFillColor;
    @ColorInt
    public int getAuthResponseButtonFillColor() { return authResponseButtonFillColor; }
    public void setAuthResponseButtonFillColor(@ColorInt final int authResponseButtonFillColor) {
        savedStateHandle.set(AUTH_RESPONSE_BUTTON_FILL_COLOR, authResponseButtonFillColor);
        this.authResponseButtonFillColor = authResponseButtonFillColor;
    }

    /*
        Negative Auth Response button
    */
    private Drawable negativeAuthResponseButtonBackground;
    public Drawable getNegativeAuthResponseButtonBackground() { return negativeAuthResponseButtonBackground; }
    public void setNegativeAuthResponseButtonBackground(@ColorInt final int negativeAuthResponseButtonBackground) {
        savedStateHandle.set(NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND, negativeAuthResponseButtonBackground);
        this.negativeAuthResponseButtonBackground = new ColorDrawable(negativeAuthResponseButtonBackground);
    }
    @ColorInt
    private int negativeAuthResponseButtonTextColor;
    @ColorInt
    public int getNegativeAuthResponseButtonTextColor() { return negativeAuthResponseButtonTextColor; }
    public void setNegativeAuthResponseButtonTextColor(@ColorInt final int negativeAuthResponseButtonTextColor) {
        savedStateHandle.set(NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR, negativeAuthResponseButtonTextColor);
        this.negativeAuthResponseButtonTextColor = negativeAuthResponseButtonTextColor;
    }
    @ColorInt
    private int negativeAuthResponseButtonFillColor;
    @ColorInt
    public int getNegativeAuthResponseButtonFillColor() { return negativeAuthResponseButtonFillColor; }
    public void setNegativeAuthResponseButtonFillColor(@ColorInt final int negativeAuthResponseButtonFillColor) {
        savedStateHandle.set(NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR, negativeAuthResponseButtonFillColor);
        this.negativeAuthResponseButtonFillColor = negativeAuthResponseButtonFillColor;
    }

    /*
        Auth Response Authorized Color
    */
    @ColorInt
    private int authResponseAuthorizedColor;
    @ColorInt
    public int getAuthResponseAuthorizedColor() { return authResponseAuthorizedColor; }
    public void setAuthResponseAuthorizedColor(@ColorInt final int authResponseAuthorizedColor) {
        savedStateHandle.set(AUTH_RESPONSE_AUTHORIZED_COLOR, authResponseAuthorizedColor);
        this.authResponseAuthorizedColor = authResponseAuthorizedColor;
    }

    /*
        Auth Response Denied Color
    */
    @ColorInt
    private int authResponseDeniedColor;
    @ColorInt
    public int getAuthResponseDeniedColor() { return authResponseDeniedColor; }
    public void setAuthResponseDeniedColor(@ColorInt final int authResponseDeniedColor) {
        savedStateHandle.set(AUTH_RESPONSE_DENIED_COLOR, authResponseDeniedColor);
        this.authResponseDeniedColor = authResponseDeniedColor;
    }

    /*
        Auth Response Failed Color
    */
    @ColorInt
    private int authResponseFailedColor;
    @ColorInt
    public int getAuthResponseFailedColor() { return authResponseFailedColor; }
    public void setAuthResponseFailedColor(@ColorInt final int authResponseFailedColor) {
        savedStateHandle.set(AUTH_RESPONSE_FAILED_COLOR, authResponseFailedColor);
        this.authResponseFailedColor = authResponseFailedColor;
    }

    /*
        Auth Content View Background
    */
    @ColorInt
    private int authContentViewBackground;
    @ColorInt
    public int getAuthContentViewBackground() { return authContentViewBackground; }
    public void setAuthContentViewBackground(@ColorInt final int authContentViewBackground) {
        savedStateHandle.set(AUTH_CONTENT_VIEW_BACKGROUND, authContentViewBackground);
        this.authContentViewBackground = authContentViewBackground;
    }

    public AppConfigsThemeViewModel(@NonNull final AuthenticatorUIManager authenticatorUIManager,
                                    @NonNull final Context context,
                                    @NonNull final SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;

        final AuthenticatorTheme authenticatorTheme = authenticatorUIManager.getConfig().themeObj();

        // App Bar
        final AppBarUiProp appBarUiProp = authenticatorTheme.getAppBar();
        if (savedStateHandle.get(APP_BAR_BACKGROUND) != null) this.appBarBackground = savedStateHandle.get(APP_BAR_BACKGROUND);
        else this.appBarBackground = appBarUiProp.getBackgroundColor();
        if (savedStateHandle.get(APP_BAR_ITEM_COLOR) != null) this.appBarItemColor = savedStateHandle.get(APP_BAR_ITEM_COLOR);
        else this.appBarItemColor = appBarUiProp.getColorItems();

        // Background
        final BackgroundUiProp backgroundUiProp = authenticatorTheme.getBg();
        if (savedStateHandle.get(BACKGROUND) != null) this.background = new ColorDrawable(savedStateHandle.get(BACKGROUND));
        else this.background = backgroundUiProp.getDrawableBg();

        // Background Overlay
        final BackgroundOverlayUiProp backgroundOverlayUiProp = authenticatorTheme.getBgOverlay();
        if (savedStateHandle.get(BACKGROUND_OVERLAY) != null) this.backgroundOverlay = savedStateHandle.get(BACKGROUND_OVERLAY);
        else this.backgroundOverlay = backgroundOverlayUiProp.getColorBgOverlay();

        // Button
        final ButtonUiProp buttonUiProp = authenticatorTheme.getButton();
        if (savedStateHandle.get(BUTTON) != null) this.button = new ColorDrawable(savedStateHandle.get(BUTTON));
        else this.button = buttonUiProp.getDrawableBg();
        if (savedStateHandle.get(BUTTON_TEXT) != null) this.buttonText = savedStateHandle.get(BUTTON_TEXT);
        else this.buttonText = buttonUiProp.getColorText();

        // Button Negative
        final NegativeButtonUiProp negativeButtonUiProp = authenticatorTheme.getButtonNegative();
        if (savedStateHandle.get(BUTTON_NEGATIVE) != null) this.buttonNegative = savedStateHandle.get(BUTTON_NEGATIVE);
        else this.buttonNegative = negativeButtonUiProp.getDrawableBg();
        if (savedStateHandle.get(BUTTON_NEGATIVE_TEXT) != null) this.buttonNegativeText = savedStateHandle.get(BUTTON_NEGATIVE_TEXT);
        else this.buttonNegativeText = negativeButtonUiProp.getColorText();

        // List Headers
        final ListHeadersUiProp listHeadersUiProp = authenticatorTheme.getListHeaders();
        if (savedStateHandle.get(LIST_HEADER_VISIBILITY) != null) this.listHeadersVisibility = savedStateHandle.get(LIST_HEADER_VISIBILITY);
        else this.listHeadersVisibility = listHeadersUiProp.getVisibility();
        if (savedStateHandle.get(LIST_HEADER_COLOR_BACKGROUND) != null) this.listHeadersColorBackground = savedStateHandle.get(LIST_HEADER_COLOR_BACKGROUND);
        else this.listHeadersColorBackground = listHeadersUiProp.getColorBg();
        if (savedStateHandle.get(LIST_HEADER_COLOR_TEXT) != null) this.listHeadersColorText = savedStateHandle.get(LIST_HEADER_COLOR_TEXT);
        else this.listHeadersColorText = listHeadersUiProp.getColorText();

        // Factors Security Icons
        final AuthMethodsSecurityIcons authMethodsSecurityIcons = authenticatorTheme.getMethodsSecurityIcons();
        if (savedStateHandle.get(AUTH_METHODS_SECURITY_VISIBILITY) != null) this.factorsSecurityIconsVisibility = savedStateHandle.get(AUTH_METHODS_SECURITY_VISIBILITY);
        else this.factorsSecurityIconsVisibility = authMethodsSecurityIcons.getIconVisibility();
        if (savedStateHandle.get(AUTH_METHODS_SECURITY_PIN_CODE_ICON) != null) this.factorsSecurityIconsPINCodeIcon = savedStateHandle.get(AUTH_METHODS_SECURITY_PIN_CODE_ICON);
        else this.factorsSecurityIconsPINCodeIcon = authMethodsSecurityIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsSecurityIcons.getIconPinCodeRes()) : authMethodsSecurityIcons.getIconPinCode();
        if (savedStateHandle.get(AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON) != null) this.factorsSecurityIconsCircleCodeIcon = savedStateHandle.get(AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON);
        else this.factorsSecurityIconsCircleCodeIcon = authMethodsSecurityIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsSecurityIcons.getIconCircleCodeRes()) : authMethodsSecurityIcons.getIconCircleCode();
        if (savedStateHandle.get(AUTH_METHODS_SECURITY_GEOFENCES_ICON) != null) this.factorsSecurityIconsGeofencesIcon = savedStateHandle.get(AUTH_METHODS_SECURITY_GEOFENCES_ICON);
        else this.factorsSecurityIconsGeofencesIcon = authMethodsSecurityIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsSecurityIcons.getIconGeofencingRes()) : authMethodsSecurityIcons.getIconGeofencing();
        if (savedStateHandle.get(AUTH_METHODS_SECURITY_WEARABLES_ICON) != null) this.factorsSecurityIconsWearablesIcon = savedStateHandle.get(AUTH_METHODS_SECURITY_WEARABLES_ICON);
        else this.factorsSecurityIconsWearablesIcon = authMethodsSecurityIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsSecurityIcons.getIconWearableRes()) : authMethodsSecurityIcons.getIconWearable();
        if (savedStateHandle.get(AUTH_METHODS_SECURITY_BIOMETRIC_ICON) != null) this.factorsSecurityIconsBiometricIcon = savedStateHandle.get(AUTH_METHODS_SECURITY_BIOMETRIC_ICON);
        else this.factorsSecurityIconsBiometricIcon = authMethodsSecurityIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsSecurityIcons.getIconFingerprintScanRes()) : authMethodsSecurityIcons.getIconFingerprintScan();

        // Help Menu Items
        final HelpMenuUiProp helpMenuUiProp = authenticatorTheme.getHelpMenuItems();
        if (savedStateHandle.get(HELP_MENU_ITEMS_VISIBILITY) != null) this.helpMenuItemsVisibility = savedStateHandle.get(HELP_MENU_ITEMS_VISIBILITY);
        else this.helpMenuItemsVisibility = helpMenuUiProp.getVisible() ? View.VISIBLE : View.GONE;

        // PIN Code
        final PinCodeUiProp pinCodeUiProp = authenticatorTheme.getPinCode();
        if (savedStateHandle.get(PIN_CODE_BACKGROUND) != null) this.pinCodeColorBackground = new ColorDrawable(savedStateHandle.get(PIN_CODE_BACKGROUND));
        else this.pinCodeColorBackground = pinCodeUiProp.getDrawableBg();
        if (savedStateHandle.get(PIN_CODE_TEXT_COLOR) != null) this.pinCodeColorText = savedStateHandle.get(PIN_CODE_TEXT_COLOR);
        else this.pinCodeColorText = pinCodeUiProp.getLabelColor();

        // Circle Code
        final CircleCodeUiProp circleCodeUiProp = authenticatorTheme.getCircleCode();
        if (savedStateHandle.get(CIRCLE_CODE_HIGHLIGHT_COLOR) != null) this.circleCodeHighlightColor = savedStateHandle.get(CIRCLE_CODE_HIGHLIGHT_COLOR);
        else this.circleCodeHighlightColor = circleCodeUiProp.getColorHighlight();
        if (savedStateHandle.get(CIRCLE_CODE_TICK_COLOR) != null) this.circleCodeTickColor = savedStateHandle.get(CIRCLE_CODE_TICK_COLOR);
        else this.circleCodeTickColor = circleCodeUiProp.getColorMarks();

        // Factors Busy Icons
        final AuthMethodsBusyIcons authMethodsBusyIcons = authenticatorTheme.getMethodsBusyIcons();
        if (savedStateHandle.get(AUTH_METHODS_BUSY_GEOFENCES_ICON) != null) this.factorsBusyIconsGeofencingIcon = savedStateHandle.get(AUTH_METHODS_BUSY_GEOFENCES_ICON);
        else this.factorsBusyIconsGeofencingIcon = authMethodsBusyIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsBusyIcons.getIconGeofencingRes()) : authMethodsBusyIcons.getIconGeofencing();
        if (savedStateHandle.get(AUTH_METHODS_BUSY_WEARABLES_ICON) != null) this.factorsBusyIconsWearablesIcon = savedStateHandle.get(AUTH_METHODS_BUSY_WEARABLES_ICON);
        else this.factorsBusyIconsWearablesIcon = authMethodsBusyIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsBusyIcons.getIconWearableRes()) : authMethodsBusyIcons.getIconWearable();
        if (savedStateHandle.get(AUTH_METHODS_BUSY_BIOMETRIC_ICON) != null) this.factorsBusyIconsBiometricIcon = savedStateHandle.get(AUTH_METHODS_BUSY_BIOMETRIC_ICON);
        else this.factorsBusyIconsBiometricIcon = authMethodsBusyIcons.getResProvided() ? AppCompatResources.getDrawable(context, authMethodsBusyIcons.getIconFingerprintScanRes()) : authMethodsBusyIcons.getIconFingerprintScan();

        // Settings Headers
        final SettingsHeadersUiProp settingsHeadersUiProp = authenticatorTheme.getSettingsHeaders();
        if (savedStateHandle.get(SETTINGS_HEADERS_BACKGROUND_COLOR) != null) this.settingsHeadersBackground = savedStateHandle.get(SETTINGS_HEADERS_BACKGROUND_COLOR);
        else this.settingsHeadersBackground = settingsHeadersUiProp.getColorBackground();
        if (savedStateHandle.get(SETTINGS_HEADERS_TEXT_COLOR) != null) this.settingsHeadersText = savedStateHandle.get(SETTINGS_HEADERS_TEXT_COLOR);
        else this.settingsHeadersText = settingsHeadersUiProp.getColorText();

        // Edit Text
        final EditTextUiProp editTextUiProp = authenticatorTheme.getEditText();
        if (savedStateHandle.get(EDIT_TEXT_HINT_COLOR) != null) this.editTextHint = savedStateHandle.get(EDIT_TEXT_HINT_COLOR);
        else this.editTextHint = editTextUiProp.getColorHint();
        if (savedStateHandle.get(EDIT_TEXT_TEXT_COLOR) != null) this.editTextText = savedStateHandle.get(EDIT_TEXT_TEXT_COLOR);
        else this.editTextText = editTextUiProp.getColorText();

        // Expiration Timer
        final ExpirationTimerProp expirationTimerProp = authenticatorTheme.getExpirationTimer();
        if (savedStateHandle.get(EXPIRATION_TIMER_BACKGROUND_COLOR) != null) this.expirationTimerBackground = savedStateHandle.get(EXPIRATION_TIMER_BACKGROUND_COLOR);
        else this.expirationTimerBackground = expirationTimerProp.getColorBg();
        if (savedStateHandle.get(EXPIRATION_TIMER_FILL_COLOR) != null) this.expirationTimerFill = savedStateHandle.get(EXPIRATION_TIMER_FILL_COLOR);
        else this.expirationTimerFill = expirationTimerProp.getColorFill();
        if (savedStateHandle.get(EXPIRATION_TIMER_WARNING_COLOR) != null) this.expirationTimerWarningColor = savedStateHandle.get(EXPIRATION_TIMER_WARNING_COLOR);
        else this.expirationTimerWarningColor = expirationTimerProp.getColorWarn();

        // Denial Reasons
        final DenialOptionsUiProp denialOptionsUiProp = authenticatorTheme.getDenialOptions();
        if (savedStateHandle.get(DENIAL_REASONS_OPTION_NORMAL) != null) this.denialReasonsOptionNormal = savedStateHandle.get(DENIAL_REASONS_OPTION_NORMAL);
        else this.denialReasonsOptionNormal = denialOptionsUiProp.getColorNormal();
        if (savedStateHandle.get(DENIAL_REASONS_OPTION_CHECKED) != null) this.denialReasonsOptionChecked = savedStateHandle.get(DENIAL_REASONS_OPTION_CHECKED);
        else this.denialReasonsOptionChecked = denialOptionsUiProp.getColorChecked();

        // Auth Response Button
        final TarbUiProp tarbUiProp = authenticatorTheme.getArb();
        if (savedStateHandle.get(AUTH_RESPONSE_BUTTON_BACKGROUND) != null) this.authResponseButtonBackground = savedStateHandle.get(AUTH_RESPONSE_BUTTON_BACKGROUND);
        else this.authResponseButtonBackground = tarbUiProp.getUseReferences() ? AppCompatResources.getDrawable(context, tarbUiProp.getBackgroundResId()) : tarbUiProp.getBackground();
        if (savedStateHandle.get(AUTH_RESPONSE_BUTTON_TEXT_COLOR) != null) this.authResponseButtonTextColor = savedStateHandle.get(AUTH_RESPONSE_BUTTON_TEXT_COLOR);
        else this.authResponseButtonTextColor = tarbUiProp.getColorText();
        if (savedStateHandle.get(AUTH_RESPONSE_BUTTON_FILL_COLOR) != null) this.authResponseButtonFillColor = savedStateHandle.get(AUTH_RESPONSE_BUTTON_FILL_COLOR);
        else this.authResponseButtonFillColor = tarbUiProp.getColorFill();

        // Negative Auth Response Button
        final TarbNegativeUiProp negativeTarbUiProp = authenticatorTheme.getArbNegative();
        if (savedStateHandle.get(NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND) != null) this.negativeAuthResponseButtonBackground = savedStateHandle.get(NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND);
        else this.negativeAuthResponseButtonBackground = negativeTarbUiProp.getUseReferences() ? AppCompatResources.getDrawable(context, negativeTarbUiProp.getBackgroundResId()) : negativeTarbUiProp.getBackground();
        if (savedStateHandle.get(NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR) != null) this.negativeAuthResponseButtonTextColor = savedStateHandle.get(NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR);
        else this.negativeAuthResponseButtonTextColor = negativeTarbUiProp.getColorText();
        if (savedStateHandle.get(NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR) != null) this.negativeAuthResponseButtonFillColor = savedStateHandle.get(NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR);
        else this.negativeAuthResponseButtonFillColor = negativeTarbUiProp.getColorFill();

        // Auth Response Authorized Color
        if (savedStateHandle.get(AUTH_RESPONSE_AUTHORIZED_COLOR) != null) this.authResponseAuthorizedColor = savedStateHandle.get(AUTH_RESPONSE_AUTHORIZED_COLOR);
        else this.authResponseAuthorizedColor = authenticatorTheme.getAuthResponseAuthorized();

        // Auth Response Denied Color
        if (savedStateHandle.get(AUTH_RESPONSE_DENIED_COLOR) != null) this.authResponseDeniedColor = savedStateHandle.get(AUTH_RESPONSE_DENIED_COLOR);
        else this.authResponseDeniedColor = authenticatorTheme.getAuthResponseDenied();

        // Auth Response Failed Color
        if (savedStateHandle.get(AUTH_RESPONSE_FAILED_COLOR) != null) this.authResponseFailedColor = savedStateHandle.get(AUTH_RESPONSE_FAILED_COLOR);
        else this.authResponseFailedColor = authenticatorTheme.getAuthResponseFailed();

        // Auth Content View Background
        final AuthContentBgUiProp authContentBgUiProp = authenticatorTheme.getAuthContentBg();
        if (savedStateHandle.get(AUTH_CONTENT_VIEW_BACKGROUND) != null) this.authContentViewBackground = savedStateHandle.get(AUTH_CONTENT_VIEW_BACKGROUND);
        else this.authContentViewBackground = authContentBgUiProp.getColorBg();
    }

    public AuthenticatorTheme getAuthenticatorTheme(@NonNull final Context context) {
        return new AuthenticatorTheme.Builder(context.getApplicationContext(), R.style.DemoAppTheme)
                .appBar(this.appBarBackground, this.appBarItemColor)
                .background(this.background)
                .backgroundOverlay(this.backgroundOverlay)
                .button(this.button, this.buttonText)
                .buttonNegative(this.buttonNegative, this.buttonNegativeText)
                .listHeaders(this.listHeadersVisibility, this.listHeadersColorBackground, this.listHeadersColorText)
                .factorsSecurityIcons(this.factorsSecurityIconsVisibility, this.factorsSecurityIconsPINCodeIcon, this.factorsSecurityIconsCircleCodeIcon, this.factorsSecurityIconsGeofencesIcon, this.factorsSecurityIconsWearablesIcon, this.factorsSecurityIconsBiometricIcon)
                .helpMenuItems(this.helpMenuItemsVisibility == View.VISIBLE)
                .pinCode(this.pinCodeColorBackground, this.pinCodeColorText)
                .circleCode(this.circleCodeHighlightColor, this.circleCodeTickColor)
                .factorsBusyIcons(this.factorsBusyIconsGeofencingIcon, this.factorsBusyIconsWearablesIcon, this.factorsBusyIconsBiometricIcon)
                .settingsHeaders(this.settingsHeadersBackground, this.settingsHeadersText)
                .editText(this.editTextHint, this.editTextText)
                .expirationTimer(this.expirationTimerBackground, this.expirationTimerFill, this.expirationTimerWarningColor)
                .denialReasons(this.denialReasonsOptionNormal, this.denialReasonsOptionChecked)
                .authResponseButton(this.authResponseButtonBackground, this.authResponseButtonTextColor, this.authResponseButtonFillColor)
                .authResponseButtonNegative(this.negativeAuthResponseButtonBackground, this.negativeAuthResponseButtonTextColor, this.negativeAuthResponseButtonFillColor)
                .authResponseAuthorizedColor(this.authResponseAuthorizedColor)
                .authResponseDeniedColor(this.authResponseDeniedColor)
                .authResponseFailedColor(this.authResponseFailedColor)
                .authContentViewBackground(this.authContentViewBackground)
                .build();
    }
}
