package a737.heisenberg.connectfour;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    CFGrid grid;
    Dots dots;
    boolean game=false;
    int sx,sy;
    int c,p=0,a,b;
    int w=Resources.getSystem().getDisplayMetrics().widthPixels,h=Resources.getSystem().getDisplayMetrics().heightPixels;
    int[] count={5,5,5,5,5,5,5};
    int[][] res=new int[7][6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setframe();
        sx=w/14;
        sy=h/16;

    }
    public void setframe()
    {
        RelativeLayout relativeLayout = findViewById(R.id.relativelayout);
        grid=new CFGrid(this);
        RelativeLayout.LayoutParams vp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        grid.setLayoutParams(vp);
        relativeLayout.addView(grid);
    }

    public void dropDot() {
        dots=new Dots(this);
        RelativeLayout.LayoutParams vp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        dots.setLayoutParams(vp);
        RelativeLayout relativeLayout = findViewById(R.id.relativelayout);
        relativeLayout.addView(dots);
        dots.check();
        if(p==41)
            tie();
        Log.d("Drop Dot","res[c][count[c]]="+res[c][count[c]]+"");
    }

    public void tie()
    {
        Toast.makeText(this,"The match is a tie",Toast.LENGTH_LONG).show();
    }



    public void result()
    {
        if(res[c][count[c]]==1)
            Toast.makeText(this,"RED",Toast.LENGTH_LONG).show();
        else if(res[c][count[c]]==2)
            Toast.makeText(this,"BLUE",Toast.LENGTH_LONG).show();
    }






    public class CFGrid extends View {
        private Paint paint=new Paint();
        public CFGrid(Context context)
        {
            super(context);
        }
        int cx=0,cy=0,i,j;
        int sx,sy;

        Bitmap bitmap;
        Canvas canvas;


        @Override
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if(bitmap==null)
            {
                drawGrid();
            }
            canvas.drawBitmap(bitmap,0,0,null);
        }
        public void drawGrid()
        {
            bitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
            canvas=new Canvas(bitmap);
            canvas.drawColor(Color.YELLOW);
            sx=w/14;
            sy=h/16;
            paint.setColor(Color.BLACK);
            for(i = sx ; i < w && cx < 7 ; i = i + (w/7) , cx++)
            {      cy=0;
                for (j = sy ; j < h && cy<6; j = j + (h/8) , cy++)
                {
                    canvas.drawCircle(i, j, 50, paint);
                }
            }

        }


        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float X=event.getX();
            float Y=event.getY();
            c = (int) Math.floor(7*(X)/w);
            c = Math.abs(c);
            if(event.getAction()==MotionEvent.ACTION_DOWN)
            {   if(X<=(40+sx)||Y<=(40+sy))
            {
                game=true;
                dropDot();
            }
            }
//            if(p%2==0)
//                res[c][count[c]]=1;
//            else if(p%2==1)
//                res[c][count[c]]=2;
            return true;
        }

    }




    public class Dots extends View {

        private Paint red=new Paint();
        private Paint blue=new Paint();
        public Dots(Context context) {
            super(context);
            red.setColor(Color.RED);
            blue.setColor(Color.BLUE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (p % 2== 0 && count[c]>=0) {
                canvas.drawCircle((sx) + c * (w) / 7, (sy) + (count[c]) * (h) / 8, 50, red);
                res[c][count[c]]=1; //updated res here
                Log.d("C4","rec[c][count[c]]="+res[c][count[c]]+"");
                count[c] = count[c] - 1;
                p++;

            } else if (p %2 == 1 && count[c]>=0) {
                canvas.drawCircle((sx) + c * (w) / 7, (sy) + (count[c]) * (h) / 8, 50, blue);
                res[c][count[c]]=2;  //updated res here
                Log.d("C4","rec[c][count[c]]="+res[c][count[c]]+"");
//                d[c]++;
                count[c] = count[c] - 1;
                p++;
            }



        }


        public void check() {
            a = c;
            b = count[c];
            Log.d("Check", " res[a][b]=" + res[a][b]); //Although res is updated, but it's not reflected here. res[a][b] is 0 here.

                if (b > 2 && res[a][b] == res[a][b - 1] && res[a][b] == res[a][b - 2] && res[a][b] == res[a][b - 3])
                    result();
                for (int i = 0; i < 4; i++) {
                    if (res[i][b] == res[a][b] && res[i + 1][b] == res[a][b] && res[i + 2][b] == res[a][b] && res[i + 3][b] == res[a][b])
                        result();
                }
//        if(a-b>=0)
//        {
//            for(int i=0;i<4&&b-a+i<3;i++)
//            {
//                if(res[i][b-a+i]==res[a][b]&&res[i+1][b-a+i+1]==res[a][b]&&res[i+2][b-a+i+2]==res[a][b]&&res[i+3][b-a+i+3]==res[a][b])
//                    result();
//            }
//        }
//        else
//        {
//            for(int i=5;i>2&&i>a+b-3;i--)
//            {
//                if(res[i][a + b - i] == res[a][b] && res[i - 1][a + b - i + 1] == res[a][b] && res[i - 2][a + b - i + 2] == res[a][b] && res[i - 3][a + b - i + 3] == res[a][b])
//                    result();
//            }
//        }

        }


    }





}


