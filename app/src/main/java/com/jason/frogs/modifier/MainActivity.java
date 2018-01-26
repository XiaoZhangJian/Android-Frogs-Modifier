package com.jason.frogs.modifier;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.frogs.modifier.model.Frogs;
import com.jason.frogs.modifier.tool.CustomDialog;
import com.jason.frogs.modifier.tool.ImageTool;
import com.jason.frogs.modifier.tool.Modifier;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Jason
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.clover_et)
    EditText etClover;

    @BindView(R.id.raffle_tickets_et)
    EditText etRaffleTickets;

    @BindView(R.id.show_tv)
    TextView tvShow;

    Modifier modifier;
    private static int CLICK_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndPermission.with(this)
                .permission(Permission.STORAGE)
                .callback(listener)
                .start();
        modifier = new Modifier.Builder()
                .setFile(new File(getExternalCacheDir().getParentFile().getParentFile(),
                        "jp.co.hit_point.tabikaeru/files/GameData.sav"))
                .build();

        initView();
    }

    private void initView() {
        tvShow.setText("三叶草：" +
                getString(R.string.number, modifier.readInt(new Frogs(0xc70)))
                + "\n抽奖券："
                + getString(R.string.number, modifier.readInt(new Frogs(0xc74))));
    }


    @OnClick(R.id.submit_bt)
    public void onSubmit(View v) {
        AndPermission.with(this)
                .requestCode(CLICK_CODE)
                .permission(Permission.STORAGE)
                .callback(listener)
                .start();
    }

    @OnClick(R.id.course_bt)
    public void onCourse(View v) {
        showDialog(getString(R.string.course_bt), getString(R.string.course_str));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reward:
                rewardDialog("赏赐");
                break;
            case R.id.menu_about:
                showDialog(getString(R.string.menu_about), getString(R.string.about_str));
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void edit() {
        String cloverNum = etClover.getText().toString().trim();
        String raffleTicketsNum = etRaffleTickets.getText().toString().trim();
        modifier.writeInt(new Frogs(0xc70, Integer.valueOf(cloverNum)));
        modifier.writeInt(new Frogs(0xc74, Integer.valueOf(raffleTicketsNum)));
        Toast.makeText(MainActivity.this, getString(R.string.edit_toast), Toast.LENGTH_SHORT).show();
    }


    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            if (requestCode == CLICK_CODE) {
                edit();
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, getString(R.string.err_toast), Toast.LENGTH_SHORT).show();
        }
    };


    private void showDialog(String title, String msg) {
        final CustomDialog dialog = new CustomDialog(this);
        dialog.showView(title, msg, v -> dialog.dismiss());

    }

    private void rewardDialog(String title) {
        final CustomDialog dialog = new CustomDialog(this);

        View view = getLayoutInflater().inflate(R.layout.reward_dialog_layout, null);

        ImageView imageView = view.findViewById(R.id.image_reward);

        dialog.showImageView(title, null, view, v -> {
            Toast.makeText(MainActivity.this, "感谢各位小哥哥，小姐姐，打赏！", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        });

        imageView.setOnClickListener(v -> {

            Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_reward);
            if (ImageTool.saveImageToGallery(this,bitmap)){
                Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();

            }

        });

    }


}
