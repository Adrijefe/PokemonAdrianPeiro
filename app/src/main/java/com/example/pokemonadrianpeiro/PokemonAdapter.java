package com.example.pokemonadrianpeiro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

LayoutInflater inflater;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Pokemon pokemon = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.pokemon_list_item,parent,false);

        TextView txtPokemonName = convertView.findViewById(R.id.textPokemonName);

        TextView txtPokemonSpecies = convertView.findViewById(R.id.txtPokemonSpecies);

        txtPokemonName.setText(pokemon.getName());

        txtPokemonSpecies.setText(pokemon.getSpecies());

        ImageView imgPokemonSprite = convertView.findViewById(R.id.imgPokemonSprite);

        Glide.with(getContext()).load(pokemon.getSprite()).into(imgPokemonSprite);

        if (convertView == null){
            if (inflater == null){
               inflater = LayoutInflater.from(getContext());
            }
            convertView = inflater.inflate(R.layout.pokemon_list_item,parent,false);

        }



        return convertView;
    }

    public PokemonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Pokemon> objects) {

        super(context, resource, objects);
    }
}
