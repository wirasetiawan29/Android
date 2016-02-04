package com.wirasetiawan.headerfootercardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wira on 2/4/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    Header header;
    List<ListItem> listItems;
    Footer footer;

    public MyRecyclerAdapter(Header header, List<ListItem> listItems, Footer footer)
    {
        this.header = header;
        this.listItems = listItems;
        this.footer = footer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_header, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new VHItem(v);
        }
        else if (viewType == TYPE_FOOTER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_footer, parent, false);
        }
        return null;
    }

    private ListItem getItem(int position)
    {
        return listItems.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader)holder;
            VHheader.txtHeader.setText(header.getHeader());
        }
        else if(holder instanceof VHItem)
        {
            ListItem currentItem = getItem(position-1);
            VHItem VHitem = (VHItem)holder;
            VHitem.txtName.setText(currentItem.getName());
            VHitem.iv.setBackgroundResource(currentItem.getId());
        }
        else if (holder instanceof VHFooter){
            VHFooter VHfooter = (VHFooter)holder;
            VHFooter.txtFooter.setText(footer.getFooter());
        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return listItems.size()+1;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView txtHeader;
        public VHHeader(View itemView) {
            super(itemView);
            this.txtHeader = (TextView)itemView.findViewById(R.id.txtHeader);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView iv;
        public VHItem(View itemView) {
            super(itemView);
            this.txtName = (TextView)itemView.findViewById(R.id.txtName);
            this.iv = (ImageView)itemView.findViewById(R.id.ivListItem);
        }
    }

    class VHFooter extends RecyclerView.ViewHolder{
        TextView txtFooter;
        public VHFooter(View itemView){
            super(itemView);
            this.txtFooter = (TextView)itemView.findViewById(R.id.txtFooter);
        }
    }
}
