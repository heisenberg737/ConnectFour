package a737.heisenberg.connectfour;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    public CFGrid(Context context) {
        super(context);
    }
    int x,y,cx=0,cy=0,i,j;
    int sx=Resources.getSystem().getDisplayMetrics().widthPixels;
    int sy=Resources.getSystem().getDisplayMetrics().heightPixels;
    LinearLayout layout=new LinearLayout(getContext());
    TextView textView=new TextView(getContext());
    @Override
    protected void onDraw(Canvas canvas)
    {   textView.setVisibility(View.VISIBLE);
        textView.setText("C1");
        textView.setTextSize(50);
        layout.addView(textView);
        canvas.drawColor(Color.YELLOW);
        x=Resources.getSystem().getDisplayMetrics().widthPixels;
        y=Resources.getSystem().getDisplayMetrics().heightPixels;
        layout.measure(x,y);
        for(i=(x/15);i<x&&cx<7;i=i+(x/7),cx++)
        {      cy=0;
            for (j =(y/13) ; j < y&&cy<6; j = j + (y/8),cy++) {
                canvas.drawCircle(i, j, 50, paint);
            }
        }
        layout.layout(0,0,x,y);
        canvas.translate(0,y-300);
        layout.draw(canvas);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float X=event.getX();
        float Y=event.getY();
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            Log.d("B","x="+X+"  y="+Y+"");
        }
        return false;
    }
}
