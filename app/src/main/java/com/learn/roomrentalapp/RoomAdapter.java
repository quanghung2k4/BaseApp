package com.learn.roomrentalapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Context context;
    private List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText(room.getName());
        holder.tvRoomPrice.setText(String.format("Giá thuê: %.0f VNĐ", room.getPrice()));

        if (room.isRented()) {
            holder.tvRoomStatus.setText("Đã thuê");
            holder.tvRoomStatus.setTextColor(Color.RED);
        } else {
            holder.tvRoomStatus.setText("Còn trống");
            holder.tvRoomStatus.setTextColor(Color.GREEN);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditRoomActivity.class);
            intent.putExtra("room_position", position);
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa phòng")
                    .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        roomList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, roomList.size());
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvRoomPrice, tvRoomStatus;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRoomPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvRoomStatus = itemView.findViewById(R.id.tvRoomStatus);
        }
    }
}
