package com.lmu.warungdana.ModelSQLite;

public class UserLogModel {
    int id;
    String created_at;
    int id_cms_users;
    int id_modul;
    int id_data;
    String jenis;


    public UserLogModel(){
    }

    public UserLogModel(int id_cms_users,int id_modul,int id_data,String jenis){
        this.id_cms_users = id_cms_users;
        this.id_modul = id_modul;
        this.id_data = id_data;
        this.jenis = jenis;
    }

    public UserLogModel(int id,String created_at,int id_cms_users,int id_modul,int id_data,String jenis){
        this.id = id;
        this.created_at = created_at;
        this.id_cms_users = id_cms_users;
        this.id_modul = id_modul;
        this.id_data = id_data;
        this.jenis = jenis;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId_cms_users() {
        return id_cms_users;
    }

    public void setId_cms_users(int id_cms_users) {
        this.id_cms_users = id_cms_users;
    }

    public int getId_modul() {
        return id_modul;
    }

    public void setId_modul(int id_modul) {
        this.id_modul = id_modul;
    }

    public int getId_data() {
        return id_data;
    }

    public void setId_data(int id_data) {
        this.id_data = id_data;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

}
