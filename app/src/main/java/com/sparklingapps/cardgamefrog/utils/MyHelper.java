package com.sparklingapps.cardgamefrog.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.sparklingapps.cardgamefrog.BuildConfig;
import com.sparklingapps.cardgamefrog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by basiljose on 9/14/2018.
 */

public class MyHelper {

    public static final String APPCONSTANT_SHAREDPREFERENCENAME = "ZioMeetAppPreference";
    private static MediaPlayer mediaPlayer;
    private static final String TAG = "MyHelper";
    @Nullable
    private static ProgressDialog sProgressDialog;
    @Nullable
    private static AlertDialog.Builder builder;

    private static TypedArray mLanguageFlag;
    private static String[] mLanguageName;
    private static String[] mLanguageCode;




    /**
     * method to check if android version is lollipop
     *
     * @return this return value
     */
    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    /**
     * BUILD TYPE CHECKING
     */

    public static boolean isDEBUG() {
        return BuildConfig.DEBUG;
    }

    /**
     * method to get color
     *
     * @param context this is the first parameter for getColor  method
     * @param id      this is the second parameter for getColor  method
     * @return return value
     */
    public static int getColor(Context context, int id) {
        if (isAndroid5()) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static void showSnackBar(Context mContext, View mView, String snackMessage, int backgroundColor, int textColor) {
        try {

            Snackbar snackbar = Snackbar.make(mView, snackMessage, Snackbar.LENGTH_LONG);
            View snackView = snackbar.getView();
            snackView.setBackgroundColor(ContextCompat.getColor(mContext, backgroundColor));
            TextView snackTextView = snackView.findViewById(R.id.snackbar_text);
            snackTextView.setText(snackMessage);
            snackTextView.setTextColor(ContextCompat.getColor(mContext, textColor));
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void speakOutOffline(Context context, int resId) {

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId);
            mediaPlayer.start();
        } else {
            try {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, resId);
                mediaPlayer.start();
            } catch (Exception exp) {
                Log.d(TAG, "speakOutOffline: " + exp);
            }
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
            }
        });
    }


    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo.isConnected();
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void logCat(String message) {
        Log.d(TAG, message.toString());
    }

    /**
     * Show Progress bar from any activity
     *
     * @param activity
     */

    public static void showProgress(@Nullable Activity activity, boolean withLogo) {

        try {
            if (null != activity) {
                if (null == sProgressDialog) {
                    activity.runOnUiThread(() -> {
                        sProgressDialog = new ProgressDialog(activity);
                        sProgressDialog.setProgressStyle(R.style.MBillProgressTheme);

                        sProgressDialog.setIndeterminate(true);
                        sProgressDialog.setCancelable(false);
                        if (sProgressDialog.getWindow() != null) {
                            sProgressDialog.getWindow()
                                    .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        }

                        try {
                            if (!sProgressDialog.isShowing() && sProgressDialog != null) {
                                sProgressDialog.show();
                            }
                            if (withLogo) { //this checking will add zoi logo with the dialog box
                                sProgressDialog.setContentView(R.layout.basil_progress_bar_dialog);
                            } else {

                            }
                        } catch (WindowManager.BadTokenException e) {
                            e.printStackTrace();
                        }
                    });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To dismiss the progress
     */
    public static void hideProgress() {
        try {
            if (null != sProgressDialog) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To dismiss keyboard
     */
    public static void hideKeyBoard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * show the keyboard to edit the text from dialogBox
     */
    public static void showKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }



    /**
     * @param email
     * @return true or false
     */
    public static boolean isValidEmail(String email) {
        return !(email == null || email.isEmpty()) && Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }



    public static String encodeData(String data) {
        byte[] encodedData = Base64.encode(data.getBytes(), Base64.DEFAULT);
        return new String(encodedData);
    }

    public static String decodeData(String data) {
        byte[] decodedData = Base64.decode(data.getBytes(), Base64.DEFAULT);
        return new String(decodedData);
    }

    public static Locale getLocale(Context context){
        Locale locale;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        }else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    /**
     * method for format the given unformatted date into a give format
     *
     * @param format sample format of the output
     * @param locale based on the local it for mat the date
     * @param date   unformatted date object
     * @return return the formatted date&time in String
     */
    public static String formatDateString(String format, Locale locale, Date date) {
        SimpleDateFormat sdfTime = new SimpleDateFormat(format, locale);
        sdfTime.setTimeZone(TimeZone.getDefault());
        return sdfTime.format(date);
    }

    /**
     * method for format the given date
     *
     * @param format sample format of the output
     * @param locale based on the local it for mat the date
     * @param date   unformatted date object
     * @return return the formatted date&time in date object
     */
    public static String formatDate(String format, Locale locale, Date date) {
        SimpleDateFormat sdfTime = new SimpleDateFormat(format, locale);
        return sdfTime.format(date);
    }

    /**
     * method for format the given unformatted date into a give format
     *
     * @param format sample format of the output
     * @param locale based on the local it for mat the date
     * @param date   unformatted date object
     * @return return the formatted date&time in String
     */
    public static String formatLocalTime(String format, Locale locale, Date date) {
        SimpleDateFormat sdfTime = new SimpleDateFormat(format, locale);
        sdfTime.setTimeZone(TimeZone.getDefault());
        return sdfTime.format(date);
    }

    /**
     * method for convert the given date&time to UTC time format
     *
     * @param sampleFormat sample format of the output
     * @param locale       based on the local it for mat the date
     * @param meetingDate  unformatted date object
     * @return return the formatted date&time in date object
     */
    public static Date parseUtcTime(String sampleFormat, Locale locale, String meetingDate) throws ParseException {
        SimpleDateFormat input = new SimpleDateFormat(sampleFormat, locale);
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        return input.parse(meetingDate);
    }




    /**
     * for splitting the time from the long value
     */
    public static String getTimeFromLong(long date) {
        long seconds = Math.abs(date / 1000 % 60);
        long minutes = Math.abs(date / (60 * 1000) % 60);
        long hours = Math.abs(date / (60 * 60 * 1000));

        String hoursString = "";

        if(hours > 0) {
            hoursString = ((hours < 10) ? "0" + hours : "" + hours ) + ":";
        }

        return hoursString + ((minutes < 10) ? "0" + minutes : minutes) + ":" + (seconds<10 ? "0" +seconds : seconds );
    }



}
