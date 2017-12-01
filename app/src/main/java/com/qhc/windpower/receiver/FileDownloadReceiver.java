package com.qhc.windpower.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;


import com.qhc.windpower.helper.AppPreferences;
import com.qhc.windpower.helper.ToastHelper;
import com.qhc.windpower.utils.AppUtil;

import java.io.File;

/**
 * Created by zongdongdong on 2016/8/15.
 * DownloadManager下载，广播监听
 */

public class FileDownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            long appUpdateId = AppPreferences.getAppUpdateId();
            if (downLoadId == appUpdateId) {//提示用户是否安装
                    Uri downloadFileUri = manager.getUriForDownloadedFile(downLoadId);
                if(downloadFileUri != null){
//                    Log.i("update", downloadFileUri.toString());
                    String u = Environment.getExternalStorageDirectory() + "/Download/nhs_update.apk";
                    AppUtil.installApk(context, Uri.fromFile(new File(u)));
//                    AppUtils.installApk(context, downloadFileUri);
                }else{
                    ToastHelper.showToast(context, "版本更新失败");
                }
//                if(AppPreferences.isForceUpdateAPP()){
//                    AppUtils.installApk(context,downloadFileUri);
//                }else{
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("哪划算App版本更新");
//                    builder.setMessage("现在是否需要安装?");
//                    builder.setCancelable(true);
//                    builder.setPositiveButton("安装", (dialog, which) -> AppUtils.installApk(context,downloadFileUri));
//                    builder.setNegativeButton("取消",null);
//                    AlertDialog dialog = builder.create();
//                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    dialog.show();
//                }
            }
        } else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {

        }
    }
}
