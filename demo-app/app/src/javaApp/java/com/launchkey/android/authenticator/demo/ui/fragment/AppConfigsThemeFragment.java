package com.launchkey.android.authenticator.demo.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentConfigsThemeBinding;
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentConfigsThemeItemBinding;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.Arrays;
import java.util.List;

public class AppConfigsThemeFragment extends BaseDemoFragment<DemoFragmentConfigsThemeBinding> {
    public AppConfigsThemeFragment() {
        super(R.layout.demo_fragment_configs_theme);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DemoFragmentConfigsThemeBinding.bind(view);

        final AppConfigsThemeViewModel appConfigsThemeViewModel = new ViewModelProvider(requireParentFragment()).get(AppConfigsThemeViewModel.class);
        final List<ThemeItem> items = Arrays.asList(
                new ThemeItem("App Bar", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAppBarBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAppBarBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() {
                                return appConfigsThemeViewModel.getAppBarItemColor();
                            }

                            @Override
                            public void setThemeValue(@NonNull Integer value) {
                                appConfigsThemeViewModel.setAppBarItemColor(value);
                            }
                        })
                ),
                new ThemeItem("Background", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setBackground(value); }
                        })
                ),
                new ThemeItem("Background Overlay", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getBackgroundOverlay(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setBackgroundOverlay(value); }
                        })
                ),
                new ThemeItem("Button", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getButton(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setButton(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getButtonText(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setButtonText(value); }
                        })
                ),
                new ThemeItem("Button Negative", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getButtonNegative(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setButtonNegative(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getButtonNegativeText(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setButtonNegativeText(value); }
                        })
                ),
                new ThemeItem("List Headers", Arrays.asList(
                        new ThemeItem.ConfigItem.BooleanConfigItem() {
                            @NonNull
                            @Override
                            public Boolean getThemeValue() { return appConfigsThemeViewModel.getListHeadersVisibility() == View.VISIBLE; }
                            @Override
                            public void setThemeValue(@NonNull Boolean value) { appConfigsThemeViewModel.setListHeadersVisibility(value ? View.VISIBLE : View.GONE); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getListHeadersColorBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setListHeadersColorBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getListHeadersColorText(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setListHeadersColorText(value); }
                        })
                ),
                new ThemeItem("Auth Method Icons", Arrays.asList(
                        new ThemeItem.ConfigItem.BooleanConfigItem() {
                            @NonNull
                            @Override
                            public Boolean getThemeValue() { return appConfigsThemeViewModel.getFactorsSecurityIconsVisibility() == View.VISIBLE; }
                            @Override
                            public void setThemeValue(@NonNull Boolean value) { appConfigsThemeViewModel.setFactorsSecurityIconsVisibility(value ? View.VISIBLE : View.GONE); }
                        },
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getFactorsSecurityIconsPINCodeIcon(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setFactorsSecurityIconsPINCodeIcon(value); }
                        },
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getFactorsSecurityIconsCircleCodeIcon(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setFactorsSecurityIconsCircleCodeIcon(value); }
                        },
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getFactorsSecurityIconsGeofencesIcon(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setFactorsSecurityIconsGeofencesIcon(value); }
                        },
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getFactorsSecurityIconsWearablesIcon(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setFactorsSecurityIconsWearablesIcon(value); }
                        },
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getFactorsSecurityIconsBiometricIcon(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setFactorsSecurityIconsBiometricIcon(value); }
                        })
                ),
                new ThemeItem("Help Menu Items", Arrays.asList(
                        new ThemeItem.ConfigItem.BooleanConfigItem() {
                            @NonNull
                            @Override
                            public Boolean getThemeValue() { return appConfigsThemeViewModel.getHelpMenuItemsVisibility() == View.VISIBLE ? true : false; }
                            @Override
                            public void setThemeValue(@NonNull Boolean value) { appConfigsThemeViewModel.setHelpMenuItemsVisibility(value ? View.VISIBLE : View.GONE); }
                        })
                ),
                new ThemeItem("PIN Code", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getPINCodeColorBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setPINCodeColorBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getPINCodeColorText(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setPINCodeColorText(value); }
                        })
                ),
                new ThemeItem("Circle Code", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getCircleCodeHighlightColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setCircleCodeHighlightColor(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getCircleCodeTickColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setCircleCodeTickColor(value); }
                        })
                ),
                new ThemeItem("Settings Headers", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getSettingsHeadersBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setSettingsHeadersBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getSettingsHeadersText(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setSettingsHeadersText(value); }
                        })
                ),
                new ThemeItem("Edit Text", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getEditTextHint(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setEditTextHint(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getEditTextText(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setEditTextText(value); }
                        })
                ),
                new ThemeItem("Expiration Timer", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getExpirationTimerBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setExpirationTimerBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getExpirationTimerFill(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setExpirationTimerFill(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getExpirationTimerWarningColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setExpirationTimerWarningColor(value); }
                        })
                ),
                new ThemeItem("Denial Reasons", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getDenialReasonsOptionNormal(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setDenialReasonsOptionNormal(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getDenialReasonsOptionChecked(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setDenialReasonsOptionChecked(value); }
                        })
                ),
                new ThemeItem("Auth Response Button", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getAuthResponseButtonBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthResponseButtonBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAuthResponseButtonTextColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthResponseButtonTextColor(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAuthResponseButtonFillColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthResponseButtonFillColor(value); }
                        })
                ),
                new ThemeItem("Auth Response Button Negative", Arrays.asList(
                        new ThemeItem.ConfigItem.DrawableConfigItem() {
                            @NonNull
                            @Override
                            public Drawable getThemeValue() { return appConfigsThemeViewModel.getNegativeAuthResponseButtonBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setNegativeAuthResponseButtonBackground(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getNegativeAuthResponseButtonTextColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setNegativeAuthResponseButtonTextColor(value); }
                        },
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getNegativeAuthResponseButtonFillColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setNegativeAuthResponseButtonFillColor(value); }
                        })
                ),
                new ThemeItem("Auth Response Authorized Color", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAuthResponseAuthorizedColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthResponseAuthorizedColor(value); }
                        })
                ),
                new ThemeItem("Auth Response Denied Color", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAuthResponseDeniedColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthResponseDeniedColor(value); }
                        })
                ),
                new ThemeItem("Auth Response Failed Color", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAuthResponseFailedColor(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthResponseFailedColor(value); }
                        })
                ),
                new ThemeItem("Auth Content View Background", Arrays.asList(
                        new ThemeItem.ConfigItem.ColorConfigItem() {
                            @NonNull
                            @Override
                            public Integer getThemeValue() { return appConfigsThemeViewModel.getAuthContentViewBackground(); }
                            @Override
                            public void setThemeValue(@NonNull Integer value) { appConfigsThemeViewModel.setAuthContentViewBackground(value); }
                        })
                )
        );


        // Don't convert this into a lambda, converting to kotlin will fail
        binding.themeList.setAdapter(new ThemeAdapter(items, new ThemeAdapter.OnClickListener() {
            @Override
            public void onClick(@NonNull ThemeItem themeItem, @NonNull ThemeItem.ConfigItem configItem) {
                if (configItem instanceof ThemeItem.ConfigItem.ColorConfigItem || configItem instanceof ThemeItem.ConfigItem.DrawableConfigItem) {
                    final ColorPickerDialog.Builder colorPickupPopupBuilder = new ColorPickerDialog.Builder(requireActivity())
                            .attachAlphaSlideBar(true)
                            .attachBrightnessSlideBar(true);
                    if (configItem instanceof ThemeItem.ConfigItem.ColorConfigItem) {
                        final int initialColor = ((ThemeItem.ConfigItem.ColorConfigItem) configItem).getThemeValue();
                        colorPickupPopupBuilder.getColorPickerView().setInitialColor(initialColor);
                    }
                    // Currently we don't set the color wheel to the drawable color since I don't know how
                    // to do that
//            else if (configItem instanceof ThemeItem.ConfigItem.DrawableConfigItem) {
//
//            }
                    colorPickupPopupBuilder
                            .setPositiveButton("Choose", (ColorEnvelopeListener) (envelope, fromUser) -> {
                                ((ThemeItem.ConfigItem<?, Integer>)configItem).setThemeValue(envelope.getColor());
                                binding.themeList.getAdapter().notifyItemChanged(items.indexOf(themeItem));
                            })
                            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                            .show();
                } else if (configItem instanceof ThemeItem.ConfigItem.BooleanConfigItem) {
                    configItem.setThemeValue(!((ThemeItem.ConfigItem.BooleanConfigItem)configItem).getThemeValue());
                    binding.themeList.getAdapter().notifyItemChanged(items.indexOf(themeItem));
                }
            }
        }));
        binding.themeList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.themeList.setHasFixedSize(true);
    }

    static class ThemeItem {
        interface ConfigItem<T, V> {
            @NonNull
            T getThemeValue();
            void setThemeValue(@NonNull final V value);

            abstract class ColorConfigItem implements ConfigItem<Integer, Integer> {}
            abstract class DrawableConfigItem implements ConfigItem<Drawable, Integer> {}
            abstract class BooleanConfigItem implements ConfigItem<Boolean, Boolean> {}
        }
        @NonNull
        final List<ConfigItem> configItems;
        @NonNull
        final String itemName;
        ThemeItem(@NonNull final String itemName, @NonNull final List<ConfigItem> configItems) {
            this.itemName = itemName;
            this.configItems = configItems;
        }
    }

    static class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
        @NonNull
        private final List<ThemeItem> items;
        @NonNull
        private final OnClickListener onClickListener;
        public ThemeAdapter(@NonNull final List<ThemeItem> items, @NonNull final OnClickListener onClickListener) {
            this.items = items;
            this.onClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            return new ViewHolder(DemoFragmentConfigsThemeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.bind(items.get(position), onClickListener);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @NonNull
            private final DemoFragmentConfigsThemeItemBinding binding;
            public ViewHolder(@NonNull final DemoFragmentConfigsThemeItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
            public void bind(@NonNull final ThemeItem themeItem,
                             @NonNull final OnClickListener onClickListener) {
                binding.themeName.setText(themeItem.itemName);

                // Rebinding adds more views so we need to remove the previous ones
                binding.themeItems.removeAllViews();
                // Need to create a variable number of ImageView based on the variable number of ConfigItem's
                for (final ThemeItem.ConfigItem configItem : themeItem.configItems) {
                    if (configItem instanceof ThemeItem.ConfigItem.ColorConfigItem || configItem instanceof ThemeItem.ConfigItem.DrawableConfigItem) {
                        final ImageView imageView = new ImageView(binding.getRoot().getContext());
                        if (configItem instanceof ThemeItem.ConfigItem.ColorConfigItem) {
                            imageView.setImageDrawable(new ColorDrawable(((ThemeItem.ConfigItem.ColorConfigItem) configItem).getThemeValue()));
                        } else if (configItem instanceof ThemeItem.ConfigItem.DrawableConfigItem) {
                            imageView.setImageDrawable(((ThemeItem.ConfigItem.DrawableConfigItem) configItem).getThemeValue());
                        }
                        imageView.setBackground(ContextCompat.getDrawable(imageView.getContext(), R.drawable.outline));
                        binding.themeItems.addView(imageView, new FrameLayout.LayoutParams((int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                25,
                                imageView.getContext().getResources().getDisplayMetrics()), FrameLayout.LayoutParams.MATCH_PARENT));
                        imageView.setOnClickListener(v -> onClickListener.onClick(themeItem, configItem));
                    } else if (configItem instanceof ThemeItem.ConfigItem.BooleanConfigItem) {
                        final ToggleButton toggleButton = new ToggleButton(binding.getRoot().getContext());
                        binding.themeItems.addView(toggleButton, new FrameLayout.LayoutParams((int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                50,
                                toggleButton.getContext().getResources().getDisplayMetrics()), FrameLayout.LayoutParams.MATCH_PARENT));
                        toggleButton.setOnCheckedChangeListener(null);
                        toggleButton.setChecked(((ThemeItem.ConfigItem.BooleanConfigItem) configItem).getThemeValue());
                        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            onClickListener.onClick(themeItem, configItem);
                        });
                    }
                }
            }
        }

        interface OnClickListener {
            void onClick(@NonNull final ThemeItem themeItem, @NonNull final ThemeItem.ConfigItem configItem);
        }
    }
}
