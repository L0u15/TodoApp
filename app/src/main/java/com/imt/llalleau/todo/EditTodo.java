package com.imt.llalleau.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EditTodo extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    static final  String TAG = EditTodo.class.getName();


    private Todo todo;

    EditText descEditText;
    CheckBox doneCheckBox;
    Spinner prioritySpinner;
    EditText deadlineEditText;
    Button validateBtn;

    ArrayAdapter<String> priorityAdapter;
    List<String> priorityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        descEditText = (EditText) findViewById(R.id.description);
        doneCheckBox = (CheckBox) findViewById(R.id.done);
        deadlineEditText = (EditText) findViewById(R.id.deadline);
        prioritySpinner = (Spinner) findViewById(R.id.priority);
        validateBtn = (Button) findViewById(R.id.validate);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent != null) {
            todo = (Todo) intent.getSerializableExtra(MainActivity.DATA_TODO_KEY);
            descEditText.setText(todo.getDescription());
            doneCheckBox.setActivated(todo.isDone());
            deadlineEditText.setText(todo.getDeadline());
            priorityList = new LinkedList<String>();
            priorityList.add("low");
            priorityList.add("normal");
            priorityList.add("high");
            priorityAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, priorityList
            );
            prioritySpinner.setAdapter(priorityAdapter);
            int index = priorityList.indexOf(todo.getPriority());
            prioritySpinner.setSelection(index);

        } else {
            finish();
        }
        validateBtn.setOnClickListener(this);
        deadlineEditText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == this.validateBtn) {
            onValidateBtnClick();
        } else if (v == this.deadlineEditText) {
            onDeadLineEditTextClick();
        }
    }

    private void onDeadLineEditTextClick() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    private void onValidateBtnClick() {
String desc = descEditText.getText().toString();
if(desc.isEmpty()){
    Toast.makeText(this,"Description can't be empty",Toast.LENGTH_LONG)
}
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i(TAG,"date set");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
    }
}
