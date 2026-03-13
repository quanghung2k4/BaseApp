package com.learn.roomrentalapp;

import android.content.Context;
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
    private OnRoomItemClickListener listener;

    public RoomAdapter(Context context, List<Room> roomList, OnRoomItemClickListener listener) {
        this.context = context;
        this.roomList = roomList;
        this.listener = listener;
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
            if (listener != null) {
                listener.onRoomItemClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onRoomItemLongClick(position);
            }
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

    public interface OnRoomItemClickListener {
        void onRoomItemClick(int position);
        void onRoomItemLongClick(int position);
    }
}
