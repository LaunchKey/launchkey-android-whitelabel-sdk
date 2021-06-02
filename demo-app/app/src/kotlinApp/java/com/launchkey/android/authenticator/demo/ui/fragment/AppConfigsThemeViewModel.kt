package com.launchkey.android.authenticator.demo.ui.fragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager
import com.launchkey.android.authenticator.sdk.ui.theme.AuthenticatorTheme

class AppConfigsThemeViewModel(authenticatorUIManager: AuthenticatorUIManager,
                               context: Context,
                               private val savedStateHandle: SavedStateHandle) : ViewModel() {
    /*
        App Bar
    */
    @ColorInt
    private var appBarBackground = 0
    @ColorInt
    fun getAppBarBackground(): Int {
        return appBarBackground
    }

    fun setAppBarBackground(@ColorInt appBarBackground: Int) {
        savedStateHandle.set(APP_BAR_BACKGROUND, this.appBarBackground)
        this.appBarBackground = appBarBackground
    }

    @ColorInt
    private var appBarItemColor = 0
    @ColorInt
    fun getAppBarItemColor(): Int {
        return appBarItemColor
    }

    fun setAppBarItemColor(@ColorInt appBarItemColor: Int) {
        savedStateHandle.set(APP_BAR_ITEM_COLOR, this.appBarItemColor)
        this.appBarItemColor = appBarItemColor
    }

    /*
        Background
    */
    var background: Drawable? = null
        private set

    fun setBackground(@ColorInt background: Int) {
        savedStateHandle.set(BACKGROUND, background)
        this.background = ColorDrawable(background)
    }

    /*
        Background Overlay
    */
    @ColorInt
    private var backgroundOverlay = 0
    @ColorInt
    fun getBackgroundOverlay(): Int {
        return backgroundOverlay
    }

    fun setBackgroundOverlay(@ColorInt backgroundOverlay: Int) {
        savedStateHandle.set(BACKGROUND_OVERLAY, backgroundOverlay)
        this.backgroundOverlay = backgroundOverlay
    }

    /*
        Button
    */
    var button: Drawable? = null
        private set

    fun setButton(@ColorInt button: Int) {
        savedStateHandle.set(BUTTON, button)
        this.button = ColorDrawable(button)
    }

    @ColorInt
    private var buttonText = 0
    @ColorInt
    fun getButtonText(): Int {
        return buttonText
    }

    fun setButtonText(@ColorInt buttonText: Int) {
        savedStateHandle.set(BUTTON, buttonText)
        this.buttonText = buttonText
    }

    /*
        Button Negative
    */
    var buttonNegative: Drawable? = null
        private set

    fun setButtonNegative(@ColorInt buttonNegative: Int) {
        savedStateHandle.set(BUTTON_NEGATIVE, buttonNegative)
        this.buttonNegative = ColorDrawable(buttonNegative)
    }

    @ColorInt
    private var buttonNegativeText = 0
    @ColorInt
    fun getButtonNegativeText(): Int {
        return buttonNegativeText
    }

    fun setButtonNegativeText(@ColorInt buttonNegativeText: Int) {
        savedStateHandle.set(BUTTON, buttonNegativeText)
        this.buttonNegativeText = buttonNegativeText
    }

    /*
        List Headers
    */
    private var listHeadersVisibility = 0
    fun getListHeadersVisibility(): Int {
        return listHeadersVisibility
    }

    fun setListHeadersVisibility(listHeadersVisibility: Int) {
        savedStateHandle.set(LIST_HEADER_VISIBILITY, listHeadersVisibility)
        this.listHeadersVisibility = listHeadersVisibility
    }

    @ColorInt
    private var listHeadersColorBackground = 0
    @ColorInt
    fun getListHeadersColorBackground(): Int {
        return listHeadersColorBackground
    }

    fun setListHeadersColorBackground(@ColorInt listHeadersColorBackground: Int) {
        savedStateHandle.set(LIST_HEADER_COLOR_BACKGROUND, listHeadersColorBackground)
        this.listHeadersColorBackground = listHeadersColorBackground
    }

    @ColorInt
    private var listHeadersColorText = 0
    @ColorInt
    fun getListHeadersColorText(): Int {
        return listHeadersColorText
    }

    fun setListHeadersColorText(@ColorInt listHeadersColorText: Int) {
        savedStateHandle.set(LIST_HEADER_COLOR_TEXT, listHeadersColorText)
        this.listHeadersColorText = listHeadersColorText
    }

    /*
        List Headers
    */
    private var factorsSecurityIconsVisibility = 0
    fun getFactorsSecurityIconsVisibility(): Int {
        return factorsSecurityIconsVisibility
    }

    fun setFactorsSecurityIconsVisibility(factorsSecurityIconsVisibility: Int) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_VISIBILITY, factorsSecurityIconsVisibility)
        this.factorsSecurityIconsVisibility = factorsSecurityIconsVisibility
    }

    var factorsSecurityIconsPINCodeIcon: Drawable? = null
        private set

    fun setFactorsSecurityIconsPINCodeIcon(@ColorInt factorsSecurityIconsPINCodeIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_PIN_CODE_ICON, factorsSecurityIconsPINCodeIcon)
        this.factorsSecurityIconsPINCodeIcon = ColorDrawable(factorsSecurityIconsPINCodeIcon)
    }

    var factorsSecurityIconsCircleCodeIcon: Drawable? = null
        private set

    fun setFactorsSecurityIconsCircleCodeIcon(@ColorInt factorsSecurityIconsCircleCodeIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON, factorsSecurityIconsCircleCodeIcon)
        this.factorsSecurityIconsCircleCodeIcon = ColorDrawable(factorsSecurityIconsCircleCodeIcon)
    }

    var factorsSecurityIconsGeofencesIcon: Drawable? = null
        private set

    fun setFactorsSecurityIconsGeofencesIcon(@ColorInt factorsSecurityIconsGeofencesIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_GEOFENCES_ICON, factorsSecurityIconsGeofencesIcon)
        this.factorsSecurityIconsGeofencesIcon = ColorDrawable(factorsSecurityIconsGeofencesIcon)
    }

    var factorsSecurityIconsWearablesIcon: Drawable? = null
        private set

    fun setFactorsSecurityIconsWearablesIcon(@ColorInt factorsSecurityIconsWearablesIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_WEARABLES_ICON, factorsSecurityIconsWearablesIcon)
        this.factorsSecurityIconsWearablesIcon = ColorDrawable(factorsSecurityIconsWearablesIcon)
    }

    var factorsSecurityIconsBiometricIcon: Drawable? = null
        private set

    fun setFactorsSecurityIconsBiometricIcon(@ColorInt factorsSecurityIconsBiometricIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_SECURITY_BIOMETRIC_ICON, factorsSecurityIconsBiometricIcon)
        this.factorsSecurityIconsBiometricIcon = ColorDrawable(factorsSecurityIconsBiometricIcon)
    }

    /*
        Help Menu Items
    */
    private var helpMenuItemsVisibility = 0
    fun getHelpMenuItemsVisibility(): Int {
        return helpMenuItemsVisibility
    }

    fun setHelpMenuItemsVisibility(helpMenuItemsVisibility: Int) {
        savedStateHandle.set(HELP_MENU_ITEMS_VISIBILITY, helpMenuItemsVisibility)
        this.helpMenuItemsVisibility = helpMenuItemsVisibility
    }

    /*
        PIN Code
     */
    var pinCodeColorBackground: Drawable? = null
        private set

    fun setPINCodeColorBackground(@ColorInt pinCodeColorBackground: Int) {
        savedStateHandle.set(PIN_CODE_BACKGROUND, pinCodeColorBackground)
        this.pinCodeColorBackground = ColorDrawable(pinCodeColorBackground)
    }

    @ColorInt
    @get:ColorInt
    var pinCodeColorText = 0
        set(pinCodeColorText) {
            savedStateHandle.set(PIN_CODE_TEXT_COLOR, pinCodeColorText)
            field = pinCodeColorText
        }

    /*
     Circle Code
  */
    @ColorInt
    private var circleCodeHighlightColor = 0
    @ColorInt
    fun getCircleCodeHighlightColor(): Int {
        return circleCodeHighlightColor
    }

    fun setCircleCodeHighlightColor(@ColorInt circleCodeHighlightColor: Int) {
        savedStateHandle.set(CIRCLE_CODE_HIGHLIGHT_COLOR, circleCodeHighlightColor)
        this.circleCodeHighlightColor = circleCodeHighlightColor
    }

    @ColorInt
    private var circleCodeTickColor = 0
    @ColorInt
    fun getCircleCodeTickColor(): Int {
        return circleCodeTickColor
    }

    fun setCircleCodeTickColor(@ColorInt circleCodeTickColor: Int) {
        savedStateHandle.set(CIRCLE_CODE_TICK_COLOR, circleCodeTickColor)
        this.circleCodeTickColor = circleCodeTickColor
    }

    /*
        Factors Busy Icons
    */
    var factorsBusyIconsGeofencingIcon: Drawable? = null
        private set

    fun setFactorsBusyIconsGeofencingIcon(@ColorInt factorsBusyIconsGeofencingIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_BUSY_GEOFENCES_ICON, factorsBusyIconsGeofencingIcon)
        this.factorsBusyIconsGeofencingIcon = ColorDrawable(factorsBusyIconsGeofencingIcon)
    }

    var factorsBusyIconsWearablesIcon: Drawable? = null
        private set

    fun setFactorsBusyIconsWearablesIcon(@ColorInt factorsBusyIconsWearablesIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_BUSY_WEARABLES_ICON, factorsBusyIconsWearablesIcon)
        this.factorsBusyIconsWearablesIcon = ColorDrawable(factorsBusyIconsWearablesIcon)
    }

    var factorsBusyIconsBiometricIcon: Drawable? = null
        private set

    fun setFactorsBusyIconsBiometricIcon(@ColorInt factorsBusyIconsBiometricIcon: Int) {
        savedStateHandle.set(AUTH_METHODS_BUSY_BIOMETRIC_ICON, factorsBusyIconsBiometricIcon)
        this.factorsBusyIconsBiometricIcon = ColorDrawable(factorsBusyIconsBiometricIcon)
    }

    /*
        Settings Headers
    */
    var settingsHeadersBackground: Drawable? = null
        private set

    fun setSettingsHeadersBackground(@ColorInt settingsHeadersBackground: Int) {
        savedStateHandle.set(SETTINGS_HEADERS_BACKGROUND_COLOR, settingsHeadersBackground)
        this.settingsHeadersBackground = ColorDrawable(settingsHeadersBackground)
    }

    @ColorInt
    private var settingsHeadersText = 0
    @ColorInt
    fun getSettingsHeadersText(): Int {
        return settingsHeadersText
    }

    fun setSettingsHeadersText(@ColorInt settingsHeadersText: Int) {
        savedStateHandle.set(SETTINGS_HEADERS_TEXT_COLOR, settingsHeadersText)
        this.settingsHeadersText = settingsHeadersText
    }

    /*
        Edit Text
    */
    @ColorInt
    private var editTextHint = 0
    @ColorInt
    fun getEditTextHint(): Int {
        return editTextHint
    }

    fun setEditTextHint(@ColorInt editTextHint: Int) {
        savedStateHandle.set(EDIT_TEXT_HINT_COLOR, editTextHint)
        this.editTextHint = editTextHint
    }

    @ColorInt
    private var editTextText = 0
    @ColorInt
    fun getEditTextText(): Int {
        return editTextText
    }

    fun setEditTextText(@ColorInt editTextText: Int) {
        savedStateHandle.set(EDIT_TEXT_TEXT_COLOR, editTextText)
        this.editTextText = editTextText
    }

    /*
        Expiration Timer
    */
    @ColorInt
    private var expirationTimerBackground = 0
    @ColorInt
    fun getExpirationTimerBackground(): Int {
        return expirationTimerBackground
    }

    fun setExpirationTimerBackground(@ColorInt expirationTimerBackground: Int) {
        savedStateHandle.set(EXPIRATION_TIMER_BACKGROUND_COLOR, expirationTimerBackground)
        this.expirationTimerBackground = expirationTimerBackground
    }

    @ColorInt
    private var expirationTimerFill = 0
    @ColorInt
    fun getExpirationTimerFill(): Int {
        return expirationTimerFill
    }

    fun setExpirationTimerFill(@ColorInt expirationTimerFill: Int) {
        savedStateHandle.set(EXPIRATION_TIMER_FILL_COLOR, expirationTimerFill)
        this.expirationTimerFill = expirationTimerFill
    }

    @ColorInt
    private var expirationTimerWarningColor = 0
    @ColorInt
    fun getExpirationTimerWarningColor(): Int {
        return expirationTimerWarningColor
    }

    fun setExpirationTimerWarningColor(@ColorInt expirationTimerWarningColor: Int) {
        savedStateHandle.set(EXPIRATION_TIMER_WARNING_COLOR, expirationTimerWarningColor)
        this.expirationTimerWarningColor = expirationTimerWarningColor
    }

    /*
        Denial Reasons
    */
    @ColorInt
    private var denialReasonsOptionNormal = 0
    @ColorInt
    fun getDenialReasonsOptionNormal(): Int {
        return denialReasonsOptionNormal
    }

    fun setDenialReasonsOptionNormal(@ColorInt denialReasonsOptionNormal: Int) {
        savedStateHandle.set(DENIAL_REASONS_OPTION_NORMAL, denialReasonsOptionNormal)
        this.denialReasonsOptionNormal = denialReasonsOptionNormal
    }

    @ColorInt
    private var denialReasonsOptionChecked = 0
    @ColorInt
    fun getDenialReasonsOptionChecked(): Int {
        return denialReasonsOptionChecked
    }

    fun setDenialReasonsOptionChecked(@ColorInt denialReasonsOptionChecked: Int) {
        savedStateHandle.set(DENIAL_REASONS_OPTION_CHECKED, denialReasonsOptionChecked)
        this.denialReasonsOptionChecked = denialReasonsOptionChecked
    }

    /*
        Auth Response button
    */
    var authResponseButtonBackground: Drawable? = null
        private set

    fun setAuthResponseButtonBackground(@ColorInt authResponseButtonBackground: Int) {
        savedStateHandle.set(AUTH_RESPONSE_BUTTON_BACKGROUND, authResponseButtonBackground)
        this.authResponseButtonBackground = ColorDrawable(authResponseButtonBackground)
    }

    @ColorInt
    private var authResponseButtonTextColor = 0
    @ColorInt
    fun getAuthResponseButtonTextColor(): Int {
        return authResponseButtonTextColor
    }

    fun setAuthResponseButtonTextColor(@ColorInt authResponseButtonTextColor: Int) {
        savedStateHandle.set(AUTH_RESPONSE_BUTTON_TEXT_COLOR, authResponseButtonTextColor)
        this.authResponseButtonTextColor = authResponseButtonTextColor
    }

    @ColorInt
    private var authResponseButtonFillColor = 0
    @ColorInt
    fun getAuthResponseButtonFillColor(): Int {
        return authResponseButtonFillColor
    }

    fun setAuthResponseButtonFillColor(@ColorInt authResponseButtonFillColor: Int) {
        savedStateHandle.set(AUTH_RESPONSE_BUTTON_FILL_COLOR, authResponseButtonFillColor)
        this.authResponseButtonFillColor = authResponseButtonFillColor
    }

    /*
        Negative Auth Response button
    */
    var negativeAuthResponseButtonBackground: Drawable? = null
        private set

    fun setNegativeAuthResponseButtonBackground(@ColorInt negativeAuthResponseButtonBackground: Int) {
        savedStateHandle.set(NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND, negativeAuthResponseButtonBackground)
        this.negativeAuthResponseButtonBackground = ColorDrawable(negativeAuthResponseButtonBackground)
    }

    @ColorInt
    private var negativeAuthResponseButtonTextColor = 0
    @ColorInt
    fun getNegativeAuthResponseButtonTextColor(): Int {
        return negativeAuthResponseButtonTextColor
    }

    fun setNegativeAuthResponseButtonTextColor(@ColorInt negativeAuthResponseButtonTextColor: Int) {
        savedStateHandle.set(NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR, negativeAuthResponseButtonTextColor)
        this.negativeAuthResponseButtonTextColor = negativeAuthResponseButtonTextColor
    }

    @ColorInt
    private var negativeAuthResponseButtonFillColor = 0
    @ColorInt
    fun getNegativeAuthResponseButtonFillColor(): Int {
        return negativeAuthResponseButtonFillColor
    }

    fun setNegativeAuthResponseButtonFillColor(@ColorInt negativeAuthResponseButtonFillColor: Int) {
        savedStateHandle.set(NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR, negativeAuthResponseButtonFillColor)
        this.negativeAuthResponseButtonFillColor = negativeAuthResponseButtonFillColor
    }

    /*
        Auth Response Authorized Color
    */
    @ColorInt
    private var authResponseAuthorizedColor = 0
    @ColorInt
    fun getAuthResponseAuthorizedColor(): Int {
        return authResponseAuthorizedColor
    }

    fun setAuthResponseAuthorizedColor(@ColorInt authResponseAuthorizedColor: Int) {
        savedStateHandle.set(AUTH_RESPONSE_AUTHORIZED_COLOR, authResponseAuthorizedColor)
        this.authResponseAuthorizedColor = authResponseAuthorizedColor
    }

    /*
        Auth Response Denied Color
    */
    @ColorInt
    private var authResponseDeniedColor = 0
    @ColorInt
    fun getAuthResponseDeniedColor(): Int {
        return authResponseDeniedColor
    }

    fun setAuthResponseDeniedColor(@ColorInt authResponseDeniedColor: Int) {
        savedStateHandle.set(AUTH_RESPONSE_DENIED_COLOR, authResponseDeniedColor)
        this.authResponseDeniedColor = authResponseDeniedColor
    }

    /*
        Auth Response Failed Color
    */
    @ColorInt
    private var authResponseFailedColor = 0
    @ColorInt
    fun getAuthResponseFailedColor(): Int {
        return authResponseFailedColor
    }

    fun setAuthResponseFailedColor(@ColorInt authResponseFailedColor: Int) {
        savedStateHandle.set(AUTH_RESPONSE_FAILED_COLOR, authResponseFailedColor)
        this.authResponseFailedColor = authResponseFailedColor
    }

    /*
        Auth Content View Background
    */
    @ColorInt
    private var authContentViewBackground = 0
    @ColorInt
    fun getAuthContentViewBackground(): Int {
        return authContentViewBackground
    }

    fun setAuthContentViewBackground(@ColorInt authContentViewBackground: Int) {
        savedStateHandle.set(AUTH_CONTENT_VIEW_BACKGROUND, authContentViewBackground)
        this.authContentViewBackground = authContentViewBackground
    }

    fun getAuthenticatorTheme(context: Context): AuthenticatorTheme? {
        return AuthenticatorTheme.Builder(context.applicationContext, R.style.DemoAppTheme)
                .appBar(appBarBackground, appBarItemColor)
                .background(background!!)
                .backgroundOverlay(backgroundOverlay)
                .button(button!!, buttonText)
                .buttonNegative(buttonNegative!!, buttonNegativeText)
                .listHeaders(listHeadersVisibility, listHeadersColorBackground, listHeadersColorText)
                .factorsSecurityIcons(factorsSecurityIconsVisibility, factorsSecurityIconsPINCodeIcon, factorsSecurityIconsCircleCodeIcon, factorsSecurityIconsGeofencesIcon, factorsSecurityIconsWearablesIcon, factorsSecurityIconsBiometricIcon)
                .helpMenuItems(helpMenuItemsVisibility == View.VISIBLE)
                .pinCode(pinCodeColorBackground!!, pinCodeColorText)
                .circleCode(circleCodeHighlightColor, circleCodeTickColor)
                .factorsBusyIcons(factorsBusyIconsGeofencingIcon, factorsBusyIconsWearablesIcon, factorsBusyIconsBiometricIcon)
                .settingsHeaders(settingsHeadersBackground!!, settingsHeadersText)
                .editText(editTextHint, editTextText)
                .expirationTimer(expirationTimerBackground, expirationTimerFill, expirationTimerWarningColor)
                .denialReasons(denialReasonsOptionNormal, denialReasonsOptionChecked)
                .authResponseButton(authResponseButtonBackground, authResponseButtonTextColor, authResponseButtonFillColor)
                .authResponseButtonNegative(negativeAuthResponseButtonBackground, negativeAuthResponseButtonTextColor, negativeAuthResponseButtonFillColor)
                .authResponseAuthorizedColor(authResponseAuthorizedColor)
                .authResponseDeniedColor(authResponseDeniedColor)
                .authResponseFailedColor(authResponseFailedColor)
                .authContentViewBackground(authContentViewBackground)
                .build()
    }

    companion object {
        // App Bar
        private const val APP_BAR_BACKGROUND = "AppBarBackground"
        private const val APP_BAR_ITEM_COLOR = "AppBarItemColor"

        // Background
        private const val BACKGROUND = "Background"

        // Background Overlay
        private const val BACKGROUND_OVERLAY = "BackgroundOverlay"

        // Button
        private const val BUTTON = "Button"
        private const val BUTTON_TEXT = "ButtonText"

        // Button Negative
        private const val BUTTON_NEGATIVE = "ButtonNegative"
        private const val BUTTON_NEGATIVE_TEXT = "ButtonNegativeText"

        // List Headers
        private const val LIST_HEADER_VISIBILITY = "ListHeaderVisibility"
        private const val LIST_HEADER_COLOR_BACKGROUND = "ListHeaderColorBackground"
        private const val LIST_HEADER_COLOR_TEXT = "ListHeaderColorText"

        // List Headers
        private const val AUTH_METHODS_SECURITY_VISIBILITY = "AuthMethodsSecurityVisibility"
        private const val AUTH_METHODS_SECURITY_PIN_CODE_ICON = "AuthMethodsSecurityPINCodeIcon"
        private const val AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON = "AuthMethodsSecurityCircleCodeIcon"
        private const val AUTH_METHODS_SECURITY_GEOFENCES_ICON = "AuthMethodsSecurityGeofencesIcon"
        private const val AUTH_METHODS_SECURITY_WEARABLES_ICON = "AuthMethodsSecurityWearablesIcon"
        private const val AUTH_METHODS_SECURITY_BIOMETRIC_ICON = "AuthMethodsSecurityBiometricIcon"

        // Help Menu Items
        private const val HELP_MENU_ITEMS_VISIBILITY = "HelpMenuItemsVisibility"

        // PIN Code
        private const val PIN_CODE_BACKGROUND = "PINCodeBackground"
        private const val PIN_CODE_TEXT_COLOR = "PINCodeTextColor"

        // Circle Code
        private const val CIRCLE_CODE_HIGHLIGHT_COLOR = "CircleCodeHighlightColor"
        private const val CIRCLE_CODE_TICK_COLOR = "CircleCodeTickColor"

        // Factors Busy Icons
        private const val AUTH_METHODS_BUSY_GEOFENCES_ICON = "AuthMethodBusyGeofencesIcon"
        private const val AUTH_METHODS_BUSY_WEARABLES_ICON = "AuthMethodBusyWearablesIcon"
        private const val AUTH_METHODS_BUSY_BIOMETRIC_ICON = "AuthMethodBusyBiometricIcon"

        // Settings Headers
        private const val SETTINGS_HEADERS_BACKGROUND_COLOR = "SettingsHeadersBackgroundColor"
        private const val SETTINGS_HEADERS_TEXT_COLOR = "SettingsHeadersTextColor"

        // Edit Text
        private const val EDIT_TEXT_HINT_COLOR = "EditTextHintColor"
        private const val EDIT_TEXT_TEXT_COLOR = "EditTextTextColor"

        // Expiration Timer
        private const val EXPIRATION_TIMER_BACKGROUND_COLOR = "ExpirationTimerBackgroundColor"
        private const val EXPIRATION_TIMER_FILL_COLOR = "ExpirationTimerFillColor"
        private const val EXPIRATION_TIMER_WARNING_COLOR = "ExpirationTimerWarningColor"

        // Denial Reasons
        private const val DENIAL_REASONS_OPTION_NORMAL = "DenialReasonsOptionNormal"
        private const val DENIAL_REASONS_OPTION_CHECKED = "DenialReasonsOptionChecked"

        // Auth Response Button
        private const val AUTH_RESPONSE_BUTTON_BACKGROUND = "AuthResponseButtonBackground"
        private const val AUTH_RESPONSE_BUTTON_TEXT_COLOR = "AuthResponseButtonTextColor"
        private const val AUTH_RESPONSE_BUTTON_FILL_COLOR = "AuthResponseButtonFillColor"

        // Negative Auth Response Button
        private const val NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND = "NegativeAuthResponseButtonBackground"
        private const val NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR = "NegativeAuthResponseButtonTextColor"
        private const val NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR = "NegativeAuthResponseButtonFillColor"

        // Auth Response Authorized Color
        private const val AUTH_RESPONSE_AUTHORIZED_COLOR = "AuthResponseAuthorizedColor"

        // Auth Response Denied Color
        private const val AUTH_RESPONSE_DENIED_COLOR = "AuthResponseDeniedColor"

        // Auth Response Failed Color
        private const val AUTH_RESPONSE_FAILED_COLOR = "AuthResponseFailedColor"

        // Auth Content View Background
        private const val AUTH_CONTENT_VIEW_BACKGROUND = "AuthContentViewBackground"
    }

    init {
        val authenticatorTheme = authenticatorUIManager.config.themeObj()

        // App Bar
        val (backgroundColor, colorItems) = authenticatorTheme.appBar
        appBarBackground = if (savedStateHandle.get<Any?>(APP_BAR_BACKGROUND) != null) savedStateHandle.get<Int>(APP_BAR_BACKGROUND)!! else backgroundColor
        appBarItemColor = if (savedStateHandle.get<Any?>(APP_BAR_ITEM_COLOR) != null) savedStateHandle.get<Int>(APP_BAR_ITEM_COLOR)!! else colorItems

        // Background
        val drawableBg = authenticatorTheme.bg
        background = if (savedStateHandle.get<Any?>(BACKGROUND) != null) ColorDrawable(savedStateHandle.get<Int>(BACKGROUND)!!) else drawableBg.drawableBg

        // Background Overlay
        val (colorBgOverlay) = authenticatorTheme.bgOverlay
        backgroundOverlay = if (savedStateHandle.get<Any?>(BACKGROUND_OVERLAY) != null) savedStateHandle.get<Int>(BACKGROUND_OVERLAY)!! else colorBgOverlay

        // Button
        val buttonUiProp = authenticatorTheme.button
        button = if (savedStateHandle.get<Any?>(BUTTON) != null) ColorDrawable(savedStateHandle.get<Int>(BUTTON)!!) else buttonUiProp.drawableBg
        buttonText = if (savedStateHandle.get<Any?>(BUTTON_TEXT) != null) savedStateHandle.get<Int>(BUTTON_TEXT)!! else buttonUiProp.colorText

        // Button Negative
        val negativeButtonUiProp = authenticatorTheme.buttonNegative
        buttonNegative = if (savedStateHandle.get<Any?>(BUTTON_NEGATIVE) != null) savedStateHandle.get<Drawable>(BUTTON_NEGATIVE) else negativeButtonUiProp.drawableBg
        buttonNegativeText = if (savedStateHandle.get<Any?>(BUTTON_NEGATIVE_TEXT) != null) savedStateHandle.get<Int>(BUTTON_NEGATIVE_TEXT)!! else negativeButtonUiProp.colorText

        // List Headers
        val listHeadersUiProp = authenticatorTheme.listHeaders
        listHeadersVisibility = if (savedStateHandle.get<Any?>(LIST_HEADER_VISIBILITY) != null) savedStateHandle.get<Int>(LIST_HEADER_VISIBILITY)!! else listHeadersUiProp.visibility
        listHeadersColorBackground = if (savedStateHandle.get<Any?>(LIST_HEADER_COLOR_BACKGROUND) != null) savedStateHandle.get<Int>(LIST_HEADER_COLOR_BACKGROUND)!! else listHeadersUiProp.colorBg
        listHeadersColorText = if (savedStateHandle.get<Any?>(LIST_HEADER_COLOR_TEXT) != null) savedStateHandle.get<Int>(LIST_HEADER_COLOR_TEXT)!! else listHeadersUiProp.colorText

        // Factors Security Icons
        val authMethodsSecurityIcons = authenticatorTheme.methodsSecurityIcons
        factorsSecurityIconsVisibility = if (savedStateHandle.get<Any?>(AUTH_METHODS_SECURITY_VISIBILITY) != null) savedStateHandle.get<Int>(AUTH_METHODS_SECURITY_VISIBILITY)!! else authMethodsSecurityIcons.iconVisibility
        factorsSecurityIconsPINCodeIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_SECURITY_PIN_CODE_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_SECURITY_PIN_CODE_ICON) else if (authMethodsSecurityIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsSecurityIcons.iconPinCodeRes!!) else authMethodsSecurityIcons.iconPinCode
        factorsSecurityIconsCircleCodeIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_SECURITY_CIRCLE_CODE_ICON) else if (authMethodsSecurityIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsSecurityIcons.iconCircleCodeRes!!) else authMethodsSecurityIcons.iconCircleCode
        factorsSecurityIconsGeofencesIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_SECURITY_GEOFENCES_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_SECURITY_GEOFENCES_ICON) else if (authMethodsSecurityIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsSecurityIcons.iconGeofencingRes!!) else authMethodsSecurityIcons.iconGeofencing
        factorsSecurityIconsWearablesIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_SECURITY_WEARABLES_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_SECURITY_WEARABLES_ICON) else if (authMethodsSecurityIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsSecurityIcons.iconWearableRes!!) else authMethodsSecurityIcons.iconWearable
        factorsSecurityIconsBiometricIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_SECURITY_BIOMETRIC_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_SECURITY_BIOMETRIC_ICON) else if (authMethodsSecurityIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsSecurityIcons.iconFingerprintScanRes!!) else authMethodsSecurityIcons.iconFingerprintScan

        // Help Menu Items
        val (visible) = authenticatorTheme.helpMenuItems
        if (savedStateHandle.get<Any?>(HELP_MENU_ITEMS_VISIBILITY) != null) factorsSecurityIconsVisibility = savedStateHandle.get<Int>(HELP_MENU_ITEMS_VISIBILITY)!! else helpMenuItemsVisibility = if (visible) View.VISIBLE else View.GONE

        // PIN Code
        val pinCodeUiProp = authenticatorTheme.pinCode
        pinCodeColorBackground = if (savedStateHandle.get<Any?>(PIN_CODE_BACKGROUND) != null) ColorDrawable(savedStateHandle.get<Int>(PIN_CODE_BACKGROUND)!!) else pinCodeUiProp.drawableBg
        pinCodeColorText = if (savedStateHandle.get<Any?>(PIN_CODE_TEXT_COLOR) != null) savedStateHandle.get<Int>(PIN_CODE_TEXT_COLOR)!! else pinCodeUiProp.labelColor

        // Circle Code
        val (colorHighlight, colorMarks) = authenticatorTheme.circleCode
        circleCodeHighlightColor = if (savedStateHandle.get<Any?>(CIRCLE_CODE_HIGHLIGHT_COLOR) != null) savedStateHandle.get<Int>(CIRCLE_CODE_HIGHLIGHT_COLOR)!! else colorHighlight
        circleCodeTickColor = if (savedStateHandle.get<Any?>(CIRCLE_CODE_TICK_COLOR) != null) savedStateHandle.get<Int>(CIRCLE_CODE_TICK_COLOR)!! else colorMarks

        // Factors Busy Icons
        val authMethodsBusyIcons = authenticatorTheme.methodsBusyIcons
        factorsBusyIconsGeofencingIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_BUSY_GEOFENCES_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_BUSY_GEOFENCES_ICON) else if (authMethodsBusyIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsBusyIcons.iconGeofencingRes) else authMethodsBusyIcons.iconGeofencing
        factorsBusyIconsWearablesIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_BUSY_WEARABLES_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_BUSY_WEARABLES_ICON) else if (authMethodsBusyIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsBusyIcons.iconWearableRes) else authMethodsBusyIcons.iconWearable
        factorsBusyIconsBiometricIcon = if (savedStateHandle.get<Any?>(AUTH_METHODS_BUSY_BIOMETRIC_ICON) != null) savedStateHandle.get<Drawable>(AUTH_METHODS_BUSY_BIOMETRIC_ICON) else if (authMethodsBusyIcons.resProvided) AppCompatResources.getDrawable(context, authMethodsBusyIcons.iconFingerprintScanRes) else authMethodsBusyIcons.iconFingerprintScan

        // Settings Headers
        val colorBackground = authenticatorTheme.settingsHeaders.colorBackground
        val colorText = authenticatorTheme.settingsHeaders.colorText
        settingsHeadersBackground = if (savedStateHandle.get<Any?>(SETTINGS_HEADERS_BACKGROUND_COLOR) != null) savedStateHandle.get<Drawable>(SETTINGS_HEADERS_BACKGROUND_COLOR) else colorBackground
        settingsHeadersText = if (savedStateHandle.get<Any?>(SETTINGS_HEADERS_TEXT_COLOR) != null) savedStateHandle.get<Int>(SETTINGS_HEADERS_TEXT_COLOR)!! else colorText

        // Edit Text
        val (colorHint, colorText1) = authenticatorTheme.editText
        editTextHint = if (savedStateHandle.get<Any?>(EDIT_TEXT_HINT_COLOR) != null) savedStateHandle.get<Int>(EDIT_TEXT_HINT_COLOR)!! else colorHint
        editTextText = if (savedStateHandle.get<Any?>(EDIT_TEXT_TEXT_COLOR) != null) savedStateHandle.get<Int>(EDIT_TEXT_TEXT_COLOR)!! else colorText1

        // Expiration Timer
        val (colorBg, colorFill, colorWarn) = authenticatorTheme.expirationTimer
        expirationTimerBackground = if (savedStateHandle.get<Any?>(EXPIRATION_TIMER_BACKGROUND_COLOR) != null) savedStateHandle.get<Int>(EXPIRATION_TIMER_BACKGROUND_COLOR)!! else colorBg
        expirationTimerFill = if (savedStateHandle.get<Any?>(EXPIRATION_TIMER_FILL_COLOR) != null) savedStateHandle.get<Int>(EXPIRATION_TIMER_FILL_COLOR)!! else colorFill
        expirationTimerWarningColor = if (savedStateHandle.get<Any?>(EXPIRATION_TIMER_WARNING_COLOR) != null) savedStateHandle.get<Int>(EXPIRATION_TIMER_WARNING_COLOR)!! else colorWarn

        // Denial Reasons
        val (colorNormal, colorChecked) = authenticatorTheme.denialOptions
        denialReasonsOptionNormal = if (savedStateHandle.get<Any?>(DENIAL_REASONS_OPTION_NORMAL) != null) savedStateHandle.get<Int>(DENIAL_REASONS_OPTION_NORMAL)!! else colorNormal
        denialReasonsOptionChecked = if (savedStateHandle.get<Any?>(DENIAL_REASONS_OPTION_CHECKED) != null) savedStateHandle.get<Int>(DENIAL_REASONS_OPTION_CHECKED)!! else colorChecked

        // Auth Response Button
        val tarbUiProp = authenticatorTheme.arb
        authResponseButtonBackground = if (savedStateHandle.get<Any?>(AUTH_RESPONSE_BUTTON_BACKGROUND) != null) savedStateHandle.get<Drawable>(AUTH_RESPONSE_BUTTON_BACKGROUND) else if (tarbUiProp.useReferences) AppCompatResources.getDrawable(context, tarbUiProp.backgroundResId) else tarbUiProp.background
        authResponseButtonTextColor = if (savedStateHandle.get<Any?>(AUTH_RESPONSE_BUTTON_TEXT_COLOR) != null) savedStateHandle.get<Int>(AUTH_RESPONSE_BUTTON_TEXT_COLOR)!! else tarbUiProp.colorText
        authResponseButtonFillColor = if (savedStateHandle.get<Any?>(AUTH_RESPONSE_BUTTON_FILL_COLOR) != null) savedStateHandle.get<Int>(AUTH_RESPONSE_BUTTON_FILL_COLOR)!! else tarbUiProp.colorFill

        // Negative Auth Response Button
        val negativeTarbUiProp = authenticatorTheme.arbNegative
        negativeAuthResponseButtonBackground = if (savedStateHandle.get<Any?>(NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND) != null) savedStateHandle.get<Drawable>(NEGATIVE_AUTH_RESPONSE_BUTTON_BACKGROUND) else if (negativeTarbUiProp.useReferences) AppCompatResources.getDrawable(context, negativeTarbUiProp.backgroundResId) else negativeTarbUiProp.background
        negativeAuthResponseButtonTextColor = if (savedStateHandle.get<Any?>(NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR) != null) savedStateHandle.get<Int>(NEGATIVE_AUTH_RESPONSE_BUTTON_TEXT_COLOR)!! else negativeTarbUiProp.colorText
        negativeAuthResponseButtonFillColor = if (savedStateHandle.get<Any?>(NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR) != null) savedStateHandle.get<Int>(NEGATIVE_AUTH_RESPONSE_BUTTON_FILL_COLOR)!! else negativeTarbUiProp.colorFill

        // Auth Response Authorized Color
        authResponseAuthorizedColor = if (savedStateHandle.get<Any?>(AUTH_RESPONSE_AUTHORIZED_COLOR) != null) savedStateHandle.get<Int>(AUTH_RESPONSE_AUTHORIZED_COLOR)!! else authenticatorTheme.authResponseAuthorized

        // Auth Response Denied Color
        authResponseDeniedColor = if (savedStateHandle.get<Any?>(AUTH_RESPONSE_DENIED_COLOR) != null) savedStateHandle.get<Int>(AUTH_RESPONSE_DENIED_COLOR)!! else authenticatorTheme.authResponseDenied

        // Auth Response Failed Color
        authResponseFailedColor = if (savedStateHandle.get<Any?>(AUTH_RESPONSE_FAILED_COLOR) != null) savedStateHandle.get<Int>(AUTH_RESPONSE_FAILED_COLOR)!! else authenticatorTheme.authResponseFailed

        // Auth Content View Background

        // Auth Content View Background
        val colorBg1 = authenticatorTheme.authContentBg.colorBg
        authContentViewBackground = if (savedStateHandle.get<Any?>(AUTH_CONTENT_VIEW_BACKGROUND) != null) savedStateHandle.get<Int>(AUTH_CONTENT_VIEW_BACKGROUND)!! else colorBg1
    }
}