package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.contactapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Phân biệt RecycleView and ListView
    /*
    *  ListView: View, Adapter, Array
    *  RecycleView: View, Adapter(Custom) => Custom Adapter, Array (Mỗi hàng trang trí 1 giao diện riêng gọi là row_item)
    * */

    //Chỉnh binding
    private ActivityMainBinding binding;
    private SearchView searchView;

    private ArrayList<Contact> contactList;
    private ContactsAdapter contactsAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDAO;
    private static final int EDITOR_ACTIVITY_REQUEST_CODE = 1;



    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup data binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // set up adapter
        contactList = new ArrayList<Contact>();
        contactsAdapter = new ContactsAdapter(this,contactList);
        binding.rvContacts.setAdapter(contactsAdapter);
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        //binding.rvContacts.addItemDecoration(new StickyRecyclerHeadersDecoration(contactsAdapter));
        // run at new thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//              load data to screen
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDAO = appDatabase.contactDao();

                List<Contact> list = contactDAO.getAll();
                //Collections.sort(list);
                for (Contact ct:
                        list) {
                    contactList.add(ct);
                    contactsAdapter.notifyDataSetChanged();
                }

            }
        });

        // Redirect to add screen
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);

                startActivity(intent);
            }
        });







    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITOR_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refresh RecyclerView
                //refreshRecyclerView();
            }
        }
    }

//    private void refreshRecyclerView() {
//        contactList = contactDao.getAll();
//        contactAdapter.setData(contactList);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menueditor, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("Tìm kiếm tại đây: ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //contactAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //contactAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}