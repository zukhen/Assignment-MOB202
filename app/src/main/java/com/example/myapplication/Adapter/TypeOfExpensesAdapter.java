package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.TypeOfExpenses.TOExpensesDtb;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.TypeOfExpenses;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TypeOfExpensesAdapter extends RecyclerView.Adapter<TypeOfExpensesAdapter.TypeOfExpensesViewHolder> {
    private List<TypeOfExpenses> mListTOExpenses;
    private ClipData clipData;
    private Context context;
    private View v;

    public TypeOfExpensesAdapter(List<TypeOfExpenses> mListTOExpenses) {
        this.mListTOExpenses = mListTOExpenses;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TypeOfExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_of_expenses, parent, false);
        v = view;
        return new TypeOfExpensesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TypeOfExpensesViewHolder holder, int position) {
        TypeOfExpenses typeOfExpenses = mListTOExpenses.get(position);
        if (typeOfExpenses == null) {
            return;
        }
        holder.tv_nameTypeOfExpenses.setText("Tên loại chi: " + typeOfExpenses.getNameOfExpenses());
        holder.layout_type_of_expenses.setOnLongClickListener(view1 -> {
            final String mText = "ID: " + typeOfExpenses.getIdTypeOfExpenses()
                    + " | Tên loại chi: " + typeOfExpenses.getNameOfExpenses();
            clipData = ClipData.newPlainText("text", mText);
            MainActivity._myClipboard.setPrimaryClip(clipData);
            Snackbar.make(v, R.string.copy_succeed, Snackbar.LENGTH_LONG).show();
            return true;
        });
        holder.layout_deleteTypeOfExpenses.setOnClickListener(view -> {
            dialogDeleteItem(typeOfExpenses);
        });
        holder.layout_type_of_expenses.setOnClickListener(view1 -> {
            context = view1.getContext();
            dialogUpdateTypeOfExpenses(typeOfExpenses);

        });
    }

    private void dialogDeleteItem(TypeOfExpenses typeOfExpenses) {
        Dialog dialog = new Dialog(v.getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_logout);
        TextView tv_logout, tv_cancel, tv_titleLogout;
        tv_titleLogout = dialog.findViewById(R.id.tv_titleLogout);
        tv_logout = dialog.findViewById(R.id.tv_logout);
        tv_logout.setText("Xóa");
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_titleLogout.setText("Bạn chắc chắn muộn xóa " + typeOfExpenses.getNameOfExpenses() + " ?");
        tv_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        tv_logout.setOnClickListener(view -> {
            TOExpensesDtb.getInstance(context).toExpensesDAO().deleteTOExpenses(typeOfExpenses);
            loadData();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void dialogUpdateTypeOfExpenses(TypeOfExpenses typeOfExpenses) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_update_type_of_revenue);
        EditText edt_UnameTypeOfRevenue;
        TextView tv_idTORevenue, tv_title, tvErrorUTORevenue;
        AppCompatButton btn_updateTORevenue, btn_cancel;
        edt_UnameTypeOfRevenue = dialog.findViewById(R.id.edt_UnameTypeOfRevenue);
        tv_idTORevenue = dialog.findViewById(R.id.tv_idTORevenue);
        tvErrorUTORevenue = dialog.findViewById(R.id.tvErrorUTORevenue);
        btn_updateTORevenue = dialog.findViewById(R.id.btn_updateTORevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        tv_title = dialog.findViewById(R.id.tv_title);


//      fill Information
        edt_UnameTypeOfRevenue.setHint("Tên loại chi");
        tv_title.setText(R.string.update_type_of_expenses);
        tv_idTORevenue.setText("ID: " + typeOfExpenses.getIdTypeOfExpenses());
        edt_UnameTypeOfRevenue.setText(typeOfExpenses.getNameOfExpenses());


        btn_updateTORevenue.setOnClickListener(view -> {
            if (edt_UnameTypeOfRevenue.getText().toString().isEmpty()) {
                tvErrorUTORevenue.setText(R.string.empty);
                new Handler().postDelayed(() -> tvErrorUTORevenue.setText(""), 3000);
            } else {
                typeOfExpenses.setNameOfExpenses(edt_UnameTypeOfRevenue.getText().toString());
                TOExpensesDtb.getInstance(context).toExpensesDAO().updateTOExpenses(typeOfExpenses);
                Toast.makeText(context, R.string.update_succeed, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
        if (mListTOExpenses != null) {
            return mListTOExpenses.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        mListTOExpenses.clear();
        mListTOExpenses = TOExpensesDtb.getInstance(context).toExpensesDAO().getAllTOExpenses();
        notifyDataSetChanged();
    }


    public static class TypeOfExpensesViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nameTypeOfExpenses;
        LinearLayout layout_type_of_expenses, layout_deleteTypeOfExpenses;

        public TypeOfExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameTypeOfExpenses = itemView.findViewById(R.id.tv_nameTypeOfExpenses);
            layout_type_of_expenses = itemView.findViewById(R.id.layout_type_of_expenses);
            layout_deleteTypeOfExpenses = itemView.findViewById(R.id.layout_deleteTypeOfExpenses);
        }
    }
}
