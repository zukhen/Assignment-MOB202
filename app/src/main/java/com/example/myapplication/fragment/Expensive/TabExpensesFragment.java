package com.example.myapplication.fragment.Expensive;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ExpensesAdapter;
import com.example.myapplication.Database.Expenses.ExpensesDtb;
import com.example.myapplication.Database.TypeOfExpenses.TOExpensesDtb;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Expenses;
import com.example.myapplication.model.TypeOfExpenses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabExpensesFragment extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_addExpenses;
    private RecyclerView rcv_listExpenses;
    private List<Expenses> mListExpenses;
    @SuppressLint("StaticFieldLeak")
    private static ExpensesAdapter expensesAdapter;

    public TabExpensesFragment() {
    }


    public static TabExpensesFragment newInstance() {
        return new TabExpensesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnClick();
        fillRecycleView();

    }

    private void registerForOnClick() {
        btn_addExpenses.setOnClickListener(this);
//        swipe to delete

//        border bottom item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_listExpenses.addItemDecoration(itemDecoration);

    }

    private void initUi(View view) {
        btn_addExpenses = view.findViewById(R.id.btn_addExpenses);
        rcv_listExpenses = view.findViewById(R.id.rcv_listExpenses);

    }

    private void dialogAddExpenses() {
        Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_add_expenses);
        EditText edt_nameOfExpenses, edt_dateOfExpenses, ed_moneyOfExpenses, ed_descriptionExpenses, edt_fullName;
        ImageView date_picker_actions;
        TextView tvErrorExpenses;
        Spinner spn_typeOfExpenses;
        AppCompatButton btn_addExpenses, btn_cancel;
        edt_nameOfExpenses = dialog.findViewById(R.id.edt_nameOfExpenses);
        edt_dateOfExpenses = dialog.findViewById(R.id.edt_dateOfExpenses);
        ed_moneyOfExpenses = dialog.findViewById(R.id.ed_moneyOfExpenses);
        edt_fullName = dialog.findViewById(R.id.edt_fullName);
        date_picker_actions = dialog.findViewById(R.id.date_picker_actions);
        spn_typeOfExpenses = dialog.findViewById(R.id.spn_typeOfExpenses);
        ed_descriptionExpenses = dialog.findViewById(R.id.ed_descriptionExpenses);
        btn_addExpenses = dialog.findViewById(R.id.btn_addExpenses);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        tvErrorExpenses = dialog.findViewById(R.id.tvErrorExpenses);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_spinner_expenses, R.id.txtKhoanChi, new ArrayList<>());
        arrayAdapter.addAll(TOExpensesDtb.getInstance(getContext()).toExpensesDAO().getAllNameTypeOfExpenses());
        spn_typeOfExpenses.setAdapter(arrayAdapter);


        date_picker_actions.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                calendar.set(i, i1, i2);
                edt_dateOfExpenses.setText(MainActivity.simpleDateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        final List<TypeOfExpenses> mListTypeOfExpenses = TOExpensesDtb.getInstance(getContext()).toExpensesDAO().getAllTOExpenses();


        btn_addExpenses.setOnClickListener(view -> {
            int index = spn_typeOfExpenses.getSelectedItemPosition();
            if (index == -1) {
                tvErrorExpenses.setText("Vui lòng thêm loại chi");
                new Handler().postDelayed(() -> tvErrorExpenses.setText(""), 3000);
            } else {
                if (edt_nameOfExpenses.getText().toString().isEmpty() || edt_dateOfExpenses.getText().toString().isEmpty() || ed_moneyOfExpenses.getText().toString().isEmpty() || ed_descriptionExpenses.getText().toString().isEmpty() || edt_fullName.getText().toString().isEmpty()) {
                    tvErrorExpenses.setText("Vui lòng không để trống");
                    new Handler().postDelayed(() -> tvErrorExpenses.setText(""), 3000);
                } else {
                    if (MainActivity.simpleDateFormat.toPattern().equals(edt_dateOfExpenses.getText().toString()))
                    {
                        tvErrorExpenses.setText("Vui lòng nhập đúng định dạng ngày dd-MM-yyyy");
                        new Handler().postDelayed(() -> tvErrorExpenses.setText(""), 3000);
                    }
                    ExpensesDtb.getInstance(getContext()).expensesDAO().insertExpenses(
                            new Expenses(edt_nameOfExpenses.getText().toString(),
                                    edt_dateOfExpenses.getText().toString(),
                                    Float.parseFloat(ed_moneyOfExpenses.getText().toString()),
                                    ed_descriptionExpenses.getText().toString(),
                                    edt_fullName.getText().toString(),
                                    mListTypeOfExpenses.get(index).getIdTypeOfExpenses()));
                    Toast.makeText(getContext(), "Nhập thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
            fillRecycleView();
        });
        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void fillRecycleView() {
        mListExpenses = ExpensesDtb.getInstance(getContext()).expensesDAO().getAllExpenses();
        expensesAdapter = new ExpensesAdapter(mListExpenses);
        rcv_listExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_listExpenses.setAdapter(expensesAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addExpenses:
                dialogAddExpenses();
                break;
        }
    }


}