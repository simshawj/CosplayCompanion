package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by james on 3/22/16.
 */
public class ListConventionsPresenterImplTest {

    private ListConventionsView mView;
    private ListConventionsPresenterImpl mPresenter;

    @Before
    public void setup() {
        mView = mock(ListConventionsView.class);

        mPresenter = new ListConventionsPresenterImpl(mView);
    }
}
