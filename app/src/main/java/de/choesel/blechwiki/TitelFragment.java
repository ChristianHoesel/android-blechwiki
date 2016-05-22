package de.choesel.blechwiki;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.model.Titel;
import de.choesel.blechwiki.orm.BlechWikiRepository;
import de.choesel.blechwiki.orm.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TitelFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private class myAsyncTask extends AsyncTask<Void, Void, List<Titel>> {

        DatabaseHelper databaseHelper;
        String buchId = "";

        private myAsyncTask(String buchId, Context context) {
            this.buchId = buchId;
            databaseHelper = new DatabaseHelper(context);
        }

        private myAsyncTask(Context context) {
            this("",context);
        }

        @Override
        protected void onPostExecute(List<Titel> result) {
            super.onPostExecute(result);
            databaseHelper.close();
            recyclerView.setAdapter(new TitelRecyclerViewAdapter(result, mListener));
            //mSwipeView.setRefreshing(false);
        }

        @Override
        protected List<Titel> doInBackground(Void... arg0) {
            BlechWikiRepository blechWikiRepository = new BlechWikiRepository(databaseHelper);
            if (buchId != null && !buchId.isEmpty()) {

                try {
                    Buch sample = new Buch();
                    sample.setId(UUID.fromString(buchId));
                    Buch buch = databaseHelper.getBuchDao().queryForSameId(sample);

                    List<Titel> result = new ArrayList<>(buch.getStuecke());
                    Collections.sort(result, new Comparator<Titel>() {
                        @Override
                        public int compare(Titel lhs, Titel rhs) {
                            return Double.valueOf(lhs.getNummer()).compareTo(Double.valueOf(rhs.getNummer()));
                        }
                    });
                    return result;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new ArrayList<>();
            }

            return new ArrayList<>();
        }
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TitelFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TitelFragment newInstance(int columnCount) {
        TitelFragment fragment = new TitelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titel_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TitelRecyclerViewAdapter(new ArrayList<Titel>(), mListener));
            Intent intent = getActivity().getIntent();
            String buchID = intent.getStringExtra(MainActivity.BUCH_ID);
            if(buchID != null){
                myAsyncTask myRequest = new myAsyncTask(buchID,getContext());
                myRequest.execute();
            }
            //recyclerView.setAdapter(new TitelRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Titel item);
    }
}
