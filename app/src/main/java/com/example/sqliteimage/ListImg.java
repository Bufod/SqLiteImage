package com.example.sqliteimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ListImg extends AppCompatActivity {

    DBServer dbServer;
    DBServer.Products products;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbServer = new DBServer(this);
        products = dbServer.new Products();

        list = findViewById(R.id.listView);
        ListAdapter listAdapter = new ListAdapter(this, products.selectAll());
        list.setAdapter(listAdapter);
    }


    public class ListAdapter extends BaseAdapter{
        LayoutInflater inflater;
        ArrayList<Preview> data;

        public ListAdapter(Context context, ArrayList<Preview> data) {
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            Preview ob = data.get(position);
            if (ob != null) {
                return ob.getId();
            }
            return -1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.item, null);

            ImageView image = convertView.findViewById(R.id.image);
            TextView description = convertView.findViewById(R.id.text);

            Preview preview = (Preview) getItem(position);

            ByteArrayInputStream imageStream = new ByteArrayInputStream(preview.getImage());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(theImage);

            description.setText(preview.getDescripton());

            return convertView;
        }
    }
}
