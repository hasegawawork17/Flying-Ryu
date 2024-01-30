package flying.ryu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Dragon dragon;
    private Handler handler;
    private Runnable r;
    private ArrayList<Pillars> arrPillars;
    private int sumpillar,distance;
    private int score,bestscore;
    private boolean start;
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        score = 0;
        bestscore = 0;
        start = false;
        initDragon();
        initPillar();

    }



    private void initDragon() {
        dragon= new Dragon();
        dragon.setWidth(200*Constants.SCREEN_WIDTH/1080);
        dragon.setHeight(200*Constants.SCREEN_HEIGHT/1920);
        dragon.setX(100*Constants.SCREEN_WIDTH/1080);
        dragon.setY(Constants.SCREEN_HEIGHT/2-dragon.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.dragon1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.dragon2));
        dragon.setArrBms(arrBms);
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }


    private void initPillar() {
        sumpillar =6;
        distance = 300*Constants.SCREEN_HEIGHT/1920;
        arrPillars= new ArrayList<>();
        for (int i = 0; i< sumpillar; i++ ){
            if(i<sumpillar/2) {
                this.arrPillars.add(new Pillars(Constants.SCREEN_WIDTH + i * ((Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH / 1080) / (sumpillar / 2)),
                        0, 200 * Constants.SCREEN_WIDTH / 1080, Constants.SCREEN_HEIGHT / 2));
                this.arrPillars.get(this.arrPillars.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pillar2));
                this.arrPillars.get(this.arrPillars.size() - 1).randomY();


            }else{
                this.arrPillars.add(new Pillars(this.arrPillars.get(i-sumpillar/2).getX(),this.arrPillars.get(i-sumpillar/2).getY()
                        +this.arrPillars.get(i-sumpillar/2).getHeight()+this.distance,200*Constants.SCREEN_WIDTH/1080,Constants.SCREEN_HEIGHT/2));
                this.arrPillars.get(this.arrPillars.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pillar1));



            }
        }

    }
    @Override
    public void draw(@NonNull Canvas canvas){
        super.draw(canvas);
        if(start){
            dragon.draw(canvas);
            for(int i = 0; i < sumpillar; i++){

                if(dragon.getRect().intersect(arrPillars.get(i).getRect())||dragon.getY()-dragon.getHeight()<0||dragon.getY()>Constants.SCREEN_HEIGHT){
                    Pillars.speed = 0;
                    MainActivity.ScoreOver.setText(MainActivity.Scoreboard.getText());
                    MainActivity.BestScore.setText("best:"+bestscore);
                    MainActivity.Scoreboard.setVisibility(INVISIBLE);
                    MainActivity.rl_game_over.setVisibility(VISIBLE);
                }
                if(this.dragon.getX()+this.dragon.getWidth()>arrPillars.get(i).getX()+arrPillars.get(i).getWidth()/2
                        &&this.dragon.getX()+this.dragon.getWidth()<=arrPillars.get(i).getX()+arrPillars.get(i).getWidth()/2+Pillars.speed
                        &&i<sumpillar/2){
                    score++;
                    MainActivity.Scoreboard.setText(""+score);
                }
                if(this.arrPillars.get(i).getX()<-arrPillars.get(i).getWidth()){
                    this.arrPillars.get(i).setX(Constants.SCREEN_WIDTH);
                    if(i < sumpillar/2) {
                        arrPillars.get(i).randomY();
                    }else{
                        arrPillars.get(i).setY(arrPillars.get(i-sumpillar/2).getY()
                                +this.arrPillars.get(i-sumpillar/2).getHeight() +this.distance);
                    }

                }
                this.arrPillars.get(i).draw(canvas);
            }

        }else{
            if(dragon.getY()>Constants.SCREEN_HEIGHT/2){
                dragon.setDrop(-15*Constants.SCREEN_HEIGHT/1920);
            }
            dragon.draw(canvas);
        }

        handler.postDelayed(r, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            dragon.setDrop(-15);
        }
        return true;
    }


    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        MainActivity.Scoreboard.setText("0");
        score=0;
        initPillar();
        initDragon();
    }
}
