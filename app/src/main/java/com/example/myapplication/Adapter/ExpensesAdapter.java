package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Expenses.ExpensesDtb;
import com.example.myapplication.Database.TypeOfExpenses.TOExpensesDtb;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Expenses;
import com.example.myapplication.model.TypeOfExpenses;
import com.google.android.material.snackbar.Snackbar;
import com.ntduc.swipereveallayout.SwipeRevealLayout;
import com.ntduc.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder> {
    private List<Expenses> mListExpenses;
    private ClipData clipData;
    @SuppressLint("SimpleDateFormat")

    private Context context;
    private View v;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    @SuppressLint("NotifyDataSetChanged")
    public ExpensesAdapter(List<Expenses> mListExpenses) {
        this.mListExpenses = mListExpenses;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses, parent, false);
       v = view;
        return new ExpensesViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {
        Expenses expenses = mListExpenses.get(position);

        if (expenses == null) {
            return;
        }

        viewBinderHelper.bind(holder.layout_Expenses,String.valueOf(expenses.getIdExpenses()));
        holder.tv_nameOfExpenses.setText("Tên khoản chi: "+ expenses.getNameOfExpenses());
        holder.tv_dateExpenses.setText("Ngày chi: " + expenses.getDateExpenses());
        holder.tv_moneyOfExpenses.setText("Số tiền: "+ expenses.getMoneyOfExpenses()+ " vnd");
        holder.layout_foreground.setOnLongClickListener(view1 -> {
            final String mText = "ID: "+ expenses.getIdExpenses()
                    +" | Tên khoản chi" + expenses.getNameOfExpenses()
                    +" | Ngày chi: "+ expenses.getDateExpenses()
                    +" | Số tiền: "+ expenses.getMoneyOfExpenses()
                    +" | Ngwoif chi: " + expenses.getFullName();
            clipData = ClipData.newPlainText("text", mText);
            MainActivity._myClipboard.setPrimaryClip(clipData);
            Snackbar.make(v, R.string.copy_succeed,Snackbar.LENGTH_SHORT).show();
            return true;
        });
        holder.layout_DeleteExpenses.setOnClickListener(view -> {
            dialogDeleteItem(expenses);

        });
        holder.layout_updateExpenses.setOnClickListener(view -> {
            context = view.getContext();
            dialogUpdateExpenses(expenses);
        });
        holder.layout_foreground.setOnClickListener(view1 -> {
            context = view1.getContext();
            dialogInformationExpenses(expenses);
        });
    }

    private void dialogDeleteItem(Expenses expenses) {
        Dialog dialog = new Dialog(v.getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_logout);
        TextView tv_logout, tv_cancel,tv_titleLogout;
        tv_titleLogout= dialog.findViewById(R.id.tv_titleLogout);
        tv_logout = dialog.findViewById(R.id.tv_logout);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_logout.setText("Xóa");
        tv_titleLogout.setText("Bạn chắc chắn muộn xóa "+expenses.getNameOfExpenses()+" ?");
        tv_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        tv_logout.setOnClickListener(view -> {
            ExpensesDtb.getInstance(context).expensesDAO().deleteExpenses(expenses);
            loadData();
            dialog.dismiss();
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void dialogInformationExpenses(Expenses expenses) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_information_revenue);
        ImageView img_avatar;
        TextView tv_nameOfRevenue, tv_idRevenue, tv_dateRevenue, tv_fullName, tv_descriptionRevenue;
        AppCompatButton btn_UpdateRevenue, btn_cancel;

        tv_nameOfRevenue = dialog.findViewById(R.id.tv_nameOfRevenue);
        tv_idRevenue = dialog.findViewById(R.id.tv_idRevenue);
        img_avatar = dialog.findViewById(R.id.img_avatar);
        tv_dateRevenue = dialog.findViewById(R.id.tv_dateRevenue);
        tv_fullName = dialog.findViewById(R.id.tv_fullName);
        tv_descriptionRevenue = dialog.findViewById(R.id.tv_descriptionRevenue);
        btn_UpdateRevenue = dialog.findViewById(R.id.btn_UpdateRevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        tv_nameOfRevenue.setText(expenses.getNameOfExpenses());
        tv_idRevenue.setText("ID: " + expenses.getIdExpenses());
        tv_dateRevenue.setText("Ngày chi: "+ expenses.getDateExpenses());
        tv_fullName.setText("Người chi: "+ expenses.getFullName() +" - Số tiền"+ expenses.getMoneyOfExpenses());
        tv_descriptionRevenue.setText("Mô tả: "+ expenses.getDescriptionExpenses());
        img_avatar.setBackgroundResource(R.drawable.avata_khoanchi);
        btn_UpdateRevenue.setOnClickListener(view -> {
            dialogUpdateExpenses(expenses);
            dialog.dismiss();
        });
        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });


        dialog.show();
    }

    private void dialogUpdateExpenses(Expenses expenses) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_update_expenses);
        EditText ed_UnameOfExpenses, ed_UdateOfExpenses, ed_UmoneyOfExpenses, ed_UdescriptionExpenses, edt_UfullName;
        ImageView date_picker_actions;
        TextView tv_idExpenses, tvErrorUExpenses;
        Spinner spn_typeOfExpenses;
        AppCompatButton btn_updateExpenses, btn_cancel;
        tv_idExpenses = dialog.findViewById(R.id.tv_idExpenses);
        ed_UnameOfExpenses = dialog.findViewById(R.id.ed_UnameOfExpenses);
        ed_UdateOfExpenses = dialog.findViewById(R.id.ed_UdateOfExpenses);
        ed_UmoneyOfExpenses = dialog.findViewById(R.id.ed_UmoneyOfExpenses);
        ed_UdescriptionExpenses = dialog.findViewById(R.id.ed_UdescriptionExpenses);
        edt_UfullName = dialog.findViewById(R.id.edt_UfullName);
        date_picker_actions = dialog.findViewById(R.id.date_picker_actions);
        spn_typeOfExpenses = dialog.findViewById(R.id.spn_typeOfExpenses);
        tvErrorUExpenses = dialog.findViewById(R.id.tvErrorUExpenses);
        btn_updateExpenses = dialog.findViewById(R.id.btn_updateExpenses);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);


//      fill Information
        tv_idExpenses.setText("ID: "+ expenses.getIdExpenses());
        ed_UnameOfExpenses.setText(expenses.getNameOfExpenses());
        ed_UmoneyOfExpenses.setText(String.valueOf(expenses.getMoneyOfExpenses()));
        ed_UdescriptionExpenses.setText(expenses.getDescriptionExpenses());
        edt_UfullName.setText(expenses.getFullName());
        ed_UdateOfExpenses.setText(expenses.getDateExpenses());

//        spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.item_spinner_expenses, R.id.txtKhoanChi, new ArrayList<>());
            arrayAdapter.addAll(TOExpensesDtb.getInstance(context).toExpensesDAO().getAllNameTypeOfExpenses());
        spn_typeOfExpenses.setAdapter(arrayAdapter);

        final List<TypeOfExpenses> mListTypeOfExpenses = TOExpensesDtb.getInstance(context).toExpensesDAO().getAllTOExpenses();
        int index = -1;
        for (int i = 0; i < mListTypeOfExpenses.size(); i++) {
            if (mListTypeOfExpenses.get(i).getIdTypeOfExpenses() == expenses.getIdTypeOfExpenses()) {
                index = i;
            }
        }
        spn_typeOfExpenses.setSelection(index);

//        date_picker_actions.setFocusable(false);
        date_picker_actions.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, i, i1, i2) -> {
                calendar.set(i, i1, i2);
                ed_UdateOfExpenses.setText(MainActivity.simpleDateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });


        btn_updateExpenses.setOnClickListener(view -> {
            if (ed_UnameOfExpenses.getText().toString().isEmpty()
                    || ed_UdateOfExpenses.getText().toString().isEmpty()
                    || ed_UmoneyOfExpenses.getText().toString().isEmpty()
                    || edt_UfullName.getText().toString().isEmpty()
                    || ed_UdescriptionExpenses.getText().toString().isEmpty()) {
                tvErrorUExpenses.setText(R.string.empty);
                new Handler().postDelayed(() -> tvErrorUExpenses.setText(""), 3000);
            } else {
                final int finalIndex = spn_typeOfExpenses.getSelectedItemPosition();
                if (finalIndex == -1) {
                    tvErrorUExpenses.setText(R.string.empty);
                    new Handler().postDelayed(() -> tvErrorUExpenses.setText(""), 3000);
                } else {
                    expenses.setIdExpenses(expenses.getIdExpenses());
                    expenses.setDescriptionExpenses(ed_UdescriptionExpenses.getText().toString());
                    expenses.setNameOfExpenses(ed_UnameOfExpenses.getText().toString());
                    expenses.setMoneyOfExpenses(Float.parseFloat(ed_UmoneyOfExpenses.getText().toString()));
                    expenses.setDateExpenses(ed_UdateOfExpenses.getText().toString());
                    expenses.setFullName(edt_UfullName.getText().toString());
                    expenses.setIdTypeOfExpenses(mListTypeOfExpenses.get(finalIndex).getIdTypeOfExpenses());
                    ExpensesDtb.getInstance(context).expensesDAO().updateExpenses(expenses);
                    Toast.makeText(context, R.string.update_succeed, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
            loadData();
        });

        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }


    @Override
    public int getItemCount() {
        if (mListExpenses != null) {
            return mListExpenses.size();
        }
        return 0;
    }

    private void loadData() {
        mListExpenses.clear();
        mListExpenses = ExpensesDtb.getInstance(context).expensesDAO().getAllExpenses();
        notifyDataSetChanged();
    }



    public class ExpensesViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nameOfExpenses, tv_dateExpenses, tv_moneyOfExpenses;
        public LinearLayout layout_foreground, layout_updateExpenses,layout_DeleteExpenses;
        public SwipeRevealLayout layout_Expenses;

        public ExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameOfExpenses = itemView.findViewById(R.id.tv_nameOfExpenses);
            tv_dateExpenses = itemView.findViewById(R.id.tv_dateExpenses);
            tv_moneyOfExpenses = itemView.findViewById(R.id.tv_moneyOfExpenses);
            layout_Expenses = itemView.findViewById(R.id.layout_Expenses);
            layout_updateExpenses = itemView.findViewById(R.id.layout_updateExpenses);
            layout_DeleteExpenses = itemView.findViewById(R.id.layout_DeleteExpenses);
            layout_foreground = itemView.findViewById(R.id.layout_type_of_expenses);

        }
    }
}
