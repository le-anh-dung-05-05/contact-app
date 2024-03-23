package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.contactapp.databinding.ActivityDetailBinding;
import com.example.contactapp.databinding.ActivityMainBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private Bundle bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        bd = getIntent().getExtras();
        if(bd == null) {
            return;

        }
        Contact contact = (Contact) bd.get("contact");
        binding.tvFullname.setText(contact.getFullname());
        binding.tvPhone.setText(contact.getMobile());
        binding.tvAbout.setText("About " + contact.getFirstName());
        binding.textView7.setText(contact.getEmail());
        if (contact.getAvatar() != null) {
            Bitmap originalBitmap = DataConvert.convertByteArrayToImage(contact.getAvatar());
            if (originalBitmap != null) {
                // Resize ảnh về kích thước mới (300dp x 300dp)
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 400, 360, true);

                // Hiển thị ảnh đã resize
                binding.imageView.setImageBitmap(resizedBitmap);
            }
        }


        ImageButton btnClose = (ImageButton) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();

            }
        });
        ImageButton btnEdit = (ImageButton) findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contact);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
    public void goToMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}