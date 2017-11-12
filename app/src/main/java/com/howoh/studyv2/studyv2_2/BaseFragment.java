package com.howoh.studyv2.studyv2_2;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;

/**
 * Created by howoh on 2017-11-03.
 */

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String message) {
        if(message == null) {
            message = getString(R.string.loading);
        }

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
