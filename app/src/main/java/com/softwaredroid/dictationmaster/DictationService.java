package com.softwaredroid.dictationmaster;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

// https://developer.android.com/guide/topics/ui/accessibility/service

/**
 * NOTE: do not rename class then app_dev.sh doesn't work anymore
 */
public class DictationService extends AccessibilityService implements IDictationService
{
    private static final String CHANNEL_ID = "MyForegroundServiceChannel";
    private Handler handler = new Handler(Looper.getMainLooper());
    private CrashHandler crashHandler;
    private HashSet<String> appliedApps;
    private TextManipulator textManipulator;
    private boolean worksInAnyApp = true;
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onServiceConnected()
    {
        // setup crash handler
        crashHandler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        appliedApps = new HashSet<>();
        appliedApps.add("ru.vsms");
        textManipulator = new TextManipulator(this,this);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)
    {
        if (event == null)
        {
            return;
        }
        CharSequence app = event.getPackageName();
        if (app != null && (worksInAnyApp || appliedApps.contains(app.toString())))
        {


            switch (event.getEventType())
            {
                case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                    // This event is triggered when the text in a view changes
                    AccessibilityNodeInfo source = event.getSource();
                    if (source != null)
                    {
                        CharSequence text = source.getText(); // Extract the text
                        if (text != null)
                        {
                            String newText = textManipulator.searchAndApplyCommands(text.toString());
                            if (newText != null)
                            {
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    if (source.isEditable())
                                    {
                                        Bundle arguments = new Bundle();
                                        arguments.putCharSequence(AccessibilityNodeInfo
                                                .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, newText);
                                        source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                                    }
                                });
                            }
                        }
                    }
                    break;
                default:
                    break;
//                case AccessibilityEvent.TYPE_VIEW_FOCUSED:
//                    // This event is triggered when a view gains focus
//                    AccessibilityNodeInfo focusedSource = event.getSource();
//                    if (focusedSource != null)
//                    {
//                        CharSequence focusedText = focusedSource.getText(); // Extract the text if available
//                        if (focusedText != null)
//                        {
//                            // Handle the focused text
//                            Log.d("AccessibilityService", "Focused text: " + focusedText.toString());
//                        }
//                    }
//                    break;

                // Handle other event types as needed
            }
        }
    }


    @Override
    public void onInterrupt()
    {
        Log.d("a", "a");
    }

    private void showToast(String message)
    {
        handler.post(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void showNotification(String text)
    {
        showToast(text);
    }
}
