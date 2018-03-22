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

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.PeopleViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */

    public PeopleListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }


    //note: grabs the people list layout
    @Override
    public PeopleListAdapter.PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.people_list_item, parent, false);
        return new PeopleListAdapter.PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, final int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PNAME));
        String summery = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PSUMMERY));
        String small = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PAGE)) + " | "
                + mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PRANK)) + " | "
                + mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PRELATION));

        holder.nameTextView.setText(name);
        holder.summeryTextView.setText(summery);
        holder.smallTextView.setText(small);

        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!mCursor.moveToPosition(position))
                    return false; // bail if returned null
                String deleteInfo = mCursor.getString(mCursor.getColumnIndex(RumorsContract.RumorsEntry._ID));

                Class startPeople = People.class;
                Intent startPeopleIntent = new Intent(v.getContext(), startPeople);
                startPeopleIntent.putExtra("deleteInfo", deleteInfo);

                v.getContext().startActivity(startPeopleIntent);

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
    class PeopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView summeryTextView;
        TextView smallTextView;
        Context context;

        //used for longclicklistener
        public View v;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                {@link PeopleListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public PeopleViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.person_name);
            summeryTextView = (TextView) itemView.findViewById(R.id.person_summery);
            smallTextView = (TextView) itemView.findViewById(R.id.person_short);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
            //allows it to be clickable
            this.v = itemView;
            //allows the longclick
        }

        @Override
        public void onClick(View v) {
            if (!mCursor.moveToPosition(getLayoutPosition()))
                return; // bail if returned null
            String name = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PNAME));
            String summary = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PSUMMERY));
            String age = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PAGE));
            String rank = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PRANK));
            String relation = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry.COLUMN_PRELATION));
            String ID = mCursor.getString(mCursor.getColumnIndex(RumorsContract.PeopleEntry._ID));

            Class startPeopleEdit = PeopleEdit.class;
            Intent startPeopleEditIntent = new Intent(context, startPeopleEdit);
            startPeopleEditIntent.putExtra("sentName", name);
            startPeopleEditIntent.putExtra("sentSummery", summary);
            startPeopleEditIntent.putExtra("sentAge", age);
            startPeopleEditIntent.putExtra("sentRank", rank);
            startPeopleEditIntent.putExtra("sentRelation", relation);
            startPeopleEditIntent.putExtra("entryID", ID);

            context.startActivity(startPeopleEditIntent);
        }
    }
}
