package com.social.chenl.module.setting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import androidx.appcompat.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.cl.library.base.BaseActivity;
import me.cl.library.util.ToolbarUtil;
import com.social.chenl.R;
import com.social.chenl.common.util.SPUtil;

/**
 * 设置
 * https://github.com/shellhub/blog/issues/29
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ToolbarUtil.init(mToolbar, this)
                .setTitle(R.string.action_settings)
                .setBack()
                .setTitleCenter(R.style.AppTheme_Toolbar_TextAppearance)
                .build();

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, new SettingsFragment())
//                .commit();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        private static final String OPEN_TU_PICS = "open_tu_pics";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_setting);
            initPreferences();
        }

        private void initPreferences() {
            Preference openTuPics = findPreference(OPEN_TU_PICS);
            openTuPics.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    SPUtil.build().putBoolean(OPEN_TU_PICS, (Boolean) newValue);
                    return true;
                }
            });
        }
    }

}
