package app.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private List<PokemonCard> cards;

    public Deck(String name){
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<PokemonCard> getCards() {
        return cards;
    }

    public void setCards(List<PokemonCard> cards) {
        this.cards = cards;
    }

    public void addCards(PokemonCard card){
        this.cards.add(card);
    }
}

