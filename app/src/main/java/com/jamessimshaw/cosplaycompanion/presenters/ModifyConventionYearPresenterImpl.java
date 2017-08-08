package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by james on 2/18/16.
 */
public class ModifyConventionYearPresenterImpl implements ModifyConventionYearPresenter {
    private ModifyConventionYearView mView;
    private Convention mConvention;
    private ConventionYear mConventionYear;
    private SimpleDateFormat mDateFormat;
    private Date mStartDate;
    private Date mEndDate;
    private DatabaseReference mDatabaseReference;

    @Inject
    public ModifyConventionYearPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("convention_years");
    }

    @Override
    public void setView(ModifyConventionYearView view) {
        mView = view;
    }

    @Override
    public void setConvention(Convention convention) {
        mConvention = convention;
    }

    @Override
    public void setConventionYear(ConventionYear conventionYear) {
        mConventionYear = conventionYear;
    }

    @Override
    public void detachView() {
        mView = null;
        mConvention = null;
        mConventionYear = null;
    }

    @Override
    public void requestInitialData() {
        mDateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy", Locale.getDefault());
        if (mConventionYear == null) {
            Calendar calendar = Calendar.getInstance();
            mStartDate = calendar.getTime();
            mEndDate = calendar.getTime();
        } else {
            mStartDate = mConventionYear.getStartDate();
            mEndDate = mConventionYear.getEndDate();
            mView.displayLocation(mConventionYear.getLocation());
        }

        mView.displayStartDate(mDateFormat.format(mStartDate));
        mView.displayFinishDate(mDateFormat.format(mEndDate));
    }

    @Override
    public void setStartDate(Date date) {
        mStartDate = date;
        mView.displayStartDate(mDateFormat.format(mStartDate));
    }

    @Override
    public void setFinishDate(Date date) {
        mEndDate = date;
        mView.displayFinishDate(mDateFormat.format(mEndDate));
    }

    @Override
    public void submit() {
        String location = mView.getLocation();

        if (mStartDate.after(mEndDate)) {
            mView.displayWarning("End date must be after the start date");
            return;
        }

        // TODO: Do we want to change the display name?  Make it customizable?
        String displayName = mConvention.getName() + " " + getYearFromDate(mStartDate);

        if (mConventionYear == null) {
            mConventionYear = new ConventionYear(mStartDate, mEndDate, location, displayName);
        } else {
            mConventionYear.setStart(mStartDate);
            mConventionYear.setEnd(mEndDate);
            mConventionYear.setLocation(location);
            mConventionYear.setDisplayName(displayName);
        }
        mDatabaseReference.child(mConvention.getName()).child(getYearFromDate(mStartDate)).setValue(mConventionYear);
        mView.done();
    }

    private String getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Integer.toString(calendar.get(Calendar.YEAR));
    }
}
