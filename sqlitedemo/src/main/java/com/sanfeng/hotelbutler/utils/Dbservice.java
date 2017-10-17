package com.sanfeng.hotelbutler.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sanfeng.hotelbutler.bean.Person;
import com.sanfeng.hotelbutler.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class Dbservice {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabaseWrite;
    SQLiteDatabase sqLiteDatabaseRead;

    /**
     * 创建数据库
     *
     * @param context 上下文
     */
    public Dbservice(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context, Constant.DATABASE_NAME, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                //创建persion表
                String sql = "create table " + Constant.TABLE_NAME + "(" + Constant.ID + " Integer primary key," + Constant.NAME
                        + " varchar(10)," + Constant.AGE + " Integer)";
                db.execSQL(sql);

                //创建用户表
                String sql2 = "create table sf_user("
                        + "id integer primary key autoincrement,"
                        + "nickname varchar,"
                        + "push_id varchar,"
                        + "session_id varchar"
                        + ")";

                db.execSQL(sql2);


            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        sqLiteDatabaseWrite = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabaseRead = sqLiteOpenHelper.getReadableDatabase();

    }



    /**
     * 得到用户信息
     *
     * @return user
     */
    public User getUser() {
        User user = null;
        Cursor c;
        try {
            String sql = "select * from sf_user";
            c = sqLiteDatabaseRead.rawQuery(sql, null);
            if (c.moveToNext()) {
                user = new User();
                user.id = c.getInt(0);
                user.nickname = c.getString(1);
                user.push_id = c.getString(2);
                user.session_id = c.getString(3);

            }
            c.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;

    }

    /**
     * 插入一条用户信息
     *
     * @param user
     */
    public void insertUser(User user) {
        String sql = "insert into sf_user(nickname,push_id,session_id) values(?,?,?)";
        sqLiteDatabaseWrite.execSQL(sql, new Object[]{user.nickname, user.push_id, user.session_id});

    }

    /**
     * 删除用户信息
     */
    public void deleteUser() {
        String sql = "delete from sf_user";
        sqLiteDatabaseWrite.execSQL(sql);
    }

    public void deletePerson() {
        String sql = "delete from " + Constant.TABLE_NAME;
        sqLiteDatabaseWrite.execSQL(sql);
    }

    /**
     * 修改一条用户信息
     *
     * @param session_id
     */
    public void updateUser(String session_id) {
        String sql = "update sf_user set session_id=?";
        sqLiteDatabaseWrite.execSQL(sql, new Object[]{session_id});

    }
    /**
     * 修改指定person信息id大于5的人年龄改成100
     *
     * @param age
     */
    public void updatePerson(int age) {
        String sql = "update "+Constant.TABLE_NAME+" set age=? where _id>5";
        sqLiteDatabaseWrite.execSQL(sql, new Object[]{age});

    }

    /**
     * 插入所有person
     *
     * @param persons
     */
    public void insertAllPerson(List<Person> persons) {
        //sqLiteDatabaseWrite.beginTransaction();

        for (int i = 0; i < persons.size(); i++) {
            String sql = "insert into " + Constant.TABLE_NAME + "(_id,name,age) values(?,?,?)";
            sqLiteDatabaseWrite.execSQL(sql, new Object[]{persons.get(i).get_id(), persons.get(i).getName(), persons.get(i).getAge()});
        }
        // sqLiteDatabaseWrite.setTransactionSuccessful();
        //sqLiteDatabaseWrite.endTransaction();

    }

    /**
     * 查找全部学生
     *
     * @return persons
     */
    public List<Person> getAllPersons() {

        List<Person> persons = new ArrayList<>();  //存放所有persopn
        Cursor cursor;
        if (sqLiteDatabaseRead.isOpen()) {
            String sql = "select * from " + Constant.TABLE_NAME + "";
            cursor = sqLiteDatabaseRead.rawQuery(sql, null);  //得到一组游标的结果
            while (cursor.moveToNext()) {                     //遍历游标
                //得到_id在cursor中的下标--cursor.getColumnIndex（"参数名"）返回值columnIndex-当前参数名在cursor中的下标
                int columnIndex = cursor.getColumnIndex(Constant.ID);
                int _id = cursor.getInt(columnIndex);
                String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
                int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
                Person person = new Person(_id, name, age);
                persons.add(person);
            }
            cursor.close();


        }
        return persons;
    }
}
