package lyf.ermu.com.myermu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

@SuppressLint("ClickableViewAccessibility")
public class ScrollListenerHorizontalScrollView extends HorizontalScrollView
{
    
    public ScrollListenerHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    public ScrollListenerHorizontalScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public ScrollListenerHorizontalScrollView(Context context)
    {
        super(context);
    }
    
    public interface ScrollViewListener
    {
        
        void onScrollChanged(ScrollType scrollType);
        
    }
    
    private Handler mHandler;
    
    private ScrollViewListener scrollViewListener;
    
    /**
     * ����״̬ IDLE ����ֹͣ TOUCH_SCROLL ��ָ�϶����� FLING����
     */
    enum ScrollType
    {
        IDLE, TOUCH_SCROLL, ScrollType, FLING
    };
    
    /**
     * ��¼��ǰ�����ľ���
     */
    private int currentX = -9999999;
    
    /**
     * ��ǰ����״̬
     */
    private ScrollType scrollType = ScrollType.IDLE;
    
    /**
     * �����������
     */
    private int scrollDealy = 50;
    
    /**
     * ��������runnable
     */
    private Runnable scrollRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (getScrollX() == currentX)
            {
                // ����ֹͣ ȡ�������߳�
                Log.d("", "ֹͣ����");
                scrollType = ScrollType.IDLE;
                if (scrollViewListener != null)
                {
                    scrollViewListener.onScrollChanged(scrollType);
                }
                mHandler.removeCallbacks(this);
                return;
            }
            else
            {
                // ��ָ�뿪��Ļ view���ڹ�����ʱ��
                Log.d("", "Fling����������");
                scrollType = ScrollType.FLING;
                if (scrollViewListener != null)
                {
                    scrollViewListener.onScrollChanged(scrollType);
                }
            }
            currentX = getScrollX();
            mHandler.postDelayed(this, scrollDealy);
        }
    };
    
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                scrollViewListener.onScrollChanged(scrollType);
                // ��ָ�������ƶ���ʱ�� ȡ�����������߳�
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                // ��ָ�ƶ���ʱ��
                mHandler.post(scrollRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }
    
    @Override
    public void scrollTo(int x, int y)
    {
        super.scrollTo(x, y);
    }
    
    /**
     * �����ȵ��������������Handler ��Ȼ�����
     * 
     * @TODO
     */
    public void setHandler(Handler handler)
    {
        this.mHandler = handler;
    }
    
    /**
     * ���ù�������
     * 
     * @TODO
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener)
    {
        this.scrollViewListener = listener;
    }
    
}
