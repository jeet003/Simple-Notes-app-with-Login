package com.jeet.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.jeet.notes.MainActivity.uname;

public class NoteListFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private ArrayList<NoteEntry> notesList;


    NotesListAdapter notesListAdapter;

    private RecyclerView notesRecyclerView;
    private LinearLayoutManager notesLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private OnFragmentInteractionListener mListener;

    public NoteListFragment() {
        notesList = NoteEntry.notesList;
        notesListAdapter = new NotesListAdapter(notesList,this);

    }

    public static NoteListFragment newInstance(String param1, String param2, String param3) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3= getArguments().getString(ARG_PARAM3);
        }

        readEntriesFromDB();
    }


    private void readEntriesFromDB(){
        NoteReaderHelper mDbHelper = new NoteReaderHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.NoteEntryDb._ID,
                NoteContract.NoteEntryDb.COLUMN_NAME_TITLE,
                NoteContract.NoteEntryDb.COLUMN_NAME_BODY,
                NoteContract.NoteEntryDb.USERNAME
        };
        String whereClause = "username = ?";
        String[] whereArgs = new String[] {
                uname
        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NoteContract.NoteEntryDb._ID + " ASC";

        Cursor cursor = db.query(
                NoteContract.NoteEntryDb.TABLE_NAME,        // The table to query
                projection,                                 // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                                  // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        while(cursor.moveToNext()) {
            NoteEntry entry = new NoteEntry();
            Log.d("ListFragment",cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntryDb._ID)));
            entry.setTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.NoteEntryDb.COLUMN_NAME_TITLE)));
            entry.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.NoteEntryDb.COLUMN_NAME_BODY)));
            entry.setId((int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(NoteContract.NoteEntryDb._ID)));
            entry.setUsername(cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteContract.NoteEntryDb.USERNAME)
            ));
            notesList.add(entry);
        }
        notesListAdapter.notifyDataSetChanged();
        cursor.close();
        mDbHelper.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_note_list, container, false);

        notesRecyclerView = (RecyclerView) inf.findViewById(R.id.notes_recycler_view);

        notesRecyclerView.setHasFixedSize(true);

        notesLayoutManager= new LinearLayoutManager(this.getContext());
        notesRecyclerView.setLayoutManager(notesLayoutManager);

        notesRecyclerView.setAdapter(notesListAdapter);
        return inf;
    }


    public void onAddNotePressed() {
        NoteEntry entry = new NoteEntry();


        NoteReaderHelper mDbHelper = new NoteReaderHelper(getContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntryDb.COLUMN_NAME_TITLE, entry.getTitle());
        values.put(NoteContract.NoteEntryDb.COLUMN_NAME_BODY, "");
        values.put(NoteContract.NoteEntryDb.USERNAME,entry.getUsername());

        int rowID  = (int) db.insert(NoteContract.NoteEntryDb.TABLE_NAME, null, values);
        entry.setId(rowID);
        Log.d("SingleFrag","New row added with id= " + rowID);

        notesList.add(entry);
        notesListAdapter.notifyItemInserted(notesList.size()-1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int itemPosition = notesRecyclerView.getChildLayoutPosition(v);
        mListener.onFragmentInteraction(itemPosition);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int pos);
    }

}
