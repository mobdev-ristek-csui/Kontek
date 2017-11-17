package ga.anggach.kontek.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ga.anggach.kontek.R;

/**
 * Created by master on 11/17/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    /* Callback for list item click events */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name;

        public DataHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        public void setData(Contact contact) {
            name.setText(contact.getName());
        }

        @Override
        public void onClick(View v) {
            postItemClick(this);
        }
    }

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> contactsBackup;

    public DataAdapter(ArrayList<Contact> contacts){
        this.contacts = contacts;
        this.contactsBackup = (ArrayList<Contact>) contacts.clone();
    }

    public void setData(ArrayList<Contact> contacts){
        this.contacts = contacts;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void postItemClick(DataHolder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
        }
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("angga","createviewholder");
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_contact_item, parent, false);

        return new DataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        holder.setData(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public Contact getItem(int position){
        return contacts.get(position);
    }

    public void filter(String query){
        contacts.clear();
        if(query == ""){
            contacts = (ArrayList<Contact>) contactsBackup.clone();
        }else{
            for(int i = 0; i< contactsBackup.size(); i++){
                if(contactsBackup.get(i).getName().contains(query))
                    contacts.add(contactsBackup.get(i));
            }
        }
        notifyDataSetChanged();
    }

}
