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

import com.example.myapplication.Database.TypeOfRevenue.TORevenueDtb;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.TypeOfRevenue;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TypeOfRevenueAdapter extends RecyclerView.Adapter<TypeOfRevenueAdapter.TypeOfRevenueViewHolder> {
    private List<TypeOfRevenue> mListTORevenue;
    private ClipData clipData;
    private Context context;
    private View v;

    @SuppressLint("NotifyDataSetChanged")
    public TypeOfRevenueAdapter(List<TypeOfRevenue> mListTORevenue) {
        this.mListTORevenue = mListTORevenue;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TypeOfRevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_of_revenue, parent, false);
        v = view;
        return new TypeOfRevenueViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TypeOfRevenueViewHolder holder, int position) {
        TypeOfRevenue typeOfRevenue = mListTORevenue.get(position);
        if (typeOfRevenue == null) {
            return;
        }
        holder.tv_nameTypeOfRevenue.setText("Tên loại thu: " + typeOfRevenue.getNameOfRevenue());
        holder.layout_type_of_revenue.setOnLongClickListener(view1 -> {
            final String mText = "ID: "+ typeOfRevenue.getIdTypeOfRevenue()
                    + " | Tên loại thu: " + typeOfRevenue.getNameOfRevenue();
            clipData = ClipData.newPlainText("text", mText);
            MainActivity._myClipboard.setPrimaryClip(clipData);
            Snackbar.make(v, R.string.copy_succeed, Snackbar.LENGTH_LONG).show();
            return true;
        });

        holder.layout_deleteTypeOfRevenue.setOnClickListener(view -> {
           dialogDeleteItem(typeOfRevenue);
        });
        holder.layout_type_of_revenue.setOnClickListener(view1 -> {
            context = view1.getContext();
            dialogUpdateRevenue(typeOfRevenue);
        });
    }

    private void dialogDeleteItem(TypeOfRevenue typeOfRevenue) {
        Dialog dialog = new Dialog(v.getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_logout);
        TextView tv_logout, tv_cancel, tv_titleLogout;
        tv_titleLogout = dialog.findViewById(R.id.tv_titleLogout);
        tv_logout = dialog.findViewById(R.id.tv_logout);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_logout.setText("Xóa");
        tv_titleLogout.setText("Bạn chắc chắn muộn xóa " + typeOfRevenue.getNameOfRevenue() + " ?");
        tv_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        tv_logout.setOnClickListener(view -> {
            TORevenueDtb.getInstance(context).typeOfRevenueDAO().deleteTypeOfRevenue(typeOfRevenue);
            loadData();
            dialog.dismiss();
        });
        dialog.show();
    }
    private void dialogUpdateRevenue(TypeOfRevenue typeOfRevenue) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_update_type_of_revenue);
        EditText edt_UnameTypeOfRevenue;
        TextView tv_idTORevenue, tvErrorUTORevenue;
        AppCompatButton btn_updateTORevenue, btn_cancel;

        edt_UnameTypeOfRevenue = dialog.findViewById(R.id.edt_UnameTypeOfRevenue);
        tv_idTORevenue = dialog.findViewById(R.id.tv_idTORevenue);
        tvErrorUTORevenue = dialog.findViewById(R.id.tvErrorUTORevenue);
        btn_updateTORevenue = dialog.findViewById(R.id.btn_updateTORevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);


//      fill Information
        tv_idTORevenue.setText("ID: " + typeOfRevenue.getIdTypeOfRevenue());
        edt_UnameTypeOfRevenue.setHint("Tên loại thu");
        edt_UnameTypeOfRevenue.setText(typeOfRevenue.getNameOfRevenue());


        btn_updateTORevenue.setOnClickListener(view -> {
            if (edt_UnameTypeOfRevenue.getText().toString().isEmpty()) {
                new Handler().postDelayed(() -> tvErrorUTORevenue.setText(R.string.empty), 3000);
            } else {
                typeOfRevenue.setNameOfRevenue(edt_UnameTypeOfRevenue.getText().toString());
                TORevenueDtb.getInstance(context).typeOfRevenueDAO().updateTypeOfRevenue(typeOfRevenue);
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
        if (mListTORevenue != null) {
            return mListTORevenue.size();
        }
        return 0;
    }

    private void loadData() {
        mListTORevenue.clear();
        mListTORevenue = TORevenueDtb.getInstance(context).typeOfRevenueDAO().getAllTypeOfRevenue();
        notifyDataSetChanged();
    }



    public static class TypeOfRevenueViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nameTypeOfRevenue;
         LinearLayout layout_type_of_revenue,layout_deleteTypeOfRevenue;

        public TypeOfRevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameTypeOfRevenue = itemView.findViewById(R.id.tv_nameTypeOfRevenue);
            layout_deleteTypeOfRevenue = itemView.findViewById(R.id.layout_deleteTypeOfRevenue);
            layout_type_of_revenue = itemView.findViewById(R.id.layout_type_of_revenue);

        }
    }
}
