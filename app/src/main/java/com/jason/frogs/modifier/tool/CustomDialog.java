package com.jason.frogs.modifier.tool;

import android.content.Context;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * @author Jason
 * @time 2018/1/26
 */

public class CustomDialog {


    private MaterialDialog dialog;

    public CustomDialog(Context context) {
        dialog = new MaterialDialog(context);
    }


    public void showView(CharSequence title, CharSequence message, final OnDialogClick click) {
        if (dialog != null) {
            dialog.setTitle(title);
            dialog.setMessage(message);
            dialog.setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onDecideClick(v);
                }
            });
            dialog.show();
        }

    }

    public void showImageView(CharSequence title, View view, final OnDialogClick click) {
        if (dialog != null) {
            dialog.setTitle(title);
            dialog.setContentView(view);
            dialog.setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onDecideClick(v);
                }
            });
            dialog.show();
        }
    }


    public void dismiss() {
        dialog.dismiss();
    }


    public interface OnDialogClick {
        void onDecideClick(View v);

    }

}
