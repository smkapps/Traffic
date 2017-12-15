package hw.traffic;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import hw.traffic.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    int durationGreen = 1000;
    int durationYellow = 1000;
    int durationRed = 1000;

    ActivityMainBinding binding;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int color = msg.getData().getInt("light");
                Log.d("tag", "handleMessage: " + color);
                switch (color) {
                    case 0:
                        binding.green.setImageResource(R.color.colorGreen);
                        if (msg.what == 0) binding.green.setImageResource(R.color.colorGray);
                        break;
                    case 1:
                        binding.yellow.setImageResource(R.color.colorYellow);
                        if (msg.what == 0) binding.yellow.setImageResource(R.color.colorGray);
                        break;
                    case 2:
                        binding.red.setImageResource(R.color.colorRed);
                        if (msg.what == 0) binding.red.setImageResource(R.color.colorGray);
                        break;
                }

                binding.counter.setText(msg.what + "");


                return true;
            }
        });

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });


    }

    void play() {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2 ; i++) {
                    workInBackground();
                }

            }

        });

        worker.start();
    }

    void workInBackground() {
        boolean stop = false;
        Bundle bundle = new Bundle();
        int counter = 0;
        while (!stop){
            Message message = new Message();
            bundle.clear();
            bundle.putInt("light", 0);
        }





        while (!stop && durationGreen >= 0) {
            Message message = new Message();
            bundle.clear();
            bundle.putInt("light", 0);
            message.setData(bundle);
            message.what = durationGreen / 1000;
            handler.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            durationGreen -= 1000;
        }

        while (!stop && durationYellow >= 0) {
            Message message = new Message();
            bundle.clear();
            bundle.putInt("light", 1);
            message.setData(bundle);
            message.what = durationYellow / 1000;
            handler.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            durationYellow -= 1000;
        }

        while (!stop && durationRed >= 0) {
            Message message = new Message();
            bundle.clear();
            bundle.putInt("light", 2);
            message.setData(bundle);
            message.what = durationRed / 1000;
            handler.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            durationRed -= 1000;
        }

    }


}
