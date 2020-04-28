package com.example.sqliteimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    DBServer dbServer;
    DBServer.Products products;
    Button downloadBt, saveBt, viewBt;
    Bitmap byteImg;
    ImageView imageView;
    EditText et, nameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        nameEt = findViewById(R.id.nameEt);


        dbServer = new DBServer(this);
        products = dbServer.new Products();

        et = findViewById(R.id.editText);

        downloadBt = findViewById(R.id.downloadBt);
        saveBt = findViewById(R.id.saveBt);
        viewBt = findViewById(R.id.viewBt);


        viewBt.setOnClickListener(viewBtOnClick());
        downloadBt.setOnClickListener(downloadBtOnClick());
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInBd();
            }
        });

    }

    private View.OnClickListener viewBtOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListImg.class);
                startActivity(intent);
            }
        };
    }
    
    public void saveInBd() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byteImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        products.insert(imageInByte, nameEt.getText().toString());
        Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener downloadBtOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream in = null;
                        try {
                            String url = et.getText().toString();
                            in = new java.net.URL(url).openStream();
                            byteImg = BitmapFactory.decodeStream(in);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(byteImg);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };
    }


}
