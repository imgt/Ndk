package com.lg.ndk

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.lg.ndk.listener.NativeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.setText(stringFromJNI())
        registerListener(object : NativeListener {
            override fun showLog(level: Int, msg: String?) {
                tv.setText("$level----$msg")
            }
        })

        btn.setOnClickListener {
          init(sample_text.text.toString()+"回调")
        }

        sample_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length > 0) {
                    init(s.toString()+"回调")
                }
            }
        })


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    /**
     * java调用C++提供的接口
     * @param config
     */
    external fun init(config: String)

    /**
     * java注册C++的回调接口
     * @param listener
     */
    external fun registerListener(listener: NativeListener)

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-app")
        }
    }
}
