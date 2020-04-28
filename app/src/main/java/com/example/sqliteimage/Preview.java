package com.example.sqliteimage;

public class Preview {
    int id;
    byte[] image;
    String descripton;

    public Preview(int id, byte[] image, String descripton) {
        this.id = id;
        this.image = image;
        this.descripton = descripton;
    }

    public Preview(byte[] image, String descripton) {
        this.image = image;
        this.descripton = descripton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }
}
