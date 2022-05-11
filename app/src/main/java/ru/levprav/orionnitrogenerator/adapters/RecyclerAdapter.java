package ru.levprav.orionnitrogenerator.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.levprav.orionnitrogenerator.R;
import ru.levprav.orionnitrogenerator.retrofit.models.CodeRequestModel;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewRow> {

    public MutableLiveData<ArrayList<CodeRequestModel>> array;
    private final LayoutInflater inflater;

    public RecyclerAdapter(Context context, MutableLiveData<ArrayList<CodeRequestModel>> array){
        this.array = array;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_code, parent, false);
        return new ViewRow(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewRow holder, int position) {
        if(array.getValue() != null){
            CodeRequestModel model = array.getValue().get(position);
            holder.message.setText(model.getMessage());
            holder.code.setText(model.getRequestCode());
            if(model.getCode().equals("invalid"))
                holder.message.setBackgroundResource(R.color.invalid);
        }
    }

    @Override
    public int getItemCount() {
        if(array.getValue() != null)
            return array.getValue().size();
        return 0;
    }

    public static class ViewRow extends RecyclerView.ViewHolder {
        TextView code;
        TextView message;

        public ViewRow(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.cardType);
            code = itemView.findViewById(R.id.cardCode);

        }
    }
}
