package com.example.robin.scrollscribe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robin.scrollscribe.data.RumorsContract;


public class RumorsListAdapter extends RecyclerView.Adapter<RumorsListAdapter.RumorViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */

    public RumorsListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }


    //note: grabs the rumors list layout
    @Override
    public RumorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rumors_list_item, parent, false);
        return new RumorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RumorViewHolder holder, final int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry.COLUMN_RNAME));
        Log.d("Debug", "Adapter name: " + name);
        String summery = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry.COLUMN_RSUMMERY));
        Log.d("Debug", "Adapter summery: " + name);

        holder.nameTextView.setText(name);
        holder.summeryTextView.setText(summery);

        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!mCursor.moveToPosition(position))
                    return false; // bail if returned null
                String deleteInfo = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry._ID));

                Class startRumors = Rumors.class;
                Intent startRumorsIntent = new Intent(v.getContext(), startRumors);
                startRumorsIntent.putExtra("deleteInfo", deleteInfo);

                v.getContext().startActivity(startRumorsIntent);

                return true;
            }
        });


    }


    // returns the number of items in the rumors recycler view
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class RumorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView summeryTextView;
        Context context;

        //used for longclicklistener
        public View v;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                {@link RumorsListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public RumorViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.rumor_name);
            summeryTextView = (TextView) itemView.findViewById(R.id.rumor_summery);
            itemView.setOnClickListener(this);
            //allows it to be clickable
            context = itemView.getContext();
            //allows the longclick
            this.v = itemView;
        }

        @Override
        public void onClick(View v) {
            if (!mCursor.moveToPosition(getLayoutPosition()))
                return; // bail if returned null
            String name = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry.COLUMN_RNAME));
            String summary = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry.COLUMN_RSUMMERY));
            String ID = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry._ID));

            Class startRumorsEdit = RumorsEdit.class;
            Intent startRumorsEditIntent = new Intent(context, startRumorsEdit);
            startRumorsEditIntent.putExtra("sentName", name);
            startRumorsEditIntent.putExtra("sentSummery", summary);
            startRumorsEditIntent.putExtra("entryID", ID);

            context.startActivity(startRumorsEditIntent);
        }
    }



}
