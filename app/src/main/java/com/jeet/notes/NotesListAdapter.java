package com.jeet.notes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    private ArrayList<NoteEntry> mDataset;
    private static View.OnClickListener mOnClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView titleTextView;
        public TextView bodyTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            titleTextView = (TextView) itemView.findViewById(R.id.noteTile);
            bodyTextView = (TextView) itemView.findViewById(R.id.noteBody);
            itemView.setOnClickListener(mOnClickListener);
        }

    }

    public NotesListAdapter(ArrayList<NoteEntry> myDataset,View.OnClickListener mOnClickListener) {
        mDataset = myDataset;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NoteEntry entry = mDataset.get(position);
        holder.titleTextView.setText(entry.getTitle());
        holder.bodyTextView.setText(entry.getText());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}