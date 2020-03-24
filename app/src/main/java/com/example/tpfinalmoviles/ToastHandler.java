package com.example.tpfinalmoviles;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

//con clase static funciona del mismo modo. TOAST solo se maneja en UI, por eso esta clase. https://stackoverflow.com/questions/40436179/show-toast-inside-asynctask
public enum ToastHandler {
        INSTANCE;

        private final Handler handler = new Handler(Looper.getMainLooper());

        public void showToast(final Context context, final String message, final int length) {
            handler.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, message, length).show();
                        }
                    }
            );
        }

        public static ToastHandler get() {
            return INSTANCE;
        }
}
