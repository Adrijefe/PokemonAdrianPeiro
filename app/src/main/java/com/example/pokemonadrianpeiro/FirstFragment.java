package com.example.pokemonadrianpeiro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.example.pokemonadrianpeiro.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<Pokemon> pokemons;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        pokemons = new ArrayList<>();

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<Pokemon> adapter = new PokemonAdapter(
                getContext(), R.layout.pokemon_list_item,pokemons);

        setHasOptionsMenu(true);

        binding.listaPokemons.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Pokemon> pokemons = PokeAPI.buscar();
            getActivity().runOnUiThread(() -> {
                for (Pokemon p : pokemons) {
                    this.pokemons.add(p);
                }
                adapter.notifyDataSetChanged();
            });
        });

        binding.listaPokemons.setOnItemClickListener((parent, view1,position ,id )->{

            Pokemon pokemons = adapter.getItem(position);
            Bundle args = new Bundle();
            args.putSerializable("Pokemon",pokemons);

            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_pokemonsDetailsFragment2,args);
        });





    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_main, menu);
    }
    public void refresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String species = preferences.getString("species","");

        Toast.makeText(null, "", Toast.LENGTH_SHORT).show();

        executor.execute(() -> {
            PokeAPI api = new PokeAPI();
            String result = api.getNames("a");

            Log.d("DEBUG", result);
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "CLick");
        }
        if (id == R.id.action_settings){
            Intent i = new Intent(getContext(),SettingsActivity.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_refresh){
            refresh();
        }


        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
