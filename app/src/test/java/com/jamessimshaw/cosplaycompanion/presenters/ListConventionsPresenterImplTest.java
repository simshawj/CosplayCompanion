package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.BuildConfig;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by james on 3/22/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ListConventionsPresenterImplTest {

    private ListConventionsView mView;
    private ListConventionsPresenterImpl mPresenter;

    @Before
    public void setup() {
        mView = mock(ListConventionsView.class);

        mPresenter = new ListConventionsPresenterImpl(mView);
    }

    @Test
    public void testRequestConventions() throws Exception {

    }
}
