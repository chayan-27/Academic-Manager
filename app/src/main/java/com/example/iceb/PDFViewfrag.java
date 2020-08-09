package com.example.iceb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PDFViewfrag extends Fragment {
    File file;

    @SuppressLint("ValidFragment")
    public PDFViewfrag(File file) {
        // Required empty public constructor
        this.file = file;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p_d_f_viewfrag, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PDFView pdfView = (PDFView) view.findViewById(R.id.pdfView);
        pdfView.fromFile(file).load();

        return view;
    }

}
