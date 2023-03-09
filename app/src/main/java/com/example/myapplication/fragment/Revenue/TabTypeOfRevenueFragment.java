package com.example.myapplication.fragment.Revenue;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.TypeOfRevenue.TORevenueDtb;
import com.example.myapplication.Adapter.TypeOfRevenueAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.TypeOfRevenue;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabTypeOfRevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabTypeOfRevenueFragment extends Fragment implements View.OnClickListener{
    private AppCompatButton btn_addTypeOfRevenue;
    private RecyclerView rcv_listTypeOfRevenue;
    private List<TypeOfRevenue> mListTORevenue;
    private  TypeOfRevenueAdapter typeOfRevenueAdapter;

    public TabTypeOfRevenueFragment() {
        // Required empty public constructor
    }


    public static TabTypeOfRevenueFragment newInstance() {
        return new TabTypeOfRevenueFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_of_revenue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
        fillRecycleView();
    }

    private void registerForOnclick() {
        TabTypeOfRevenueFragment context = this;
        btn_addTypeOfRevenue.setOnClickListener(this);

//        border bottom item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_listTypeOfRevenue.addItemDecoration(itemDecoration);
    }

    private void initUi(View view) {
        btn_addTypeOfRevenue = view.findViewById(R.id.btn_addTypeOfRevenue);
        rcv_listTypeOfRevenue = view.findViewById(R.id.rcv_listTypeOfRevenue);
    }

    private void dialogAddTORevenue() {
        Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_add_type_of_revenue);
        EditText edt_nameTORevenue = dialog.findViewById(R.id.edt_nameTORevenue);
        TextView tvErrorTORevenue = dialog.findViewById(R.id.tvErrorTORevenue);
        AppCompatButton btn_addTORevenue, btn_cancel;
        btn_addTORevenue = dialog.findViewById(R.id.btn_addTORevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_addTORevenue.setOnClickListener(view -> {
            if (edt_nameTORevenue.getText().toString().isEmpty()) {
                tvErrorTORevenue.setText("Vui lòng không để trống");
                new Handler().postDelayed(() -> tvErrorTORevenue.setText(""), 3000);
            } else {
                TORevenueDtb.getInstance(getContext()).typeOfRevenueDAO().insertTypeOfRevenue(new TypeOfRevenue(edt_nameTORevenue.getText().toString()));
                Toast.makeText(getContext(), "Nhập thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            fillRecycleView();
        });
        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addTypeOfRevenue:
                dialogAddTORevenue();
                break;
        }

    }


    private void fillRecycleView() {
        mListTORevenue = TORevenueDtb.getInstance(getContext()).typeOfRevenueDAO().getAllTypeOfRevenue();
        typeOfRevenueAdapter = new TypeOfRevenueAdapter(mListTORevenue);
        rcv_listTypeOfRevenue.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_listTypeOfRevenue.setAdapter(typeOfRevenueAdapter);
    }


}