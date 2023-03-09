package com.example.myapplication.fragment.Expensive;

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

import com.example.myapplication.Adapter.TypeOfExpensesAdapter;
import com.example.myapplication.Database.TypeOfExpenses.TOExpensesDtb;
import com.example.myapplication.R;
import com.example.myapplication.model.TypeOfExpenses;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabTypeOfExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabTypeOfExpensesFragment extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_addTypeOfExpenses;
    private RecyclerView rcv_listTypeOfExpenses;
    private List<TypeOfExpenses> mListTOExpenses;
    private TypeOfExpensesAdapter typeOfExpensesAdapter;
    public TabTypeOfExpensesFragment() {
        // Required empty public constructor
    }


    public static TabTypeOfExpensesFragment newInstance() {

        return new TabTypeOfExpensesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_type_of_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
        fillRecycleView();
    }

    private void fillRecycleView() {
        mListTOExpenses = TOExpensesDtb.getInstance(getContext()).toExpensesDAO().getAllTOExpenses();
        typeOfExpensesAdapter = new TypeOfExpensesAdapter(mListTOExpenses);
        rcv_listTypeOfExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_listTypeOfExpenses.setAdapter(typeOfExpensesAdapter);
    }

    private void registerForOnclick() {
        btn_addTypeOfExpenses.setOnClickListener(this);
        //        border bottom item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_listTypeOfExpenses.addItemDecoration(itemDecoration);
    }

    private void initUi(View view) {
        btn_addTypeOfExpenses = view.findViewById(R.id.btn_addTypeOfExpenses);
        rcv_listTypeOfExpenses = view.findViewById(R.id.rcv_listTypeOfExpenses);

    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.btn_addTypeOfExpenses:
            dialogAddTOExpenses();
            break;
    }
    }

    private void dialogAddTOExpenses() {
        Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_add_type_of_revenue);
        EditText edt_nameTORevenue = dialog.findViewById(R.id.edt_nameTORevenue);
        TextView tvErrorTORevenue = dialog.findViewById(R.id.tvErrorTORevenue);
        TextView titleExpenses = dialog.findViewById(R.id.titleExpenses);
        AppCompatButton btn_addTORevenue, btn_cancel;
        btn_addTORevenue = dialog.findViewById(R.id.btn_addTORevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

//        fill text
        titleExpenses.setText("Thêm loại chi");
        edt_nameTORevenue.setHint("Tên loại chi");
        btn_addTORevenue.setOnClickListener(view -> {
            if (edt_nameTORevenue.getText().toString().isEmpty()) {
                tvErrorTORevenue.setText("Vui lòng không để trống");
                new Handler().postDelayed(() -> tvErrorTORevenue.setText(""), 3000);
            } else {
                TOExpensesDtb.getInstance(getContext()).toExpensesDAO().insertTOExpenses(new TypeOfExpenses(edt_nameTORevenue.getText().toString()));
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


}