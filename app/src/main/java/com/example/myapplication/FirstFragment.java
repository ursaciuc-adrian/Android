package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FirstFragment extends Fragment {
    private String search_text = "";

    private class OperatingSystem {
        public String name;
        public String description;

        public OperatingSystem(String name, String description) {
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("search", search_text);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            search_text = savedInstanceState.getString("search", "");
        }

        final ListView listview = view.findViewById(R.id.list_view);

        final ArrayList<OperatingSystem> values = new ArrayList<OperatingSystem>();
        values.add( new OperatingSystem("Windows", "Hello there from windows.") );
        values.add( new OperatingSystem("Ubuntu", "This is the description for ubuntu.") );
        values.add( new OperatingSystem("Android", "This application was done for android only.") );
        values.add( new OperatingSystem("iOS", "This is the description for iOS.") );
        values.add( new OperatingSystem("macOS", "This is the description for macOS.") );

        ArrayAdapter<String> adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, values.stream().filter(p -> p.name.toLowerCase().contains(search_text.toLowerCase())).collect(Collectors.toList()));
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                OperatingSystem selectedItem = (OperatingSystem) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString("info", selectedItem.description);

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });

        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Search");

                // Set up the input
                final EditText input = new EditText(view.getContext());
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        search_text = input.getText().toString();
                        ArrayAdapter<String> adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, values.stream().filter(p -> p.name.toLowerCase().contains(search_text.toLowerCase())).collect(Collectors.toList()));
                        listview.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
}
