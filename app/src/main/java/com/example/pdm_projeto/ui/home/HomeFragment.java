package com.example.pdm_projeto.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdm_projeto.R;
import com.example.pdm_projeto.Utils.Constant;
import com.example.pdm_projeto.Utils.DataSource;
import com.example.pdm_projeto.adapter.TimelineAdapter;
import com.example.pdm_projeto.model.TimelineItem;

import java.sql.Time;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView timeLineRv;
    private TimelineAdapter timelineAdapter;
    private List<TimelineItem> mdata;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        timeLineRv = root.findViewById(R.id.timeline_rv);
        timeLineRv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mdata = DataSource.getTimeLineData(root.getContext());
        timelineAdapter = new TimelineAdapter(root.getContext(),mdata);
        timeLineRv.setAdapter(timelineAdapter);
        return root;
    }




}
