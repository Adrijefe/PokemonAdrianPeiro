package com.example.pokemonadrianpeiro;



import androidx.room.Entity;
    import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Pokemon implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String species;
    private Integer weight;
    private String sprite;

    public Pokemon(int id, String name, String species, Integer weight, String sprite) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.weight = weight;
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }


    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", weight=" + weight +
                ", sprite='" + sprite + '\'' +
                '}';
    }
}
