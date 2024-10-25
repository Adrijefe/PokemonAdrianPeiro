package com.example.pokemonadrianpeiro;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pokemonadrianpeiro.databinding.FragmentPokemonsDetailsBinding;

public class PokemonsDetailsFragment extends Fragment {

    private PokemonsDetailsViewModel mViewModel;
    private Activity convertView;

    public static PokemonsDetailsFragment newInstance() {
        return new PokemonsDetailsFragment();
    }

    public PokemonsDetailsFragment(){}

    private FragmentPokemonsDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= FragmentPokemonsDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args!=null){
            Pokemon pokemon = (Pokemon) args.getSerializable("Pokemon");
            if (pokemon != null){
                Log.d("XXXX",pokemon.toString());
                updateUi(pokemon);
            }
        }

    }

    private void updateUi(Pokemon pokemon){
        Log.d("Pokemon",pokemon.toString());
        binding.txtPokemonsNamesDetails.setText(pokemon.getName());
        binding.txtPokemonsSpeciesDetails.setText(pokemon.getSpecies());
       binding.txtPokemonSpriteDetails.setText(pokemon.getSprite());
        Glide.with(getContext()).load(pokemon.getSprite()).into(binding.imgPokemonSpriteDetails);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PokemonsDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}