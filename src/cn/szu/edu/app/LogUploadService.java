package cn.szu.edu.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.http.Header;
import org.kymjs.kjframe.utils.FileUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.util.StringUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class LogUploadService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final File log = FileUtils.getSaveFile("OSChina", "OSCLog.log");
        String data = null;
        try {
            FileInputStream inputStream = new FileInputStream(log);
            data = StringUtils.toConvertString(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(data)) {
            OSChinaApi.uploadLog(data, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                    log.delete();
                    LogUploadService.this.stopSelf();
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                        Throwable arg3) {
                    LogUploadService.this.stopSelf();
                }
            });
        } else {
            LogUploadService.this.stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
