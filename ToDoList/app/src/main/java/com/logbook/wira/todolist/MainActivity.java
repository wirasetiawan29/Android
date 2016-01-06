package com.logbook.wira.todolist;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.logbook.wira.todolist.db.TaskContract;
import com.logbook.wira.todolist.db.TaskDBHelper;

public class MainActivity extends ListActivity {

    private ListAdapter listAdapter;
    private TaskDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                    addtask();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addtask () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a task");
        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task = inputField.getText().toString();

                helper = new TaskDBHelper(MainActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.Columns.TASK, task);

                db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add_task:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Add a task");
//                builder.setMessage("What do you want to do?");
//                final EditText inputField = new EditText(this);
//                builder.setView(inputField);
//                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String task = inputField.getText().toString();
//
//                        helper = new TaskDBHelper(MainActivity.this);
//                        SQLiteDatabase db = helper.getWritableDatabase();
//                        ContentValues values = new ContentValues();
//
//                        values.clear();
//                        values.put(TaskContract.Columns.TASK, task);
//
//                        db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
//                        updateUI();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", null);
//
//                builder.create().show();
//                return true;
//
//            default:
//                return false;
//        }
//    }

    private void updateUI() {
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK},
                new int[]{R.id.list},
                0
        );

        //klu make listactivity
       this.setListAdapter(listAdapter);

        //kalau make AppCompatActivity
//        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(listAdapter);
        ListView listView = getListView();

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    /*hapus data*/

                                    //listAdapter.remove(listAdapter.getItem(position));
                                    View v = (View) listView.getParent();
                                    TextView taskTextView = (TextView) v.findViewById(R.id.list);
                                    String task = taskTextView.getText().toString();

                                    String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                                            TaskContract.TABLE, TaskContract.Columns.TASK, task);


                                    helper = new TaskDBHelper(MainActivity.this);
                                    SQLiteDatabase sqlDB = helper.getWritableDatabase();
                                    sqlDB.execSQL(sql);
                                    updateUI();
                                    listAdapter.getItemId(position);
                                }
                                updateUI();
                                //listAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

        // Kalau ada fungsi tambahan pada detail list
//        final ViewGroup dismissableContainer = (ViewGroup) findViewById(R.id.list);
//        for (int i = 0; i < 20; i++) {
//            final Button dismissableButton = new Button(this);
//            dismissableButton.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            dismissableButton.setText("Button " + (i + 1));
//            dismissableButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(MainActivity.this,
//                            "Clicked " + ((Button) view).getText(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//            // Create a generic swipe-to-dismiss touch listener.
//            dismissableButton.setOnTouchListener(new SwipeDismissTouchListener(
//                    dismissableButton,
//                    null,
//                    new SwipeDismissTouchListener.DismissCallbacks() {
//                        @Override
//                        public boolean canDismiss(Object token) {
//                            return true;
//                        }
//
//                        @Override
//                        public void onDismiss(View view, Object token) {
//                            dismissableContainer.removeView(dismissableButton);
//                        }
//                    }));
//            dismissableContainer.addView(dismissableButton);
//        }

    }


    public void onDeleteButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.list);
        String task = taskTextView.getText().toString();


        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE, TaskContract.Columns.TASK, task);


        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }



    public void update (View view) {

//        switch (view.getParent()) {
            View u = (View) view.getParent();
        TextView taskUpdateView = (TextView) u.findViewById(R.id.list);
        final int id = taskUpdateView.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Update Data");
                final EditText inputField = new EditText(this);
                builder.setView(inputField);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String update = inputField.getText().toString();

//                        String sql = String.format("REPLACE INTO tasks (%s) VALUES %s",
//                                task, update);

//                        String sql = String.format("REPLACE INTO %s WHERE %s VALUES %s",
//                                TaskContract.TABLE, TaskContract.Columns.TASK, update);


                        helper = new TaskDBHelper(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        //db.execSQL(sql);
                        ContentValues values = new ContentValues();

                        values.clear();
                        values.put(TaskContract.Columns._ID, id);
                        values.put(TaskContract.Columns.TASK, update);

                        db.replace(TaskContract.TABLE, null, values);
                        //db.update(TaskContract.TABLE, update, values, null);
                        //db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        updateUI();
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.create().show();

        }

//    @Override
//    protected void onListItemClick(ListView listView, View view, int position, long id) {
//        Toast.makeText(this,
//                "Clicked " + getListAdapter().getItem(position).toString(),
//                Toast.LENGTH_SHORT).show();
//    }


}
