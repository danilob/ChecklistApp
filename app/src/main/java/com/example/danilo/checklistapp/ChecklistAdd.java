package com.example.danilo.checklistapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danilo.checklistapp.database.DatabaseHelper;
import com.example.danilo.checklistapp.model.Checklist;
import com.example.danilo.checklistapp.model.ChecklistDAO;

public class ChecklistAdd extends AppCompatActivity {

    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;

    private ConstraintLayout mLayoutMain;
    private ChecklistDAO mChecklistDAO;

    private EditText mEditTextDescription;
    private CheckBox mCheckBoxActive;

    private Checklist mChecklist;

    private Checklist mCopy;
    private int mState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextDescription = (EditText) findViewById(R.id.editTextDesc);
        mCheckBoxActive = (CheckBox) findViewById(R.id.checkBoxActive);

        mLayoutMain = (ConstraintLayout) findViewById(R.id.layoutAddChecklist);

        createConection();
        clearForm();
        checkParameters();

    }

    private void checkParameters(){
        Bundle bundle = getIntent().getExtras();

        if((bundle!=null) && bundle.containsKey("CHECKLIST")){
            mCopy = (Checklist) bundle.getSerializable("CHECKLIST");
            mEditTextDescription.setText(mCopy.getDescription());
            mCheckBoxActive.setChecked(mCopy.isActive());
            mState = 1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_checklist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_ok:
                confirm();
                break;
            case R.id.action_excluir:
                if(mState==1){
                    mChecklistDAO.remove(mCopy.getId());
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createConection(){
        try{
            mDataHelper = new DatabaseHelper(this.getApplicationContext());
            mConection = mDataHelper.getWritableDatabase();
            mChecklistDAO = new ChecklistDAO(mConection);
            //Snackbar.make(mLayoutMain, R.string.sucess_conection,Snackbar.LENGTH_LONG).show();
        }catch(SQLException e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    private void confirm(){
        if (validateForm() == false){
            try {
                if(mState==0) {
                    mChecklistDAO.insert(mChecklist);
                }else{
                    mChecklistDAO.alter(mChecklist);
                }
                finish();
            }catch (SQLException e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean validateForm(){
        boolean res;
        String desc = mEditTextDescription.getText().toString();
        boolean active = mCheckBoxActive.isChecked();

        mChecklist = new Checklist();
        if(mState==1){
            mChecklist.setId(mCopy.getId());
        }
        mChecklist.setDescription(desc);
        mChecklist.setActive(active);

        if (res = desc.isEmpty()){
            mEditTextDescription.requestFocus();
            AlertDialog.Builder diag = new AlertDialog.Builder(this);
            diag.setTitle("Campo Vazio");
            diag.setMessage("Campo descrição inválido!");
            diag.setNeutralButton("Ok",null);
            diag.show();
            return res;
        }
        return false;

    }

    public void clearForm(){
        mEditTextDescription.setText("");
        mCheckBoxActive.setChecked(true);
    }
}
