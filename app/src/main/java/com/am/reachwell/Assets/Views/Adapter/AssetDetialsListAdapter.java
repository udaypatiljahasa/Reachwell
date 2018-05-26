package com.am.reachwell.Assets.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am.reachwell.Assets.Models.AssetListModel;
import com.am.reachwell.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AssetDetialsListAdapter extends RecyclerView.Adapter<AssetDetialsListAdapter.AssetViewHolder> {
    private ArrayList<AssetListModel> assetList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    public AssetDetialsListAdapter(Context context,ArrayList<AssetListModel> assetList,OnItemClickListener onItemClickListener){
        this.context = context;
        this.assetList = assetList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AssetViewHolder(LayoutInflater.from(context).inflate(R.layout.asset_list_row, parent, false),onItemClickListener);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder holder, int position) {
        holder.description.setText(assetList.get(position).getAssetDesc());
        holder.name.setText(assetList.get(position).getAssetName());
        holder.assetTag.setText(assetList.get(position).getAssetTag());
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public class AssetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView description;
        TextView name,assetTag;
        OnItemClickListener onItemClickListener;

        public AssetViewHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            description = (TextView) itemView.findViewById(R.id.asset_desc_type_txt);
            name = (TextView) itemView.findViewById(R.id.asset_name_txt);
            assetTag = (TextView) itemView.findViewById(R.id.asset_tag_txt);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    public void setAssetList(ArrayList<AssetListModel> assetList){
        this.assetList = assetList;
        notifyDataSetChanged();
    }
}
