package miri.pro.mnews_app.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miri.pro.mnews_app.R;

public class RecycleViewCategoryAdapter extends RecyclerView.Adapter<RecycleViewCategoryAdapter.ViewHolder> {
    private List<RecycleViewCategoryModel> categoriesList;
    private Context context;
    private CategoryClickEvent categoryClickEvent;

    public RecycleViewCategoryAdapter(List<RecycleViewCategoryModel> categoriesList, Context context, CategoryClickEvent categoryClickEvent) {
        this.categoriesList = categoriesList;
        this.context = context;
        this.categoryClickEvent = categoryClickEvent;
    }

    @NonNull
    @Override
    public RecycleViewCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new RecycleViewCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewCategoryAdapter.ViewHolder holder, int position) {
        RecycleViewCategoryModel category = categoriesList.get(position);
        holder.categoryNameTexView.setText(category.getCategory());
        Picasso.get().load(category.getUrlImageUrl()).into(holder.categoryImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: to be reviewed
                categoryClickEvent.onCategoryItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public interface CategoryClickEvent{
        // handle click event: onclick load new data ...
        void onCategoryItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView categoryImageView;
        private TextView categoryNameTexView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.categoryImageView);
            categoryNameTexView = itemView.findViewById(R.id.categoryNameTexView);

        }
    }
}
