package com.imt.llalleau.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayTodo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_todo);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Todo todo = (Todo) bundle.getSerializable(MainActivity.DATA_TODO_KEY);

        TextView textView = findViewById(R.id.id);
        textView.setText(todo.getId());

    }
}
