package com.jamessimshaw.cosplaycompanion.helpers;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Class with keyboard methods.
 *
 * @author James Simshaw
 */

public class KeyboardHelper {
    public static void hideKeyboard(Activity activity) {
        ((InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
