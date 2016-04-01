package lyf.ermu.com.myermu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cms.media.record.cmsAudioRecorder;
import com.cms.media.widget.VideoView;
import com.lyy.lyyapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/24.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private cmsAudioRecorder audioRecorder;
    private VideoView mVideoView;
    private Button button2;
    private lyyapi api;
    private Button button;
    private String playPath;
    private int ResultCode;
    private ImageView mTalk_to2;
    private String name;
    private String deviceid;
    private TextView Dbs_name;
    private String url;
    private LinearLayout back;
    private ImageView full_scr;
    private LinearLayout buttom;
    private RelativeLayout title;
    private boolean isFull;
    private Button rec;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_er);

        initView();
        //登录羚羊平台
        api.loginLyy("536946349_0_1461806094_82c3ececf093752b55d8e52a47516de5", "[Config]\\r\\nIsDebug=0\\r\\nLocalBasePort=8200\\r\\nIsCaptureDev=1\\r\\nIsPlayDev=1\\r\\nUdpSendInterval=2\\r\\nConnectTimeout=10000\\r\\nTransferTimeout=10000\\r\\n[Tracker]\\r\\nCount=3\\r\\nIP1=121.42.156.148\\r\\nPort1=80\\r\\nIP2=182.254.149.39\\r\\nPort2=80\\r\\nIP3=203.195.157.248\\r\\nPort3=80\\r\\n[LogServer]\\r\\nCount=1\\r\\nIP1=120.26.74.53\\r\\nPort1=80\\r\\n", null);
        getPlayStream();
        mTalk_to2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("---action down-----");
                        mTalk_to2.setBackgroundResource(R.mipmap.icon_button_press);
//                        mVideoView[0].mute(1);
                        // 对讲代码
                        audioRecorder = new cmsAudioRecorder(api);
                        audioRecorder.startRecord();
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        SystemClock.sleep(1000);
                        System.out.println("---action up-----");
                        audioRecorder.stopRecord();
//                        mVideoView[0].mute(0);
                        mTalk_to2.setBackgroundResource(R.mipmap.icon_button);

                        break;
                }
                return true;
            }
        });
    }

    private void getPlayStream() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DEVICE, new Response.Listener<String>() {
            public void onResponse(String s) {
                Log.i("aa", "get请求成功" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    url = jsonObject.getString("url");
                    ResultCode = api.connRtmpCam(url);
                    if (ResultCode == 0) {
                        playPath = api.getLyyPlayPath();
                        mVideoView.playVideo(playPath, false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("aa", "get请求失败" + volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "liveplay");
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("deviceid", deviceid);
                return map;
            }
        };
        request.setTag("getPlayStream");
        MyApplication.getHttpQueues().add(request);

    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view_new);
        mTalk_to2 = (ImageView) findViewById(R.id.push_talk_new);
        full_scr = (ImageView) findViewById(R.id.full_scr);
        Dbs_name = (TextView) findViewById(R.id.Dbs_name_new);
        back = (LinearLayout) findViewById(R.id.back);
        buttom = (LinearLayout) findViewById(R.id.buttom);
        title = (RelativeLayout) findViewById(R.id.title);
        rec = (Button) findViewById(R.id.rec);

        rec.setOnClickListener(this);
        full_scr.setOnClickListener(this);
        back.setOnClickListener(this);

        name = getIntent().getStringExtra("name");
        deviceid = getIntent().getStringExtra("deviceid");
        System.out.println("deviceid====" + deviceid);

        Dbs_name.setText(name);

        api = new lyyapi();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.button:
                int iTag = Integer.parseInt(v.getTag().toString());
                if (iTag == 2) {
                    ((Button) v).setText("停止播放");
                    v.setTag(3);
                    //播放
                    ResultCode = api.connRtmpCam("rtmp://61.55.189.151:1935/live/537002266_134283008_1459407578_c4618d36c95c79ecf09aefd63277da58");
                    // api.connP2PCam(devToken, trackIp, trackPort);
                    if (ResultCode == 0) {
                        playPath = api.getLyyPlayPath();
                        mVideoView.playVideo(playPath, false);
                    }
                } else {
                    ((Button) v).setText("播放");
                    v.setTag(2);
                    mVideoView.stopPlayback(true);//停止播放
                    api.lyyDisConn();//如果没有这行代码 再点击播放就没效果
                }
                break;*/
            case R.id.rec:
//                //实例化SelectPicPopupWindow
//                menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
//                //显示窗口
//                menuWindow.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                Intent i =new Intent(this,RecActivity.class);
                startActivity(i);
                break;
            case R.id.full_scr:
                isFull = true;
                buttom.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                break;
            case R.id.back:
                finish();
                break;
            /*case R.id.button2:
                int iTag1 = Integer.parseInt(v.getTag().toString());
                if (iTag1 == 0) {
                    ((Button) v).setText("停止对讲");
                    v.setTag(1);
                    // 对讲代码
                    audioRecorder = new cmsAudioRecorder(api);
                    audioRecorder.startRecord();
                    mVideoView.mute(1);

                } else {
                    ((Button) v).setText("开始对讲");
                    v.setTag(0);
                    audioRecorder.stopRecord();
                    mVideoView.mute(0);
                }
                break;*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback(true);//停止播放
        api.lyyDisConn();//如果没有这行代码 再点击播放就没效果
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isFull = true) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                buttom.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                isFull = false;
            } else {
                finish();
            }

            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
