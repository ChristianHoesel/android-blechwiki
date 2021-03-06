package de.choesel.blechwiki;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import de.choesel.blechwiki.model.BlaeserWikiFactory;
import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.orm.BlechWikiRepository;
import de.choesel.blechwiki.orm.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BuchFragment extends Fragment {

    private BuchRecyclerViewAdapter buchRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeView;

    private class myAsyncTask extends AsyncTask<Void, Void, List<Buch>> {

        DatabaseHelper databaseHelper;
        String suchstring = "";

        private myAsyncTask(String suchstring, Context context) {
            this.suchstring = suchstring;
            databaseHelper = new DatabaseHelper(context);
        }

        private myAsyncTask(Context context) {
            this("",context);
        }

        @Override
        protected void onPostExecute(List<Buch> result) {
            super.onPostExecute(result);
            databaseHelper.close();
            recyclerView.setAdapter(new BuchRecyclerViewAdapter(result, mListener));
            mSwipeView.setRefreshing(false);
        }

        @Override
        protected List<Buch> doInBackground(Void... arg0) {
            BlechWikiRepository blechWikiRepository = new BlechWikiRepository(databaseHelper);
            if (suchstring != null && !suchstring.isEmpty()) {

                try {

                    Dao<Buch, Integer> buchDao = databaseHelper.getBuchDao();
                    QueryBuilder<Buch, Integer> buchIntegerQueryBuilder = buchDao.queryBuilder();
                    Where<Buch, Integer> where = buchIntegerQueryBuilder.where();

                    Where<Buch, Integer> like = where.like("titel", "%"+suchstring+"%");

                    return like.query();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return blechWikiRepository.getBuecher();
            }

            return blechWikiRepository.getBuecher();
        }
    }

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BuchFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BuchFragment newInstance(int columnCount) {
        BuchFragment fragment = new BuchFragment();
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
        View view = inflater.inflate(R.layout.fragment_buch_list, container, false);


        View listView = view.findViewById(R.id.list);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            recyclerView = (RecyclerView) listView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            buchRecyclerViewAdapter = new BuchRecyclerViewAdapter(new ArrayList<Buch>(), mListener);

            recyclerView.setAdapter(buchRecyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), null));
        }

        if(view instanceof SwipeRefreshLayout){
            mSwipeView = (SwipeRefreshLayout) view;
            mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    myAsyncTask myRequest = new myAsyncTask(getContext());
                    myRequest.execute();
                }
            });
        }

        myAsyncTask myRequest = new myAsyncTask(getContext());
        myRequest.execute();

        return view;
    }


    public void setSuchstring(final String suchstring) {
        myAsyncTask myRequest = new myAsyncTask(suchstring,getActivity());
        myRequest.execute();
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
        void onListFragmentInteraction(Buch item);
    }
}
