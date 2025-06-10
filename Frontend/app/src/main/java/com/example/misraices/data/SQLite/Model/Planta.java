package com.example.misraices.data.SQLite.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "plantas",
        indices = {@Index(value = {"plantaIdServidor"}, unique = true)})
public class Planta {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "plantaIdServidor")
    private int plantaIdServidor;

    String nombre;
    String img;
    String cuidados;
    String video;
    private int usuarioId;

    public Planta() {
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlantaIdServidor() {
        return plantaIdServidor;
    }

    public void setPlantaIdServidor(int plantaIdServidor) {
        this.plantaIdServidor = plantaIdServidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Planta{" +
                "id=" + id +
                ", plantaIdServidor=" + plantaIdServidor +
                ", nombre='" + nombre + '\'' +
                ", img='" + img + '\'' +
                ", cuidados='" + cuidados + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
