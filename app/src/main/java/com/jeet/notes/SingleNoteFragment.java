package com.jeet.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static com.jeet.notes.MainActivity.uname;




public class SingleNoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "notePosition";
    private static final String ARG_PARAM2 = "param2";


    private int notePosition;
    private NoteEntry noteEntry;
    private EditText titleEditText;
    private EditText bodyEditText;


    private OnFragmentInteractionListener mListener;
    private SQLiteDatabase db;
    private NoteReaderHelper mDbHelper;

    public SingleNoteFragment() {
        // Required empty public constructor
    }

    public static SingleNoteFragment newInstance(int  notePosition, String param2) {
        SingleNoteFragment fragment = new SingleNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, notePosition);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notePosition = getArguments().getInt(ARG_PARAM1);
            noteEntry = NoteEntry.notesList.get(notePosition);
        }
        mDbHelper = new NoteReaderHelper(getContext());
        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_note, container, false);

        updateUI(v);
        return v;
    }

    private void updateUI(View v) {
        titleEditText= (EditText) v.findViewById(R.id.fragmentNoteTitle);
        bodyEditText = (EditText) v.findViewById(R.id.fragmentNoteBody);
        titleEditText.setText(noteEntry.getTitle());
        bodyEditText.setText(noteEntry.getText());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.mOptionsMenu.setGroupVisible(R.id.single_note_group,true);
    }

    public void deleteNote(){
        // Define 'where' part of query.
        String selection = NoteContract.NoteEntryDb._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(noteEntry.getId()) };
        // Issue SQL statement.
        db.delete(NoteContract.NoteEntryDb.TABLE_NAME, selection, selectionArgs);
        if ( noteEntry != null){
            Log.d("SingleFr",noteEntry.getId()+"");
            NoteEntry.notesList.remove(NoteEntry.notesList.lastIndexOf(noteEntry));
            noteEntry = null;
        }
        getActivity().onBackPressed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(noteEntry != null) insertOrUpdateToDB();
        MainActivity.mOptionsMenu.setGroupVisible(R.id.single_note_group,false);
    }

    private void insertOrUpdateToDB(){
        noteEntry.setTitle(titleEditText.getText().toString());
        noteEntry.setText(bodyEditText.getText().toString());
        noteEntry.setUsername(uname);

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntryDb.COLUMN_NAME_TITLE, noteEntry.getTitle());
        values.put(NoteContract.NoteEntryDb.COLUMN_NAME_BODY, noteEntry.getText());
        values.put(NoteContract.NoteEntryDb.USERNAME, noteEntry.getUsername());


        String selection = NoteContract.NoteEntryDb._ID + " = ?";
        String[] selectionArgs = { noteEntry.getId()+"" };

        Log.d("SingleFrag", "Entry on position "+ NoteEntry.notesList.lastIndexOf(noteEntry));

        int count = db.update(
                    NoteContract.NoteEntryDb.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        Log.d("SingleFrag",count+" rows updated");



        mDbHelper.close();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
