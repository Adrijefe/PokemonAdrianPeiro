package com.example.pokemonadrianpeiro;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("select * from pokemon")
    LiveData<List<Pokemon>> getPokemons();

    @Insert
    void addPokemon(Pokemon pokemon);

    @Insert
    void addPokemons (List<Pokemon> pokemons);

    @Delete
    void deletePokemon(Pokemon pokemon);

    @Query("Delete from Pokemon")
    void deletePokemons();

}
