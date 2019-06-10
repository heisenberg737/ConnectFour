package a737.heisenberg.connectfour;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CFGrid extends View{
    private Paint paint=new Paint();
    private Paint red=new Paint();
    private Paint blue=new Paint();
    public CFGrid(Context context) {
        super(context);
    }
    int w,h,cx=0,cy=0,i,j,c,p=0;
    boolean game=false;
    int sx,sy;
    int[] count={6,6,6,6,6,6,6};
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float X=event.getX();
        float Y=event.getY();
        c= (int) Math.floor(7*(X)/w);
        c=Math.abs(c);
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {   if(X<=(40+sx)||Y<=(40+sy))
               {
                   game=true;
                   invalidate();
               }

        }
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {   canvas.drawColor(Color.YELLOW);
        paint.setColor(Color.BLACK);
        red.setColor(Color.RED);
        blue.setColor(Color.BLUE);
        w=Resources.getSystem().getDisplayMetrics().widthPixels;
        h=Resources.getSystem().getDisplayMetrics().heightPixels;
        sx=w/14;
        sy=h/16;
        for(i=sx;i<w&&cx<7;i=i+(w/7),cx++)
        {      cy=0;
            for (j =sy ; j < h&&cy<6; j = j + (h/8),cy++)
            {
                canvas.drawCircle(i, j, 50, paint);
            }
        }
        if(game)
        {   if(p==0)
            {
            canvas.drawCircle(sx + c * w / 7, sy + count[c] * h / 8, 50, red);
            count[c] = count[c] - 1;
            p=1;
            }
            else if(p==1)
            { canvas.drawCircle(sx + c * w / 7, sy + count[c] * h / 8, 50, blue);
              count[c] = count[c]-1;
              p=0;
            }
        }

    }

}
