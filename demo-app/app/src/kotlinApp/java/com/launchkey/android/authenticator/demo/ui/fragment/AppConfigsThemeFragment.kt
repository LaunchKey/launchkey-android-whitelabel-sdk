package com.launchkey.android.authenticator.demo.ui.fragment

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentConfigsThemeBinding
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentConfigsThemeItemBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.util.*

class AppConfigsThemeFragment : BaseDemoFragment<DemoFragmentConfigsThemeBinding>(R.layout.demo_fragment_configs_theme) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DemoFragmentConfigsThemeBinding.bind(view)
        val appConfigsThemeViewModel = ViewModelProvider(requireParentFragment()).get(AppConfigsThemeViewModel::class.java)
        val items = Arrays.asList(
                ThemeItem("App Bar", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAppBarBackground()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAppBarBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAppBarItemColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAppBarItemColor(value)
                            }
                        })
                ),
                ThemeItem("Background", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.background!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setBackground(value)
                            }
                        })
                ),
                ThemeItem("Background Overlay", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getBackgroundOverlay()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setBackgroundOverlay(value)
                            }
                        })
                ),
                ThemeItem("Button", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.button!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setButton(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getButtonText()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setButtonText(value)
                            }
                        })
                ),
                ThemeItem("Button Negative", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.buttonNegative!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setButtonNegative(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getButtonNegativeText()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setButtonNegativeText(value)
                            }
                        })
                ),
                ThemeItem("List Headers", Arrays.asList(
                        object : ThemeItem.ConfigItem.BooleanConfigItem() {
                            override fun getThemeValue(): Boolean {
                                return appConfigsThemeViewModel.getListHeadersVisibility() == View.VISIBLE
                            }

                            override fun setThemeValue(value: Boolean) {
                                appConfigsThemeViewModel.setListHeadersVisibility(if (value) View.VISIBLE else View.GONE)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getListHeadersColorBackground()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setListHeadersColorBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getListHeadersColorText()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setListHeadersColorText(value)
                            }
                        })
                ),
                ThemeItem("Auth Method Icons", Arrays.asList(
                        object : ThemeItem.ConfigItem.BooleanConfigItem() {
                            override fun getThemeValue(): Boolean {
                                return appConfigsThemeViewModel.getFactorsSecurityIconsVisibility() == View.VISIBLE
                            }

                            override fun setThemeValue(value: Boolean) {
                                appConfigsThemeViewModel.setFactorsSecurityIconsVisibility(if (value) View.VISIBLE else View.GONE)
                            }
                        },
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.factorsSecurityIconsPINCodeIcon!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setFactorsSecurityIconsPINCodeIcon(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.factorsSecurityIconsCircleCodeIcon!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setFactorsSecurityIconsCircleCodeIcon(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.factorsSecurityIconsGeofencesIcon!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setFactorsSecurityIconsGeofencesIcon(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.factorsSecurityIconsWearablesIcon!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setFactorsSecurityIconsWearablesIcon(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.factorsSecurityIconsBiometricIcon!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setFactorsSecurityIconsBiometricIcon(value)
                            }
                        })
                ),
                ThemeItem("Help Menu Items", Arrays.asList(
                        object : ThemeItem.ConfigItem.BooleanConfigItem() {
                            override fun getThemeValue(): Boolean {
                                return if (appConfigsThemeViewModel.getHelpMenuItemsVisibility() == View.VISIBLE) true else false
                            }

                            override fun setThemeValue(value: Boolean) {
                                appConfigsThemeViewModel.setHelpMenuItemsVisibility(if (value) View.VISIBLE else View.GONE)
                            }
                        })
                ),
                ThemeItem("PIN Code", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.pinCodeColorBackground!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setPINCodeColorBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.pinCodeColorText
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.pinCodeColorText = value
                            }
                        })
                ),
                ThemeItem("Circle Code", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getCircleCodeHighlightColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setCircleCodeHighlightColor(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getCircleCodeTickColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setCircleCodeTickColor(value)
                            }
                        })
                ),
                ThemeItem("Settings Headers", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.settingsHeadersBackground!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setSettingsHeadersBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getSettingsHeadersText()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setSettingsHeadersText(value)
                            }
                        })
                ),
                ThemeItem("Edit Text", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getEditTextHint()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setEditTextHint(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getEditTextText()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setEditTextText(value)
                            }
                        })
                ),
                ThemeItem("Expiration Timer", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getExpirationTimerBackground()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setExpirationTimerBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getExpirationTimerFill()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setExpirationTimerFill(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getExpirationTimerWarningColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setExpirationTimerWarningColor(value)
                            }
                        })
                ),
                ThemeItem("Denial Reasons", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getDenialReasonsOptionNormal()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setDenialReasonsOptionNormal(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getDenialReasonsOptionChecked()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setDenialReasonsOptionChecked(value)
                            }
                        })
                ),
                ThemeItem("Auth Response Button", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.authResponseButtonBackground!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthResponseButtonBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAuthResponseButtonTextColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthResponseButtonTextColor(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAuthResponseButtonFillColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthResponseButtonFillColor(value)
                            }
                        })
                ),
                ThemeItem("Auth Response Button Negative", Arrays.asList(
                        object : ThemeItem.ConfigItem.DrawableConfigItem() {
                            override fun getThemeValue(): Drawable {
                                return appConfigsThemeViewModel.negativeAuthResponseButtonBackground!!
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setNegativeAuthResponseButtonBackground(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getNegativeAuthResponseButtonTextColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setNegativeAuthResponseButtonTextColor(value)
                            }
                        },
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getNegativeAuthResponseButtonFillColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setNegativeAuthResponseButtonFillColor(value)
                            }
                        })
                ),
                ThemeItem("Auth Response Authorized Color", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAuthResponseAuthorizedColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthResponseAuthorizedColor(value)
                            }
                        })
                ),
                ThemeItem("Auth Response Denied Color", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAuthResponseDeniedColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthResponseDeniedColor(value)
                            }
                        })
                ),
                ThemeItem("Auth Response Failed Color", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAuthResponseFailedColor()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthResponseFailedColor(value)
                            }
                        })
                ),
                ThemeItem("Auth Content View Background", Arrays.asList(
                        object : ThemeItem.ConfigItem.ColorConfigItem() {
                            override fun getThemeValue(): Int {
                                return appConfigsThemeViewModel.getAuthContentViewBackground()
                            }

                            override fun setThemeValue(value: Int) {
                                appConfigsThemeViewModel.setAuthContentViewBackground(value)
                            }
                        })
                )
        )


        // Don't convert this into a lambda, converting to kotlin will fail
        binding!!.themeList.adapter = ThemeAdapter(items, object : ThemeAdapter.OnClickListener {
            override fun onClick(themeItem: ThemeItem, configItem: ThemeItem.ConfigItem<*, *>) {
                if (configItem is ThemeItem.ConfigItem.ColorConfigItem || configItem is ThemeItem.ConfigItem.DrawableConfigItem) {
                    val colorPickupPopupBuilder = ColorPickerDialog.Builder(requireActivity())
                            .attachAlphaSlideBar(true)
                            .attachBrightnessSlideBar(true)
                    if (configItem is ThemeItem.ConfigItem.ColorConfigItem) {
                        val initialColor = configItem.getThemeValue()!!
                        colorPickupPopupBuilder.colorPickerView.setInitialColor(initialColor)
                    }
                    // Currently we don't set the color wheel to the drawable color since I don't know how
                    // to do that
//            else if (configItem instanceof ThemeItem.ConfigItem.DrawableConfigItem) {
//
//            }
                    colorPickupPopupBuilder
                            .setPositiveButton("Choose", ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
                                (configItem as ThemeItem.ConfigItem<*, Int?>).setThemeValue(envelope.color)
                                binding!!.themeList.adapter!!.notifyItemChanged(items.indexOf(themeItem))
                            })
                            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
                            .show()
                } else if (configItem is ThemeItem.ConfigItem.BooleanConfigItem) {
                    configItem.setThemeValue(!configItem.getThemeValue()!!)
                    binding!!.themeList.adapter!!.notifyItemChanged(items.indexOf(themeItem))
                }
            }
        })
        binding!!.themeList.layoutManager = LinearLayoutManager(requireActivity())
        binding!!.themeList.setHasFixedSize(true)
    }

    internal class ThemeItem(val itemName: String, val configItems: List<ConfigItem<*, *>>) {
        internal interface ConfigItem<T, V> {
            fun getThemeValue(): T
            fun setThemeValue(value: V)
            abstract class ColorConfigItem : ConfigItem<Int, Int>
            abstract class DrawableConfigItem : ConfigItem<Drawable, Int>
            abstract class BooleanConfigItem : ConfigItem<Boolean, Boolean>
        }
    }

    internal class ThemeAdapter(private val items: List<ThemeItem>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(DemoFragmentConfigsThemeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position], onClickListener)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        internal class ViewHolder(private val binding: DemoFragmentConfigsThemeItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(themeItem: ThemeItem,
                     onClickListener: OnClickListener) {
                binding.themeName.text = themeItem.itemName

                // Rebinding adds more views so we need to remove the previous ones
                binding.themeItems.removeAllViews()
                // Need to create a variable number of ImageView based on the variable number of ConfigItem's
                for (configItem in themeItem.configItems) {
                    if (configItem is ThemeItem.ConfigItem.ColorConfigItem || configItem is ThemeItem.ConfigItem.DrawableConfigItem) {
                        val imageView = ImageView(binding.root.context)
                        if (configItem is ThemeItem.ConfigItem.ColorConfigItem) {
                            imageView.setImageDrawable(ColorDrawable(configItem.getThemeValue()!!))
                        } else if (configItem is ThemeItem.ConfigItem.DrawableConfigItem) {
                            imageView.setImageDrawable(configItem.getThemeValue())
                        }
                        imageView.background = ContextCompat.getDrawable(imageView.context, R.drawable.outline)
                        binding.themeItems.addView(imageView, FrameLayout.LayoutParams(TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 25f,
                                imageView.context.resources.displayMetrics).toInt(), FrameLayout.LayoutParams.MATCH_PARENT))
                        imageView.setOnClickListener { v: View? -> onClickListener.onClick(themeItem, configItem) }
                    } else if (configItem is ThemeItem.ConfigItem.BooleanConfigItem) {
                        val toggleButton = ToggleButton(binding.root.context)
                        binding.themeItems.addView(toggleButton, FrameLayout.LayoutParams(TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 50f,
                                toggleButton.context.resources.displayMetrics).toInt(), FrameLayout.LayoutParams.MATCH_PARENT))
                        toggleButton.setOnCheckedChangeListener(null)
                        toggleButton.isChecked = configItem.getThemeValue()!!
                        toggleButton.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean -> onClickListener.onClick(themeItem, configItem) }
                    }
                }
            }
        }

        internal interface OnClickListener {
            fun onClick(themeItem: ThemeItem, configItem: ThemeItem.ConfigItem<*, *>)
        }
    }
}