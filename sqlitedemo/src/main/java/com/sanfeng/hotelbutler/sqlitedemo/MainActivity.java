package com.sanfeng.hotelbutler.sqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sanfeng.hotelbutler.bean.Person;
import com.sanfeng.hotelbutler.bean.User;
import com.sanfeng.hotelbutler.utils.Dbservice;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insertUser;
    private  Button insertListPerson;
    private Button deleteUser;
    private Button updateUser;
    private Button queryAllPerson;
    private  Button updatePerson;
    private Dbservice dbservice;
    private Button getUser;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertUser=(Button)findViewById(R.id.insertUser);
        deleteUser=(Button)findViewById(R.id.deleteUser);
        updateUser=(Button)findViewById(R.id.updateUser);
        insertListPerson=(Button)findViewById(R.id.insertListPerson);
        queryAllPerson=(Button)findViewById(R.id.queryAllPerson);
        updatePerson=(Button)findViewById(R.id.updatePerson);
        getUser=(Button)findViewById(R.id.getUser);
        //初始化数据库
         dbservice=new Dbservice(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.insertUser:
                //模拟插入一条用户数据
                User user=new User();
                user.id=007;
                user.nickname="鹏鹏";
                user.push_id="123";
                user.session_id="89757";
                dbservice.insertUser(user);
                Toast.makeText(this,"成功插入一条用户信息",Toast.LENGTH_SHORT).show();
                break;
            case R.id.getUser:
                User getuser=null;
                getuser  =dbservice.getUser();
               Log.e("TAG",getuser.toString());
                break;
             case R.id.deleteUser :
                 //模拟删除用户表
                 dbservice.deleteUser();
                 Toast.makeText(this,"成功删除用户表",Toast.LENGTH_SHORT).show();
               break;
             case R.id.updateUser:
                 dbservice.updateUser("0001");
                 Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
               break;
             case R.id.insertListPerson:
                 dbservice.deletePerson();//插入前先情况表数据
                 //插入所有person
                 List<Person> persons=new ArrayList<>();
                 Person person = null;
                 for (int i=0;i<10;i++){
                     person=new Person(i,"路人"+i,i+10);
                     persons.add(person);
                 }
                 dbservice.insertAllPerson(persons);
                 Toast.makeText(this,"插入成功",Toast.LENGTH_SHORT).show();
               break;
             case R.id.queryAllPerson:
                 //查询所有person
                 List<Person> personList=null;
                 personList=dbservice.getAllPersons();
                 for (Person p:personList){
                     Log.e("TAG",p.toString());
                 }
               break;
             case R.id.updatePerson:
                 dbservice.updatePerson(100);
               break;


        }
    }
}
