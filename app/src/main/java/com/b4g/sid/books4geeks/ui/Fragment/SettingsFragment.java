package com.b4g.sid.books4geeks.ui.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.b4g.sid.books4geeks.R;
import com.b4g.sid.books4geeks.Util.FileUtil;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {


    private static final int BACKUP_KEY = 9;
    private static final int RESTORE_KEY = 10;

    private static final String DB_NAME = "bookDB.db";
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference backupPreference = findPreference(getString(R.string.pref_key_backup));
        backupPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_DIR);

                intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(intent,BACKUP_KEY);
                Toast.makeText(getActivity(),R.string.backup_select,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference restorePreference = findPreference(getString(R.string.pref_key_restore));
        restorePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

                intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(intent,RESTORE_KEY);
                Toast.makeText(getActivity(),R.string.restore_select,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==BACKUP_KEY && resultCode == Activity.RESULT_OK){
            File fromFile = getActivity().getDatabasePath(DB_NAME);
            File toFile = new File(data.getData().getPath()+ File.separator + "Books4Geeks" + (System.currentTimeMillis()/1000) + ".bak");
            try {
                FileUtil.copyFile(fromFile,toFile);
                Toast.makeText(getActivity(),R.string.backup_complete,Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),R.string.backup_failed,Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode==RESTORE_KEY && resultCode == Activity.RESULT_OK){
            File fromFile = new File(data.getData().getPath());
            if(FileUtil.isValidDbFile(fromFile)){
                try {
                    File toFile = getActivity().getDatabasePath(DB_NAME);
                    FileUtil.copyFile(fromFile,toFile);
                    Toast.makeText(getActivity(),R.string.restore_complete,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),R.string.restore_failed,Toast.LENGTH_SHORT).show();
                }
            }
            else Toast.makeText(getActivity(),R.string.restore_invalid,Toast.LENGTH_SHORT).show();
        }
    }
}
