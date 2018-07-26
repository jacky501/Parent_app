package org.atctech.parent.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.adapter.TeacherAdapter;
import org.atctech.parent.model.TeacherDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFragment extends Fragment implements SearchView.OnQueryTextListener{

    ApiRequest service;
    RecyclerView recyclerView;
    List<TeacherDetails> teacherDetails;
    private SearchView searchView;
    TeacherAdapter adapter;

    public TeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Teachers");

        recyclerView = view.findViewById(R.id.teacherList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        Call<List<TeacherDetails>> listCall = service.getAllTeacher();

        listCall.enqueue(new Callback<List<TeacherDetails>>() {
            @Override
            public void onResponse(Call<List<TeacherDetails>> call, Response<List<TeacherDetails>> response) {
                if (response.isSuccessful())
                {
                    teacherDetails = response.body();
                    if (teacherDetails!=null) {
                        adapter = new TeacherAdapter(getContext(), teacherDetails);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),2));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }else {

                }
            }

            @Override
            public void onFailure(Call<List<TeacherDetails>> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        final MenuItem item = menu.findItem(R.id.ic_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setFilter(teacherDetails);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<TeacherDetails> filteredModelList = filter(teacherDetails, newText);
        adapter.setFilter(filteredModelList);

        return true;
    }

    private List<TeacherDetails> filter(List<TeacherDetails> models, String query) {
        query = query.toLowerCase();
        final List<TeacherDetails> filteredModelList = new ArrayList<>();
        for (TeacherDetails model : models) {
            final String text = model.getFname().toLowerCase()+" "+model.getLname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
