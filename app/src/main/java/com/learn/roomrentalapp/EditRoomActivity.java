package com.learn.roomrentalapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class EditRoomActivity extends AppCompatActivity {

    private EditText etRoomId, etRoomName, etRoomPrice;
    private SwitchCompat swRentedStatus;
    private Button btnSave, btnDelete;
    private int roomPosition;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etRoomId = findViewById(R.id.etRoomId);
        etRoomName = findViewById(R.id.etRoomName);
        etRoomPrice = findViewById(R.id.etRoomPrice);
        swRentedStatus = findViewById(R.id.swRentedStatus);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        roomPosition = getIntent().getIntExtra("room_position", -1);

        if (roomPosition != -1) {
            room = RoomData.roomList.get(roomPosition);
            etRoomId.setText(room.getId());
            etRoomName.setText(room.getName());
            etRoomPrice.setText(String.valueOf(room.getPrice()));
            swRentedStatus.setChecked(room.isRented());
        }

        btnSave.setOnClickListener(v -> {
            String name = etRoomName.getText().toString().trim();
            String priceStr = etRoomPrice.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            boolean isRented = swRentedStatus.isChecked();

            room.setName(name);
            room.setPrice(price);
            room.setRented(isRented);

            finish();
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xóa phòng")
                    .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        RoomData.roomList.remove(roomPosition);
                        finish();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }
}