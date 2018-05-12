package com.example.danilo.checklistapp;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danilo.checklistapp.database.DatabaseHelper;
import com.example.danilo.checklistapp.model.Checklist;
import com.example.danilo.checklistapp.model.ChecklistDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;

    private ConstraintLayout mLayoutMain;
    private ChecklistDAO mChecklistDAO;

    private RecyclerView mListChecklist;
    private ChecklistAdapter mChecklistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //localize este trecho de código
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLayoutMain = (ConstraintLayout) findViewById(R.id.mainActivity);

        mListChecklist = (RecyclerView) findViewById(R.id.ChecklistList);

        createConection();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mListChecklist.setLayoutManager(linearLayoutManager);

        mChecklistAdapter = new ChecklistAdapter(mChecklistDAO.listChecklists());

        mListChecklist.setAdapter(mChecklistAdapter);

        mListChecklist.setHasFixedSize(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove item from backing list here
                if(swipeDir==8) { //mover para direita
                    Checklist chk = mChecklistAdapter.getData().get(viewHolder.getLayoutPosition());
                    mChecklistDAO.remove(chk.getId());
                    mChecklistAdapter = new ChecklistAdapter(mChecklistDAO.listChecklists());
                    mListChecklist.setAdapter(mChecklistAdapter);
                }else if(swipeDir==4){ //mover para esquerda
                    Checklist chk = mChecklistAdapter.getData().get(viewHolder.getLayoutPosition());
                    chk.setActive(!chk.isActive());
                    mChecklistDAO.alter(chk);
                    mChecklistAdapter = new ChecklistAdapter(mChecklistDAO.listChecklists());
                    mListChecklist.setAdapter(mChecklistAdapter);
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(mListChecklist);

    }

    public void addChecklistView(View view){
        Intent cad = new Intent(MainActivity.this,ChecklistAdd.class);
        startActivityForResult(cad,0);
        //startActivity(cad);
    }


    private void createConection(){
        try{
            mDataHelper = new DatabaseHelper(this.getApplicationContext());
            mConection = mDataHelper.getWritableDatabase();
            mChecklistDAO = new ChecklistDAO(mConection);
            //testBank();
            //Toast.makeText(this,"Conexão criada com sucesso",Toast.LENGTH_SHORT).show();
            Snackbar.make(mLayoutMain, R.string.sucess_conection,Snackbar.LENGTH_LONG).show();

        }catch(SQLException e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    private void testBank(){
//        Checklist chk1 = new Checklist("Acordar cedo",true);
//        mChecklistDAO.insert(chk1);
//
//        Checklist result = mChecklistDAO.getChecklist(1);
//        Log.d("Checklist",result.getDescription());


//        Checklist chk1 = new Checklist("Acordar cedo",true);
//        Checklist chk2 = new Checklist("Dar aula",true);
//
//        mChecklistDAO.insert(chk1);
//        mChecklistDAO.insert(chk2);
//        Checklist chk = new Checklist("Dar aula hoje",true);
//        chk.setId(8);
//        chk.setActive(false);
//
//        mChecklistDAO.alter(chk);
//        List<String> allChecklists = mChecklistDAO.listChecklistsDescription();
//        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,allChecklists);
//        mListChecklist.setAdapter(adapter);


//        for(int i=0;i<test.size();i++){
//            Log.d("Checklist","ID: "+test.get(i).getId()+" - "+test.get(i).getDescription() + " ("+test.get(i).isActive()+")");
//        }

        //Checklist test = mChecklistDAO.getChecklist(1);
        //Toast.makeText(this,test.getDescription(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mChecklistAdapter = new ChecklistAdapter(mChecklistDAO.listChecklists());
        mListChecklist.setAdapter(mChecklistAdapter);
    }
}

