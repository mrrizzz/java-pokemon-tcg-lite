package app.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Deck> decks;

    public User(){
        this.decks = new ArrayList<>();
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public List<Deck> getDecks(){
        return this.decks;
    }

    public void addDeck(Deck deck){
        this.decks.add(deck);
    }

    public void removeDeck(Deck deck){
        this.decks.remove(deck);
    }
}
