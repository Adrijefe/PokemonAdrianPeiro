package com.example.pokemonadrianpeiro;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel  extends AndroidViewModel {
    private final Application app;
    private final BaseDatos baseDatos;
    private final PokemonDao pokemonDao;
    private LiveData<List<Pokemon>> pokemons;

    public PokemonViewModel(Application app) {
        super(app);

        this.app = app;
        this.baseDatos = BaseDatos.getDatabase(this.getApplication());
        this.pokemonDao = baseDatos.getPokemonDao();
    }

    public LiveData<List<Pokemon>> getPokemons() {
        return pokemonDao.getPokemons();
    }

    public void reload() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }

    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                    app.getApplicationContext()
            );

            PokeAPI api = new PokeAPI();
            ArrayList<Pokemon> result;

            result= api.buscar();




            pokemonDao.deletePokemons();
            pokemonDao.addPokemons(result);

            return null;
        }


    }
}