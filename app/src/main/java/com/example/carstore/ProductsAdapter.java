package com.example.carstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>{
    private Context context;
    private List<ProductModel> productModelList;

    public ProductsAdapter(Context context) {
        this.context = context;
        productModelList=new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel productModel=productModelList.get(position);
        holder.title.setText(productModel.getTitle());
        holder.description.setText(productModel.getPrice());
        holder.price.setText(productModel.getTitle());
        holder.title.setText(productModel.getTitle());

        Glide.with(context).load(productModel.getImage())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
       return  productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title , description, price;
        private ImageView img;

        public MyViewHolder(View itemView){
            super((itemView));
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.image);


        }
    }
}
