package com.example.pokemonadrianpeiro;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokemonadrianpeiro.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<Pokemon> pokemons;
    ArrayAdapter<Pokemon> adapter;
    PokemonViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater);
        View view = binding.getRoot();

        pokemons = new ArrayList<>();
        adapter = new PokemonAdapter(
                getContext(),
                R.layout.pokemon_list_item, pokemons
        );

        binding.listaPokemons.setAdapter(adapter);
        binding.listaPokemons.setOnItemClickListener((adapterView, view1, i, l) -> {
            Pokemon pokemons = adapter.getItem(i);
            Bundle navegacion = new Bundle();
            navegacion.putSerializable("Pokemon", pokemons);
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_pokemonsDetailsFragment2,navegacion);


        });

        model = new ViewModelProvider(this).get(PokemonViewModel.class);
        model.getPokemons().observe(getViewLifecycleOwner(), pokemons -> {
            adapter.clear();
            adapter.addAll(pokemons);
        });

        return view;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        //refresh();


    }

    private void refresh() {
        model.reload();
        ArrayAdapter<Pokemon> adapter = new PokemonAdapter(
                getContext(), R.layout.pokemon_list_item,pokemons);


        binding.listaPokemons.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Pokemon> pokemons = PokeAPI.buscar();
            getActivity().runOnUiThread(() -> {
                for (Pokemon p : pokemons) {
                    adapter.add(p);
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Refresh) {
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "CLick");
            refresh();
        }
        if (id == R.id.settings){
            Log.d("XXX", "Settings");
            Intent i = new Intent(getContext(),SettingsActivity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
