package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PokemonCard {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty supertype;
    private final ListProperty<String> subtypes;
    private final IntegerProperty hp;
    private final ListProperty<String> types;
    private final StringProperty imageUrl;
    private final IntegerProperty count;

    public PokemonCard(String id, String name, String supertype, List<String> subtypes,
                       List<String> types, String imageUrl, int hp) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.supertype = new SimpleStringProperty(supertype);
        this.subtypes = new SimpleListProperty<>(FXCollections.observableArrayList(subtypes));
        this.types = new SimpleListProperty<>(FXCollections.observableArrayList(types));
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.hp = new SimpleIntegerProperty(hp);
        this.count = new SimpleIntegerProperty(0);
    }

    // Property getters
    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty supertypeProperty() { return supertype; }
    public ListProperty<String> subtypesProperty() { return subtypes; }
    public ListProperty<String> typesProperty() { return types; }
    public StringProperty imageUrlProperty() { return imageUrl; }
    public IntegerProperty hpProperty() { return hp; }
    public IntegerProperty countProperty() { return count; }

    // Value getters
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getSupertype() { return supertype.get(); }
    public List<String> getSubtypes() { return subtypes.get(); }
    public List<String> getTypes() { return types.get(); }
    public String getImageUrl() { return imageUrl.get(); }
    public int getHp() { return hp.get(); }
    public int getCount() { return count.get(); }

    // Value setters
    public void setId(String value) { id.set(value); }
    public void setName(String value) { name.set(value); }
    public void setSupertype(String value) { supertype.set(value); }
    public void setSubtypes(List<String> value) { subtypes.setAll(value); }
    public void setTypes(List<String> value) { types.setAll(value); }
    public void setImageUrl(String value) { imageUrl.set(value); }
    public void setHp(int value) { hp.set(value); }
    public void setCount(int value) { count.set(value); }

    // Utility methods
    public void incrementCount() {
        count.set(getCount() + 1);
    }

    public void decrementCount() {
        if (getCount() > 0) {
            count.set(getCount() - 1);
        }
    }

    @Override
    public String toString() {
        String typeStr = getTypes().isEmpty() ? "No Type" : String.join(", ", getTypes());
        return String.format("%s (%s) - HP: %d", getName(), typeStr, getHp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonCard that = (PokemonCard) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}