package lyf.ermu.com.myermu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecActivity extends AppCompatActivity {

    // 时间刻度的布局
    private LinearLayout flipper1;

    // 横向滑动的scroll
    private ScrollListenerHorizontalScrollView scrollView;

    // 整個時間軸的总长度
    private int maxlength = 0;

    // 可滑动最大距离
    private int scroll_max = 0;

    // 屏幕的宽度
    private int phone_wegith;

    // 用于HorizontalScrollView监听事件的handler
    private Handler handler = new Handler();

    // 每一个单位的秒数
    private int m;

    private DelayThread thread;

    private TextView now_time;

    private List<View> mlist_view;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        flipper1 = (LinearLayout) findViewById(R.id.flipper1);
        now_time = (TextView) findViewById(R.id.now_time);
        scrollView = (ScrollListenerHorizontalScrollView) findViewById(R.id.view);
        scrollView.setHandler(handler);
        mlist_view = new ArrayList<View>();
        // 获取手机屏幕的宽度
        WindowManager wm = this.getWindowManager();
        phone_wegith = wm.getDefaultDisplay().getWidth();
        scrollView.setScrollContainer(false);
        for (int i = 0; i < 28; i++) {
            addView(i);
        }
        thread = new DelayThread();
        thread.start();
        scrollView.setOnScrollStateChangedListener(new ScrollListenerHorizontalScrollView.ScrollViewListener() {


            @Override
            public void onScrollChanged(final ScrollListenerHorizontalScrollView.ScrollType scrollType) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        now_time.setText(DateUtil.date(m * scrollView.getScrollX()));
                        if (scrollType == ScrollListenerHorizontalScrollView.ScrollType.IDLE) {
                            // 停止滑动的状态
                            m = 86400 / scroll_max;
                        }
                        if (scrollType == ScrollListenerHorizontalScrollView.ScrollType.FLING) {
                            // 处于正在滑动的状态
                        }
                    }
                };
            }

            /*@Override
            public void onScrollChanged(final ScrollType scrollType)
            {
                handler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        super.handleMessage(msg);
                        now_time.setText(DateUtil.date(m * scrollView.getScrollX()));
                        if (scrollType == ScrollType.IDLE)
                        {
                            // 停止滑动的状态
                            m = 86400 / scroll_max;
                        }
                        if (scrollType == ScrollType.FLING)
                        {
                            // 处于正在滑动的状态
                        }
                    }
                };
            }*/
        });

        String end_time = "14:29:30";
        String start_time = "10:30:20";
        int index1 = start_time.indexOf(":");

        String hh = start_time.substring(0, index1);

        int index3 = end_time.indexOf(":");

        String hh1 = end_time.substring(0, index3);

        for (int i = Integer.valueOf(hh); i <= Integer.valueOf(hh1); i++) {
            if (i == Integer.valueOf(hh)) {
                ((ProgressBar) mlist_view.get(i + 2)).setProgress(serProgress(start_time));
                ((ProgressBar) mlist_view.get(i + 2)).setSecondaryProgress(3600);
            } else if (i == Integer.valueOf(hh1)) {
                ((ProgressBar) mlist_view.get(i + 2)).setProgress(0);
                ((ProgressBar) mlist_view.get(i + 2)).setSecondaryProgress(serProgress(end_time));
            } else {
                ((ProgressBar) mlist_view.get(i + 2)).setProgress(0);
                ((ProgressBar) mlist_view.get(i + 2)).setSecondaryProgress(3600);
            }
        }
        // 86400

        for (int i = 0; i < scroll_max; i++) {
            int f = 86400 / scroll_max * i;
            int g = 86400 / scroll_max * (i + 1);

            if (f <= (Integer.valueOf(hh) * 3600 + serProgress(start_time)) && (Integer.valueOf(hh) * 3600 + serProgress(start_time)) < g) {
                Log.d("insert", i + "==============");
                scrollView.scrollTo(i, 0);
            }
        }
    }

    private int serProgress(String time) {
        int index1 = time.indexOf(":");
        int index2 = time.indexOf(":", index1 + 1);
        String mm = time.substring(index1 + 1, index2);
        String ss = time.substring(index2 + 1);
        int m = Integer.parseInt(mm) * 60;
        return m + Integer.valueOf(ss);
    }

    /**
     * 添加时间轴的布局
     */
    private void addView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.item, null);
        TextView text = (TextView) view.findViewById(R.id.zheng);
        text.setText((i - 1) + ":");
        TextView ling = (TextView) view.findViewById(R.id.ling);
        ProgressBar play_line = (ProgressBar) view.findViewById(R.id.progressBar1);
        RelativeLayout line = (RelativeLayout) view.findViewById(R.id.line);
        RelativeLayout shu = (RelativeLayout) view.findViewById(R.id.shu);

        // 获取view的宽高 此方法会加载onMeasure三次
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        maxlength = maxlength + width;
        if (i == 0) {
            ling.setVisibility(View.INVISIBLE);
            line.setVisibility(View.INVISIBLE);
            play_line.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
            // phone_wegith / 2 - width 屏幕一半的宽度 减去view的宽度 得到的值给予第一个view 从而使得时间轴正好对着初始0的位置
            ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(phone_wegith / 2 - width, RelativeLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(l);
        } else if (i == 1) {
            ling.setVisibility(View.INVISIBLE);
            line.setVisibility(View.INVISIBLE);
            play_line.setVisibility(View.INVISIBLE);
        } else if (i == 26) {
            text.setVisibility(View.INVISIBLE);
            line.setVisibility(View.INVISIBLE);
            shu.setVisibility(View.INVISIBLE);
            play_line.setVisibility(View.INVISIBLE);
        } else if (i == 27) {
            // phone_wegith / 2 - width 屏幕一半的宽度 减去view的宽度 得到的值给予第一个view 从而使得时间轴正好对着最后24的位置
            ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(phone_wegith / 2 - width, RelativeLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(l);
            text.setVisibility(View.INVISIBLE);
            line.setVisibility(View.INVISIBLE);
            shu.setVisibility(View.INVISIBLE);
            play_line.setVisibility(View.INVISIBLE);
            ling.setVisibility(View.INVISIBLE);
        } else {
            play_line.setVisibility(View.VISIBLE);
            scroll_max = scroll_max + width;
        }

        mlist_view.add(play_line);
        flipper1.addView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 线程用于监控滑动状态 以及后去滑动后的值
     */
    class DelayThread extends Thread {
        public void run() {
            while (true) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }
}
