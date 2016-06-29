package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by james on 10/15/15.
 */
public class TimePickerDialogFragment extends DialogFragment {
    private TimePickerDialog.OnTimeSetListener mListener;

    public TimePickerDialogFragment() {
        // Required default constructor
    }

    public void setListener(TimePickerDialog.OnTimeSetListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), mListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
