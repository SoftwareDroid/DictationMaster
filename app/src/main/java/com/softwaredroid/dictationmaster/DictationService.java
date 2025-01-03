package com.softwaredroid.dictationmaster;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.Rect;
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
    private Handler autoGoogleMicClickHandler;
    private HighlightOverlay highlightOverlay;
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
        textManipulator = new TextManipulator(this, this);
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
                        if (app.toString().equals("com.google.android.inputmethod.latin"))
            {
                /*if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
                {
                    String text = event.getText().toString();
                    if (text.equals("[Spracheingabe beendet]") && textManipulator.isEnabled())
                    {
                        // if the Gboard stops mic input auto start it Agai
                        clickOnGoardMic();
                    } else if (text.startsWith("[Tastatur") && text.endsWith("ausgeblendet]"))
                    {
                        // Stop clicking further
                        if (this.autoGoogleMicClickHandler != null)
                        {
                            this.autoGoogleMicClickHandler.removeCallbacks(null);
                        }
                    } else if (textManipulator.startMicInputAtKeyboardOpening() && text.startsWith("[Tastatur") && text.endsWith("wird angezeigt]"))
                    {
                        //user opened keyboard
                        clickOnGoardMic();
                    }

                }*/
            }

            switch (event.getEventType())
            {
                case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                {
                    // This event is triggered when the text in a view changes
                    AccessibilityNodeInfo source = event.getSource();
                    if (source != null)
                    {
                        CharSequence text = source.getText(); // Extract the text
                        if (text != null)
                        {
                            TextManipulator.CommandResult command = textManipulator.searchAndApplyCommands(text.toString());
                            if (command != null)
                            {
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    if (source.isEditable())
                                    {
                                        if (command.newText != null)
                                        {
                                            Bundle arguments = new Bundle();
                                            arguments.putCharSequence(AccessibilityNodeInfo
                                                    .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, command.newText);
                                            source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                                        }
                                        if (command.continueMicInput)
                                        {
//                                            this.clickOnGoardMic();
                                        }
                                    }
                                });
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
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

    public void performClickAtPosition(float x, float y, boolean highlight)
    {
        // Define the area to highlight (for example, a 100x100 square)
        Rect highlightRect = new Rect((int) x, (int) y, (int) x + 15, (int) y + 15);

        // Show the highlight
        if (highlight)
        {
            highlightOverlay = new HighlightOverlay(this);
            highlightOverlay.showHighlight(highlightRect);
            new Handler().postDelayed(() -> {
                // Simulate the click

                // Remove the highlight
                highlightOverlay.removeHighlight();
            }, 1500);
        }
        simulateClick(x, y);
    }


    private void simulateClick(float x, float y)
    {
        // Create a path for the gesture
        Path path = new Path();
        path.moveTo(x, y);

        // Create a gesture description
        GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
        // Long press duration (e.g., 500 milliseconds)
        long longPressDuration = 500; // Adjust as needed
        GestureDescription.StrokeDescription strokeDescription = new GestureDescription.StrokeDescription(path, 0, longPressDuration);
        gestureBuilder.addStroke(strokeDescription);

        // Dispatch the gesture
        GestureDescription gesture = gestureBuilder.build();
        boolean success = dispatchGesture(gesture, null, null);
    }

    @Override
    public void showNotification(String text)
    {
        showToast(text);
    }

    @Override
    public void clickOnGoardMic()
    {
        autoGoogleMicClickHandler = new Handler();
        autoGoogleMicClickHandler.postDelayed(() -> {
            performClickAtPosition(430, 780, false);
        }, 500);
    }


}

