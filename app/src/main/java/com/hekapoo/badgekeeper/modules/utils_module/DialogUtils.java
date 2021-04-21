package com.hekapoo.badgekeeper.modules.utils_module;


import android.app.AlertDialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hekapoo.badgekeeper.ui_drivers.create_account_driver.CreateAccount2UIDriver;

/*
 *   This class handles the creation of dialogs
*/
public class DialogUtils {
    private static DialogUtils instance = null;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (instance == null)
            instance = new DialogUtils();
        return instance;
    }

    public void buildAndShowPickerDialog(String title, String[] data, Context ctx, TextView affectedView){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctx);
        builderSingle.setTitle(title);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ctx, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(data);

        builderSingle.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, which) -> affectedView.setText( arrayAdapter.getItem(which)));
        builderSingle.show();
    }
}
