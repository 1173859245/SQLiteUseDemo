package com.sanfeng.hotelbutler.bean;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class User {
    public int id;
    public String nickname;
    public String push_id;
    public String session_id;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", push_id='" + push_id + '\'' +
                ", session_id='" + session_id + '\'' +
                '}';
    }
}
