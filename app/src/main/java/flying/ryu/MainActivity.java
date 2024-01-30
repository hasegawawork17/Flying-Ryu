package flying.ryu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView Scoreboard,BestScore,ScoreOver;
    public static RelativeLayout rl_game_over;
    public static Button Start;
    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH= dm.widthPixels;
        Constants.SCREEN_HEIGHT= dm.heightPixels;
        setContentView(R.layout.activity_main);
        ScoreOver = findViewById(R.id.ScoreOver);
        Scoreboard = findViewById(R.id.Scoreboard);
        BestScore = findViewById(R.id.BestScore);
        rl_game_over =findViewById(R.id.rl_game_over);
        gv = findViewById(R.id.gv);
        Start = findViewById(R.id.Start);

        Start.setOnClickListener(v -> {
            //gv = new GameView(this, null);
            //gv.reset();
            gv.setStart(true);
            Scoreboard.setVisibility(View.VISIBLE);
            Start.setVisibility(View.INVISIBLE);
        });

        rl_game_over.setOnClickListener(v -> {
            Start.setVisibility(View.VISIBLE);
            rl_game_over.setVisibility(View.INVISIBLE);
            gv.setStart(false);
            gv.reset();
        });


    }
}