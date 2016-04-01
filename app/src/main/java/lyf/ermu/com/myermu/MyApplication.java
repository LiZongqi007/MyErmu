package lyf.ermu.com.myermu;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MyApplication extends Application {
    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
//        setId(NAME); //初始化全局变量
    }

    public static RequestQueue getHttpQueues() {
        return queues;
    }

    private static final String NAME = "MyApplication";
}
