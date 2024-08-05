package com.example.md18_and102_asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.md18_and102_asm.R;
import com.example.md18_and102_asm.activity.CartActivity;
import com.example.md18_and102_asm.activity.CheckoutActivity;
import com.example.md18_and102_asm.dao.SanPhamDAO;
import com.example.md18_and102_asm.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamUser1Adapter extends RecyclerView.Adapter<SanPhamUser1Adapter.ViewHolder> {
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
        notifyDataSetChanged(); // Thông báo cho adapter biết là dữ liệu đã thay đổi
    }

    public SanPhamUser1Adapter(Context context, ArrayList<SanPham> list, SanPhamDAO sanPhamDAO) {
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
        // Set dữ liệu lên từng item trên recycler
        SanPham currentItem = list.get(position);
        holder.tvTenSP.setText(currentItem.getTensp());
        holder.tvGiaBanSP.setText(String.valueOf(currentItem.getGiaban()) + " VNĐ ");
        holder.tvSoLuongSP.setText(" " + String.valueOf(currentItem.getSoluong()));


        // Lấy ảnh từ drawable hoặc từ Internet
        if (currentItem.getAvatar().startsWith("http://") || currentItem.getAvatar().startsWith("https://")) {
            Picasso.get().load(currentItem.getAvatar()).into(holder.imgViewSP);
        } else {
            int imgId = ((Activity) context).getResources().getIdentifier(
                    currentItem.getAvatar(), "drawable",
                    ((Activity) context).getPackageName());
            holder.imgViewSP.setImageResource(imgId);
        }

        // Gán sự kiện click cho nút tăng số lượng
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = currentItem.getSoluong();
                currentQuantity++; // Tăng số lượng
                currentItem.setSoluong(currentQuantity); // Cập nhật lại số lượng cho sản phẩm
                sanPhamDAO.updateSanPham(currentItem); // Cập nhật số lượng trong cơ sở dữ liệu
                notifyDataSetChanged(); // Cập nhật giao diện
            }
        });

        // Gán sự kiện click cho nút giảm số lượng
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = currentItem.getSoluong();
                if (currentQuantity > 0) { // Đảm bảo số lượng không âm
                    currentQuantity--; // Giảm số lượng
                    currentItem.setSoluong(currentQuantity); // Cập nhật lại số lượng cho sản phẩm
                    sanPhamDAO.updateSanPham(currentItem); // Cập nhật số lượng trong cơ sở dữ liệu
                    notifyDataSetChanged(); // Cập nhật giao diện
                } else {
                    // Xử lý khi số lượng sản phẩm là 0
                }
            }
        });

        // Gán sự kiện click cho nút "Thêm vào giỏ hàng"
        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Bundle để chứa dữ liệu
                Bundle bundle = new Bundle();
                bundle.putDouble("tongtien", currentItem.getGiaban() * currentItem.getSoluong());
                bundle.putString("tensp", currentItem.getTensp());
                bundle.putDouble("giasp", currentItem.getGiaban());
                bundle.putInt("soluong", currentItem.getSoluong());
                bundle.putString("hinhanh", currentItem.getAvatar());

                // Tạo Intent để chuyển đến CartActivity và đính kèm Bundle
                Intent intent = new Intent(context, CartActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
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
        public ImageView imgViewSP, icAddSP, icDeleteSP, icEditSP, imgAddToCart;
        public Button btnTang, btnGiam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Ánh xạ
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaBanSP = itemView.findViewById(R.id.tvGiaBanSP);
            tvSoLuongSP = itemView.findViewById(R.id.tvSoLuongValue);
            imgViewSP = itemView.findViewById(R.id.imgViewSP);
            icAddSP = itemView.findViewById(R.id.icAddSP);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            imgAddToCart = itemView.findViewById(R.id.imgAddToCart);

        }
    }
}
