package com.example.md18_and102_asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.dao.SanPhamDAO;
import com.example.md18_and102_asm.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamUserAdapter extends RecyclerView.Adapter<SanPhamUserAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SanPham> list;
    private final SanPhamDAO sanPhamDAO;

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<SanPham> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(list);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (SanPham item : list) {
                        if (item.getTensp().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((ArrayList<SanPham>) results.values);
                notifyDataSetChanged();
            }
        };
    }
    public void setSanPhamList(ArrayList<SanPham> sanPhamList) {
        this.list = sanPhamList;
    }
    public SanPhamUserAdapter(Context context, ArrayList<SanPham> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_user,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set dữ liệu lên từng item trên recycler
        holder.tvTenSP.setText(list.get(position).getTensp());
        holder.tvGiaBanSP.setText(String.valueOf(list.get(position).getGiaban()) + " VNĐ ");
        holder.tvSoLuongSP.setText("SL: " + String.valueOf(list.get(position).getSoluong()));

        //lấy ảnh từ drawable
        int imgId = ((Activity)context).getResources().getIdentifier(
                    list.get(position).getAvatar(),"drawable",
                ((Activity)context).getPackageName());
        holder.imgViewSP.setImageResource(imgId);
        //lấy ảnh từ Internet
        if (list.get(position).getAvatar().startsWith("http://")||
                list.get(position).getAvatar().startsWith("https://")){
            Picasso.get().load(list.get(position).getAvatar()).into(holder.imgViewSP);
        }
    }
    private void dialogUpdateSanPham(SanPham sanPhamUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        //Ánh xạ các widget
        TextInputEditText edtTenSPEdit = view.findViewById(R.id.edtTenSPEdit);
        TextInputEditText edtGiaBanEdit = view.findViewById(R.id.edtGiaBanEdit);
        TextInputEditText edtSoLuongEdit = view.findViewById(R.id.edtSoLuongEdit);
        TextInputEditText edtLinkEdit = view.findViewById(R.id.edtLinkEdit);

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        //set dữ liệu lên các edt để lấy giá trị cũ của SanPham cần update
        edtTenSPEdit.setText(sanPhamUpdate.getTensp());
        edtGiaBanEdit.setText(String.valueOf(sanPhamUpdate.getGiaban()));
        edtSoLuongEdit.setText(String.valueOf(sanPhamUpdate.getSoluong()));
        edtLinkEdit.setText(sanPhamUpdate.getAvatar());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTenSP, tvGiaBanSP, tvSoLuongSP;
        public ImageView imgViewSP, icAddSP, icDeleteSP, icEditSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Ánh xạ
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaBanSP = itemView.findViewById(R.id.tvGiaBanSP);
            tvSoLuongSP = itemView.findViewById(R.id.tvSoLuongSP);
            imgViewSP = itemView.findViewById(R.id.imgViewSP);
            icAddSP = itemView.findViewById(R.id.icAddSP);
        }
    }
}
