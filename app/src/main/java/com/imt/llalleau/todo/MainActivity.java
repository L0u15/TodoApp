package com.imt.llalleau.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    static final  String TAG = MainActivity.class.getName();
    static final int EDIT_TODO_RESULT=1;
    static final String DATA_TODO_KEY = "todo";

    protected ArrayAdapter<Todo> todosAdapter;
    protected ListView todosListView;
    protected List<Todo> todos;
    protected Todo currentTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todos = new LinkedList<>();
        todosAdapter = new ArrayAdapter<Todo>(this,android.R.layout.simple_list_item_1);
        todosListView = (ListView) findViewById(R.id.todoList);
        todosListView.setAdapter(todosAdapter);

        Date now = new Date();
        List<String> tags1 = Collections.singletonList("tag1");
        List<String> tags2 = Collections.singletonList("tag2");
        todosAdapter.add(new Todo("1","Faire les courses",now.toString(),tags1));
        todosAdapter.add(new Todo("2","Marcher sur la lune",now.toString(),tags2));

        todosListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.currentTodo = todosAdapter.getItem(position);
        Log.i(TAG,"Click sur un item:"+this.currentTodo);
        Intent intent= new Intent(this,EditTodo.class);
        intent.putExtra(DATA_TODO_KEY,currentTodo);
        startActivityForResult(intent,EDIT_TODO_RESULT);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //getTodosRequest();
    }

    private void getTodosRequest() {
        String getURL = "http://172.27.161.128:8000/todos";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                getURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, "response received");
                        getTodoResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "response error:"+error.getLocalizedMessage());
                    }
                }
        );

        queue.add(jsonRequest);
    }
    private void getTodoResponse(JSONArray json) {
        int size = json.length();
        todosAdapter.clear();
        for(int i=0;i<size;i++){
            try{
                JSONObject jsonObject = json.getJSONObject(i);
                String id = jsonObject.getString("id");
                String desc = jsonObject.getString("description");
                String deadline = jsonObject.getString("deadline");
                boolean done = jsonObject.getBoolean("done");
                String priority = jsonObject.getString("priority");
                String tagStr = jsonObject.getString("tags");
                String tags[]=tagStr.split(",");
                Todo todo = new Todo(id,desc,deadline,Arrays.asList(tags));
                todo.setDone(done);
                todo.setPriority(priority);
                todosAdapter.add(todo);


            }catch (Exception e){
                e.printStackTrace();
            }
            // Update UI
        }
    }
}
