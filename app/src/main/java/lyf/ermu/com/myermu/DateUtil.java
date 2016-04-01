package lyf.ermu.com.myermu;

public class DateUtil
{
    public static String date(int second)
    {
        String hh = null;
        String mm = null;
        String ss = null;
        int h = 0;
        int m = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600)
        {
            h = second / 3600;
            if (temp != 0)
            {
                if (temp > 60)
                {
                    m = temp / 60;
                    if (temp % 60 != 0)
                    {
                        s = temp % 60;
                    }
                }
                else
                {
                    s = temp;
                }
            }
        }
        else
        {
            m = second / 60;
            if (second % 60 != 0)
            {
                s = second % 60;
            }
        }
        if (h < 10)
        {
            if (h < 1)
            {
                hh = "00";
            }
            else
            {
                hh = "0" + h;
            }
        }
        else
        {
            hh = h + "";
        }
        
        if (m < 10)
        {
            if (m < 1)
            {
                mm = "00";
            }
            else
            {
                mm = "0" + m;
            }
        }
        else
        {
            mm = m + "";
        }
        if (s < 10)
        {
            if (s < 1)
            {
                ss = "00";
            }
            else
            {
                ss = "0" + s;
            }
        }
        else
        {
            ss = s + "";
        }
        
        return hh + ":" + mm + ":" + ss;
    }
}
