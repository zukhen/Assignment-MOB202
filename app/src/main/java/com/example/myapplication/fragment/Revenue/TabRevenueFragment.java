package com.example.myapplication.fragment.Revenue;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.TypeOfRevenue.TORevenueDtb;
import com.example.myapplication.Adapter.RevenueAdapter;
import com.example.myapplication.Database.Revenue.RevenueDtb;
import com.example.myapplication.R;
import com.example.myapplication.model.Revenue;
import com.example.myapplication.model.TypeOfRevenue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabRevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabRevenueFragment extends Fragment implements View.OnClickListener{
    private AppCompatButton btn_addRevenue;
    private RecyclerView rcv_listRevenue;
    private List<Revenue> mListRevenue;
    private TabRevenueFragment context;
    private static RevenueAdapter revenueAdapter;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public TabRevenueFragment() {
    }


    public static TabRevenueFragment newInstance() {
        return new TabRevenueFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_revenue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnClick();
        fillRecycleView();

    }

    private void registerForOnClick() {
        context = this;
        btn_addRevenue.setOnClickListener(this);

//        border bottom item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_listRevenue.addItemDecoration(itemDecoration);

    }

    private void initUi(View view) {
        btn_addRevenue = view.findViewById(R.id.btn_addRevenue);
        rcv_listRevenue = view.findViewById(R.id.rcv_listRevenue);

    }

    private void dialogAddRevenue() {
        Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_add_revenue);
        EditText edt_nameOfRevenue, edt_dateOfRevenue, ed_moneyOfRevenue, ed_descriptionRevenue, edt_fullName;
        ImageView date_picker_actions;
        TextView tvErrorRevenue;
        Spinner spn_typeOfRevenue;
        AppCompatButton btn_addRevenue, btn_cancel;
        edt_nameOfRevenue = dialog.findViewById(R.id.edt_nameOfRevenue);
        edt_dateOfRevenue = dialog.findViewById(R.id.edt_dateOfRevenue);
        ed_descriptionRevenue = dialog.findViewById(R.id.ed_descriptionRevenue);
        edt_fullName = dialog.findViewById(R.id.edt_fullName);
        date_picker_actions = dialog.findViewById(R.id.date_picker_actions);
        spn_typeOfRevenue = dialog.findViewById(R.id.spn_typeOfRevenue);
        ed_moneyOfRevenue = dialog.findViewById(R.id.ed_moneyOfRevenue);
        btn_addRevenue = dialog.findViewById(R.id.btn_addRevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        tvErrorRevenue = dialog.findViewById(R.id.tvErrorRevenue);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_spinner_revenue, R.id.txtKhoanThu, new ArrayList<>());
        arrayAdapter.addAll(TORevenueDtb.getInstance(getContext()).typeOfRevenueDAO().getAllNameTypeOfRevenue());
        spn_typeOfRevenue.setAdapter(arrayAdapter);


        date_picker_actions.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                calendar.set(i, i1, i2);
                edt_dateOfRevenue.setText(simpleDateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        final List<TypeOfRevenue> mListTypeOfRevenue = TORevenueDtb.getInstance(getContext()).typeOfRevenueDAO().getAllTypeOfRevenue();

        btn_addRevenue.setOnClickListener(view -> {
            int index = spn_typeOfRevenue.getSelectedItemPosition();
            if (edt_nameOfRevenue.getText().toString().isEmpty() || edt_dateOfRevenue.getText().toString().isEmpty() || ed_moneyOfRevenue.getText().toString().isEmpty() || ed_descriptionRevenue.getText().toString().isEmpty() || edt_fullName.getText().toString().isEmpty()) {
                tvErrorRevenue.setText("Vui lòng không để trống");
                new Handler().postDelayed(() -> tvErrorRevenue.setText(""), 3000);
            } else {
                if (index == -1) {
                    tvErrorRevenue.setText("Vui lòng thêm loại thu trước");
                } else {
                    RevenueDtb.getInstance(getContext()).revenueDAO().insertRevenue(new Revenue(edt_nameOfRevenue.getText().toString(), edt_dateOfRevenue.getText().toString(), Float.parseFloat(ed_moneyOfRevenue.getText().toString()), ed_descriptionRevenue.getText().toString(), edt_fullName.getText().toString(), mListTypeOfRevenue.get(index).getIdTypeOfRevenue()));
                    Toast.makeText(getContext(), "Nhập thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
            fillRecycleView();
        });
        btn_cancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private void fillRecycleView() {
        mListRevenue = RevenueDtb.getInstance(getContext()).revenueDAO().getAllRevenue();
        revenueAdapter = new RevenueAdapter(mListRevenue);
        rcv_listRevenue.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_listRevenue.setAdapter(revenueAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addRevenue:
                dialogAddRevenue();
                break;
        }
    }




}