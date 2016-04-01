package lyf.ermu.com.myermu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //    private XListView mCamera_list;
    private LinearLayout mBack;
    private String access_token = "1.TYPtb1NjiFpHRiBQK9lk.2592000.1458698396.e21c9f3ee1f01e26f4433c23e56c272a";
    private List<Camera> cameraList;
    private CameraAdapter adapter;
    private String description;
    private ListView Camera_list;
    private String deviceid;
    private Dialog dialog;

    private final Timer timer = new Timer();
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();

        getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent inten = new Intent(this, TestActivity.class);
        inten.putExtra("name", cameraList.get(position).getDescription());
        inten.putExtra("deviceid", cameraList.get(position).getDeviceid());
        startActivity(inten);

    }

    private void getList() {
        final Dialog dialog = CreateDialog.createLoadingDialog(this, "正在加载，请稍后...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DEVICE, new Response.Listener<String>() {
            public void onResponse(String s) {
                Log.i("aa", "get请求成功" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String request_id = jsonObject.getString("request_id");
                    String count = jsonObject.getString("count");
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        deviceid = jsonObject2.getString("deviceid");
                        String stream_id = jsonObject2.getString("stream_id");
                        String status = jsonObject2.getString("status");
                        description = jsonObject2.getString("description");
                        String thumbnail = jsonObject2.getString("thumbnail");
                        System.out.println(description + "description=======");
                        System.out.println(deviceid + "description=======");

                        Camera camera = new Camera(request_id, count, deviceid, stream_id, status, description, thumbnail);
                        cameraList.add(camera);

                    }
                    adapter = new CameraAdapter(ListActivity.this, cameraList);
//                   mCamera_list.setAdapter(adapter);
                    Camera_list.setAdapter(adapter);
                    dialog.cancel();
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
                map.put("method", "list");
                map.put("access_token", access_token);

                return map;
            }
        };
        request.setTag("getList");
        MyApplication.getHttpQueues().add(request);
    }


    private void initView() {

//        mCamera_list = (XListView) this.findViewById(R.id.listView_camera);
        Camera_list = (ListView) this.findViewById(R.id.listView_camera);
        mBack = (LinearLayout) this.findViewById(R.id.back);

//        mCamera_list.setOnItemClickListener(this);
        Camera_list.setOnItemClickListener(this);
        mBack.setOnClickListener(this);

        cameraList = new ArrayList<>();


    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("getList");
    }


}
