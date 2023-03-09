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

import com.example.myapplication.Database.Revenue.RevenueDtb;
import com.example.myapplication.Database.TypeOfRevenue.TORevenueDtb;
import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Revenue;
//import com.example.khanhvvph28077.DAO.KhoanthuDAO;
import com.example.myapplication.R;
import com.example.myapplication.model.TypeOfRevenue;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {
    private List<Revenue> mListRevenue;
    private ClipData clipData;

    private Context context;
    private View v;
    public RevenueAdapter(List<Revenue> mListRevenue) {
        this.mListRevenue = mListRevenue;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revenue, parent, false);
        v = view;
        return new RevenueViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        Revenue revenue = mListRevenue.get(position);
        if (revenue == null) {
            return;
        }

        holder.tv_nameOfRevenue.setText("Tên khoản thu: " + revenue.getNameOfRevenue());
        holder.tv_dateRevenue.setText("Ngày thu: "+ revenue.getDateRevenue());
        holder.tv_moneyOfRevenue.setText("Số tiền: "+ revenue.getMoneyOfRevenue() + " vnd");
        holder.layout_type_of_revenue.setOnLongClickListener(view1 -> {
            final String mText = "ID: "+ revenue.getIdRevenue()
                    +" | Tên khoản thu: "+ revenue.getNameOfRevenue()
                    +" | Ngày thu: "+ revenue.getDateRevenue()
                    +" | Số tiền: "+ revenue.getMoneyOfRevenue()
                    +" | Người chi: " + revenue.getFullName();
                clipData = ClipData.newPlainText("text", mText);
                MainActivity._myClipboard.setPrimaryClip(clipData);
                Snackbar.make(v, R.string.copy_succeed,
                        Snackbar.LENGTH_SHORT).show();
            return true;
        });
        holder.layout_deleteRevenue.setOnClickListener(view -> {
            dialogDeleteItem(revenue);
        });

        holder.layout_updateRevenue.setOnClickListener(view -> {
            context = view.getContext();
            dialogUpdateRevenue(revenue);
        });

        holder.layout_type_of_revenue.setOnClickListener(view1 -> {
            context = view1.getContext();
            dialogInformationRevenue(revenue);
        });
    }
    private void dialogDeleteItem(Revenue revenue) {
        Dialog dialog = new Dialog(v.getContext(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_logout);
        TextView tv_logout, tv_cancel,tv_titleLogout;
        tv_titleLogout= dialog.findViewById(R.id.tv_titleLogout);
        tv_logout = dialog.findViewById(R.id.tv_logout);
        tv_logout.setText("Xóa");
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_titleLogout.setText("Bạn chắc chắn muộn xóa "+revenue.getNameOfRevenue()+" ?");
        tv_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        tv_logout.setOnClickListener(view -> {
            RevenueDtb.getInstance(context).revenueDAO().deleteRevenue(revenue);
            loadData();
            dialog.dismiss();
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void dialogInformationRevenue(Revenue revenue) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_information_revenue);
        TextView tv_nameOfRevenue, tv_idRevenue, tv_dateRevenue, tv_fullName, tv_descriptionRevenue;
        AppCompatButton btn_UpdateRevenue, btn_cancel;
        ImageView img_avatar = dialog.findViewById(R.id.img_avatar);
        tv_nameOfRevenue = dialog.findViewById(R.id.tv_nameOfRevenue);
        tv_idRevenue = dialog.findViewById(R.id.tv_idRevenue);
        tv_dateRevenue = dialog.findViewById(R.id.tv_dateRevenue);
        tv_fullName = dialog.findViewById(R.id.tv_fullName);
        tv_descriptionRevenue = dialog.findViewById(R.id.tv_descriptionRevenue);
        btn_UpdateRevenue = dialog.findViewById(R.id.btn_UpdateRevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        img_avatar.setBackgroundResource(R.drawable.avatar_khoanthu);
        tv_nameOfRevenue.setText(revenue.getNameOfRevenue());
        tv_idRevenue.setText("ID: "+ revenue.getIdRevenue());
        tv_dateRevenue.setText("Ngày thu: " + revenue.getDateRevenue());
        tv_fullName.setText("Người chi: " + revenue.getFullName() + " - Số tiền: " + revenue.getMoneyOfRevenue());
        tv_descriptionRevenue.setText("Mô tả: "+ revenue.getDescriptionRevenue());


        btn_UpdateRevenue.setOnClickListener(view -> {
            dialogUpdateRevenue(revenue);
            dialog.dismiss();
        });
        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });


        dialog.show();
    }

    private void dialogUpdateRevenue(Revenue revenue) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_update_revenue);
        EditText edt_UnameOfRevenue, edt_UdateOfRevenue, ed_UmoneyOfRevenue, ed_UdescriptionRevenue, edt_UfullName;
        ImageView date_picker_actions;
        TextView tv_idRevenue, tvErrorURevenue;
        Spinner spn_typeOfRevenue;
        AppCompatButton btn_updateRevenue, btn_cancel;
        tv_idRevenue = dialog.findViewById(R.id.tv_idRevenue);
        edt_UnameOfRevenue = dialog.findViewById(R.id.edt_UnameOfRevenue);
        edt_UdateOfRevenue = dialog.findViewById(R.id.edt_UdateOfRevenue);
        ed_UmoneyOfRevenue = dialog.findViewById(R.id.ed_UmoneyOfRevenue);
        ed_UdescriptionRevenue = dialog.findViewById(R.id.ed_UdescriptionRevenue);
        edt_UfullName = dialog.findViewById(R.id.edt_UfullName);
        date_picker_actions = dialog.findViewById(R.id.date_picker_actions);
        spn_typeOfRevenue = dialog.findViewById(R.id.spn_typeOfRevenue);
        tvErrorURevenue = dialog.findViewById(R.id.tvErrorURevenue);
        btn_updateRevenue = dialog.findViewById(R.id.btn_updateRevenue);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);


//      fill Information
        tv_idRevenue.setText("ID: "+ revenue.getIdRevenue());
        edt_UnameOfRevenue.setText(revenue.getNameOfRevenue());
        ed_UmoneyOfRevenue.setText(String.valueOf(revenue.getMoneyOfRevenue()));
        ed_UdescriptionRevenue.setText(revenue.getDescriptionRevenue());
        edt_UfullName.setText(revenue.getFullName());
        edt_UdateOfRevenue.setText(revenue.getDateRevenue());

//        spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.item_spinner_revenue, R.id.txtKhoanThu, new ArrayList<>());
        arrayAdapter.addAll(TORevenueDtb.getInstance(context).typeOfRevenueDAO().getAllNameTypeOfRevenue());
        spn_typeOfRevenue.setAdapter(arrayAdapter);

        final List<TypeOfRevenue> mListTypeOfRevenue= TORevenueDtb.getInstance(context).typeOfRevenueDAO().getAllTypeOfRevenue();
        int index = -1;
        for (int i = 0; i < mListTypeOfRevenue.size(); i++) {
            if (mListTypeOfRevenue.get(i).getIdTypeOfRevenue()==revenue.getIdTypeOfRevenue()) {
                index=i;
            }
        }
        spn_typeOfRevenue.setSelection(index);

//        date_picker_actions.setFocusable(false);
        date_picker_actions.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, i, i1, i2) -> {
                calendar.set(i, i1, i2);
                edt_UdateOfRevenue.setText(MainActivity.simpleDateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });



        btn_updateRevenue.setOnClickListener(view -> {
            if (edt_UnameOfRevenue.getText().toString().isEmpty()
                    || edt_UdateOfRevenue.getText().toString().isEmpty()
                    || ed_UmoneyOfRevenue.getText().toString().isEmpty()
                    || edt_UfullName.getText().toString().isEmpty()
                    || ed_UdescriptionRevenue.getText().toString().isEmpty()) {
                tvErrorURevenue.setText(R.string.empty);
                new Handler().postDelayed(() -> tvErrorURevenue.setText(""), 3000);
            } else {
                 final int finalIndex = spn_typeOfRevenue.getSelectedItemPosition();
               if (finalIndex==-1)
               {
                   tvErrorURevenue.setText(R.string.empty);
                   new Handler().postDelayed(() -> tvErrorURevenue.setText(""), 3000);

               }
               else{
                   revenue.setIdRevenue(revenue.getIdRevenue());
                   revenue.setDescriptionRevenue(ed_UdescriptionRevenue.getText().toString());
                   revenue.setNameOfRevenue(edt_UnameOfRevenue.getText().toString());
                   revenue.setMoneyOfRevenue(Float.parseFloat(ed_UmoneyOfRevenue.getText().toString()));
                   revenue.setDateRevenue(edt_UdateOfRevenue.getText().toString());
                   revenue.setFullName(edt_UfullName.getText().toString());
                   revenue.setIdTypeOfRevenue(mListTypeOfRevenue.get(finalIndex).getIdTypeOfRevenue());
                   RevenueDtb.getInstance(context).revenueDAO().updateRevenue(revenue);
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
        if (mListRevenue != null) {
            return mListRevenue.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        mListRevenue.clear();
        mListRevenue = RevenueDtb.getInstance(context).revenueDAO().getAllRevenue();
        notifyDataSetChanged();
    }



    public static class RevenueViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nameOfRevenue, tv_dateRevenue, tv_moneyOfRevenue;
        public LinearLayout layout_type_of_revenue,layout_deleteRevenue,layout_updateRevenue;

        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameOfRevenue = itemView.findViewById(R.id.tv_nameOfRevenue);
            tv_dateRevenue = itemView.findViewById(R.id.tv_dateRevenue);
            tv_moneyOfRevenue = itemView.findViewById(R.id.tv_moneyOfRevenue);
            layout_type_of_revenue = itemView.findViewById(R.id.layout_type_of_revenue);
            layout_deleteRevenue = itemView.findViewById(R.id.layout_deleteRevenue);
            layout_updateRevenue = itemView.findViewById(R.id.layout_updateRevenue);


        }
    }
}
