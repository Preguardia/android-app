package com.preguardia.app.consultation.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/20/16.
 */
public class NewConsultationFragment extends Fragment {

    @Bind(R.id.consultation_new_category)
    RelativeLayout categoryView;

    @Bind(R.id.consultation_new_category_content)
    TextView categoryContentView;
    @Bind(R.id.consultation_new_where_content)
    TextView whereContentView;
    @Bind(R.id.consultation_new_lasttime_content)
    TextView lastTimeContentView;

    public static NewConsultationFragment newInstance(int param) {
        NewConsultationFragment fragment = new NewConsultationFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_new, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.consultation_new_category)
    public void onCategoryClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_category)
                .items(R.array.consultation_category_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        categoryContentView.setText(text);
                        categoryContentView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                    }
                })
                .show();
    }

    @OnClick(R.id.consultation_new_where)
    public void onWhereClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_where)
                .items(R.array.consultation_where_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {


                        whereContentView.setText(text);
                        whereContentView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                    }
                })
                .show();
    }

    @OnClick(R.id.consultation_new_lasttime)
    public void onLastTimeClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_lasttime)
                .items(R.array.consultation_lasttime_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {


                        lastTimeContentView.setText(text);
                        lastTimeContentView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                    }
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
