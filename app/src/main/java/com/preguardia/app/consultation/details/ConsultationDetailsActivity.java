package com.preguardia.app.consultation.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.preguardia.app.R;
import com.preguardia.app.consultation.details.generic.GenericItem;
import com.preguardia.app.consultation.details.message.MessageItem;
import com.preguardia.app.consultation.details.message.MessageItem2;
import com.preguardia.app.consultation.details.picture.PictureItem;
import com.preguardia.app.consultation.details.title.TitleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/27/16.
 */
public class ConsultationDetailsActivity extends AppCompatActivity {

    @Bind(R.id.consultation_details_toolbar)
    Toolbar toolbar;
    @Bind(R.id.consultation_details_list)
    RecyclerView detailsList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_details);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.consultation_details_title);


        final List<GenericItem> itemList = new ArrayList<>();

        itemList.add(new TitleItem("Titulo del síntoma o enfermedad"));
        itemList.add(new MessageItem("Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen."));
        itemList.add(new PictureItem("test"));

        itemList.add(new MessageItem("test"));
        itemList.add(new MessageItem2("Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen."));
        itemList.add(new MessageItem2("test 21sa sadsd"));
        itemList.add(new MessageItem("test"));
        itemList.add(new MessageItem("test"));


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        detailsList.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        detailsList.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DetailsListAdapter(itemList);
        detailsList.setAdapter(mAdapter);

        detailsList.scrollToPosition(itemList.size() - 1);
    }

    @OnClick(R.id.consultation_details_bottom_action)
    public void onActionButtonClick() {

        new MaterialDialog.Builder(this)
                .title("Adjuntar archivo")
                .items(R.array.consultation_details_media)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {


                    }
                })
                .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consultation_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
