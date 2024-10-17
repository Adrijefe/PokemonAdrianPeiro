package com.example.pokemonadrianpeiro;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<Pokemon> adapter = new PokemonAdapter(
                getContext(), R.layout.pokemon_list_item,pokemons);
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

    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            PokeAPI api = new PokeAPI();
            String result = api.getNames("a");

            Log.d("DEBUG", result);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
