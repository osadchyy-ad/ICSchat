package com.google.firebase.codelab.friendlychat.app;

import android.util.Log;

public class Logger {
    public static void log(Object text) {
        Log.d("AndroidRuntime", text.toString());
    }
}
