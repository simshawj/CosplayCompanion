package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import javax.inject.Inject;

/**
 * Created by james on 2/21/16.
 */
public class ListConventionsPresenterImpl implements ListConventionsPresenter {
    private ListConventionsView mView;
    private DatabaseReference mConventionsReference;

    @Inject
    public ListConventionsPresenterImpl() {
        mConventionsReference = FirebaseDatabase.getInstance().getReference("conventions");
    }

//    @Override
//    public void requestConventions() {
//        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
//        internalAPI.getConventions().enqueue(new Callback<List<Convention>>() {
//            @Override
//            public void onResponse(Call<List<Convention>> call, Response<List<Convention>> response) {
//                mView.addConventions(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Convention>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void requestNewConventions() {
//
//    }

    @Override
    public void setView(ListConventionsView view) {
        mView = view;
    }

    @Override
    public void removeView(ListConventionsView view) {
        if(mView.equals(view))
            mView = null;
    }

    @Override
    public DatabaseReference getFirebaseReference() {
        return mConventionsReference;
    }
}
