package de.choesel.blechwiki;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.choesel.blechwiki.model.Buch;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Buch} and makes a call to the
 * specified {@link BuchFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BuchRecyclerViewAdapter extends RecyclerView.Adapter<BuchRecyclerViewAdapter.ViewHolder> {

    private final List<Buch> mValues;
    private final BuchFragment.OnListFragmentInteractionListener mListener;

    public BuchRecyclerViewAdapter(List<Buch> items, BuchFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_buch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Buch buch = mValues.get(position);
        holder.mItem = buch;
        holder.titelView.setText(buch.getTitel());

        String untertitel = buch.getUntertitel();
        if(untertitel == null || untertitel.isEmpty()){
            holder.untertitelView.setVisibility(View.INVISIBLE);
        }else{
            holder.untertitelView.setVisibility(View.VISIBLE);
            holder.untertitelView.setText(untertitel);
        }

        String verlag = buch.getVerlag();
        int erscheinjahr = buch.getErscheinjahr();
        if(verlag == null || verlag.isEmpty()){
            holder.verlagView.setVisibility(View.INVISIBLE);
        }else{
            holder.untertitelView.setVisibility(View.VISIBLE);
            if(erscheinjahr < 1500){
                //vorher gab es noch keinen Buchdruck
                holder.verlagView.setText( verlag);
            }else{
                holder.verlagView.setText( verlag+ " ("+erscheinjahr+")");
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titelView;
        public final TextView untertitelView;
        public final TextView verlagView;
        public Buch mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titelView = (TextView) view.findViewById(R.id.buchTitel);
            untertitelView = (TextView) view.findViewById(R.id.untertitel);
            verlagView = (TextView)view.findViewById(R.id.verlag);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titelView.getText() + "'";
        }
    }
}
