package aliasadi.memoryleak.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import aliasadi.memoryleak.R;

/**
 * Created by Ali Esa Assadi on 06/02/2018.
 */
public class InnerHandlerLeak extends AppCompatActivity {

    /*Since this Handler is declared as an inner class, it may prevent the outer class from being garbage collected.
    If the Handler is using a Looper or MessageQueue for a thread other than the main thread, then there is no issue.
    If the Handler is using the Looper or MessageQueue of the main thread, you need to fix your Handler declaration,
    as follows: Declare the Handler as a static class; In the outer class, instantiate a WeakReference to the outer class
    and pass this object to your Handler when you instantiate the Handler; Make all references to members of the outer
    class using the WeakReference object.*/

    //the handler attached to the main thread
    private final Handler mLeakyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("InnerHandlerLeak", "handle message");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //postDelayed() Causes the Runnable r to be added to the message queue, to be run after the specified amount of time elapses.
        //The runnable will be run on the thread to which this handler is attached.
        //The time-base is uptimeMillis(). Time spent in deep sleep will add an additional delay to execution.

        // Post a message and delay its execution for 10 minutes.
        mLeakyHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("InnerHandlerLeak", "in run()");
            }
        }, 1000 * 60 * 10);
    }

}