package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PokemonCard {
    private final StringProperty name;
    private final StringProperty type;
    private final StringProperty supertype;
    private final StringProperty subtype;
    private final StringProperty imageUrl;
    private final StringProperty id;
    private final IntegerProperty hp;
    private final IntegerProperty count; // untuk menghitung jumlah kartu dalam deck

    public PokemonCard(String name, String type, String supertype, String subtype, String imageUrl, String id, int hp) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.supertype = new SimpleStringProperty(supertype);
        this.subtype = new SimpleStringProperty(subtype);
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.id = new SimpleStringProperty(id);
        this.hp = new SimpleIntegerProperty(hp);
        this.count = new SimpleIntegerProperty(0);
    }

    // Property getters
    public StringProperty nameProperty() { return name; }
    public StringProperty typeProperty() { return type; }
    public StringProperty supertypeProperty() { return supertype; }
    public StringProperty subtypeProperty() { return subtype; }
    public StringProperty imageUrlProperty() { return imageUrl; }
    public StringProperty idProperty() { return id; }
    public IntegerProperty hpProperty() { return hp; }
    public IntegerProperty countProperty() { return count; }

    // Value getters
    public String getName() { return name.get(); }
    public String getType() { return type.get(); }
    public String getSupertype() { return supertype.get(); }
    public String getSubtype() { return subtype.get(); }
    public String getImageUrl() { return imageUrl.get(); }
    public String getId() { return id.get(); }
    public int getHp() { return hp.get(); }
    public int getCount() { return count.get(); }

    // Value setters
    public void setName(String value) { name.set(value); }
    public void setType(String value) { type.set(value); }
    public void setSupertype(String value) { supertype.set(value); }
    public void setSubtype(String value) { subtype.set(value); }
    public void setImageUrl(String value) { imageUrl.set(value); }
    public void setId(String value) { id.set(value); }
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
        return getName() + " (" + getType() + ")";
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