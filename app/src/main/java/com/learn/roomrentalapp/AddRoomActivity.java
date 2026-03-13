package com.learn.roomrentalapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class AddRoomActivity extends AppCompatActivity {

    private EditText etRoomId, etRoomName, etRoomPrice;
    private SwitchCompat swRentedStatus;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etRoomId = findViewById(R.id.etRoomId);
        etRoomName = findViewById(R.id.etRoomName);
        etRoomPrice = findViewById(R.id.etRoomPrice);
        swRentedStatus = findViewById(R.id.swRentedStatus);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String id = etRoomId.getText().toString().trim();
            String name = etRoomName.getText().toString().trim();
            String priceStr = etRoomPrice.getText().toString().trim();

            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            boolean isRented = swRentedStatus.isChecked();

            Room newRoom = new Room(id, name, price, isRented);
            RoomData.roomList.add(newRoom);
            finish();
        });
    }
}