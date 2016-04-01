package lyf.ermu.com.myermu;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Author liyanfei
 * Created by Administrator on 2016/3/30.
 */
public class CameraAdapter extends BaseAdapter {
    private Context context;
    private List<Camera> cameraList;
    private String url;

    public CameraAdapter(Context context, List<Camera> cameraList) {
        this.context = context;
        this.cameraList = cameraList;

    }

    public int getCount() {
        return cameraList.size();
    }

    public Object getItem(int position) {
        return cameraList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_camera, null);
            holder = new ViewHolder();
            holder.camera_name = (TextView) convertView.findViewById(R.id.camera_name);
            holder.is_no_online = (ImageView) convertView.findViewById(R.id.is_no_online);
            holder.camera_list_pic = (ImageView) convertView.findViewById(R.id.camera_list_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.camera_name.setText(cameraList.get(position).getDescription());
        if (cameraList.get(position).getThumbnail().equals("")) {
            holder.camera_list_pic.setImageResource(R.mipmap.image6);
        } else {
            Picasso.with(context).load(cameraList.get(position).getThumbnail()).into(holder.camera_list_pic);
            /*url = cameraList.get(position).getThumbnail();
            getHeadImage();*/

        }

        return convertView;
    }
    //带缓存的
  /*  private void getHeadImage() {
        Log.i("图片========", url);
        ImageRequest request3 = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.i("response", "response成功获取");
                holder.camera_list_pic.setImageBitmap(response);

            }
        }, 100, 100, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holder.camera_list_pic.setBackgroundResource(R.mipmap.ic_launcher);
            }
        });
        request3.setTag("getHeadImage");
        MyApplication.getHttpQueues().add(request3);
    }*/

    static class ViewHolder {
        private TextView camera_name;
        private ImageView is_no_online;
        private ImageView camera_list_pic;
    }

    public void setDataChanged(List<Camera> cameraList) {
        this.cameraList = cameraList;
        this.notifyDataSetChanged();
    }
}
