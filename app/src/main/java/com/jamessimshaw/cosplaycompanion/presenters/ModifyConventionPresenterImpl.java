package com.jamessimshaw.cosplaycompanion.presenters;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;

import java.io.InputStream;

import javax.inject.Inject;

/**
 * Prensenter for creating or editing a convention.
 *
 * @author James Simshaw
 */
public class ModifyConventionPresenterImpl implements ModifyConventionPresenter {
    private Convention mConvention;
    private ModifyConventionView mView;
    private DatabaseReference mConventionsRef;
    private DatabaseReference mUsersRef;
    private DatabaseReference mConventionRef;
    private StorageReference mStorageReference;

    @Inject
    public ModifyConventionPresenterImpl() {
        mConventionsRef = FirebaseDatabase.getInstance().getReference("conventions");
        mUsersRef = FirebaseDatabase.getInstance().getReference("users");
        mStorageReference = FirebaseStorage.getInstance().getReference("logos");
    }

    // ModifyConventionPresenter methods

    @Override
    public void setView(ModifyConventionView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void requestInitialData() {
        if (mConventionRef != null) {
            mConventionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mConvention = dataSnapshot.getValue(Convention.class);
                    if(mView != null) {
                        mView.displayName(mConvention.getName());
                        mView.displayDescription(mConvention.getDescription());
                        mView.displayLogo(mConvention.getLogoUriString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void setConvention(DatabaseReference convention) {
        mConventionRef = convention;
    }

    @Override
    public void submit() {
        final String name = mView.getName();
        final String description = mView.getDescription();
        InputStream logoStream = mView.getLogo();

        if (logoStream == null) {
            storeConvention(name, description, null);
        } else {
            UploadTask uploadTask = mStorageReference.child(name).putStream(logoStream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mView.displayMessage("Failed to upload image");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storeConvention(name, description, taskSnapshot.getDownloadUrl());
                }
            });
        }
    }

    @Override
    public boolean isEditMode() {
        return mConventionRef != null;
    }

    private void storeConvention(String name, String description, Uri logo) {
        String logoUriString;
        if (name == null || name.equals("")) {
            if (mView != null) {
                mView.displayMessage("Name must be set");
            }
            return;
        }
        if (logo != null) {
           logoUriString = logo.toString();
        } else if (mConvention != null) {
            logoUriString = mConvention.getLogoUriString();
        } else {
            logoUriString = null;
        }
        if (mConvention == null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mConvention = new Convention(name, description, logoUriString, user.getUid());
            mConventionRef = mConventionsRef.push();
            mUsersRef.child(user.getUid()).child("conventions").child(mConventionRef.getKey()).setValue(true);
        } else {
            mConvention.setDescription(description);
            mConvention.setLogoUriString(logoUriString);
            mConvention.setName(name);
        }
        mConventionRef.setValue(mConvention);
        mView.done();
    }
}
