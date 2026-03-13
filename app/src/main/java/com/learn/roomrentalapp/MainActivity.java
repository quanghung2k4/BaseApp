package com.learn.roomrentalapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnRoomItemClickListener {

    private static final int ADD_ROOM_REQUEST_CODE = 1;
    private static final int EDIT_ROOM_REQUEST_CODE = 2;

    private RecyclerView rvRooms;
    private RoomAdapter adapter;
    private List<Room> roomList;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo dữ liệu mẫu trực tiếp
        roomList = new ArrayList<>();
        roomList.add(new Room("P101", "Phòng 101", 2500000, true));
        roomList.add(new Room("P102", "Phòng 102", 3000000, false));

        initViews();
        setupRecyclerView();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
            startActivityForResult(intent, ADD_ROOM_REQUEST_CODE);
        });
    }

    private void initViews() {
        rvRooms = findViewById(R.id.rvRooms);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void setupRecyclerView() {
        adapter = new RoomAdapter(this, roomList, this);
        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        rvRooms.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_ROOM_REQUEST_CODE) {
                Room newRoom = (Room) data.getSerializableExtra("new_room");
                if (newRoom != null) {
                    roomList.add(newRoom);
                    adapter.notifyItemInserted(roomList.size() - 1);
                    Toast.makeText(this, "Thêm phòng thành công!", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == EDIT_ROOM_REQUEST_CODE) {
                int position = data.getIntExtra("room_position", -1);
                boolean isDeleted = data.getBooleanExtra("is_deleted", false);

                if (position != -1) {
                    if (isDeleted) {
                        roomList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, roomList.size());
                        Toast.makeText(this, "Xóa phòng thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Room updatedRoom = (Room) data.getSerializableExtra("updated_room");
                        if (updatedRoom != null) {
                            roomList.set(position, updatedRoom);
                            adapter.notifyItemChanged(position);
                            Toast.makeText(this, "Cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRoomItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditRoomActivity.class);
        intent.putExtra("room_position", position);
        intent.putExtra("room_object", roomList.get(position));
        startActivityForResult(intent, EDIT_ROOM_REQUEST_CODE);
    }

    @Override
    public void onRoomItemLongClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa phòng")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, roomList.size());
                    Toast.makeText(MainActivity.this, "Xóa phòng thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
