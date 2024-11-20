package app.model;

public class PokemonCard {
    private final String id;
    private final String name;
    private final String supertype;
    private final String subtypes;
    private final String types;
    private final String imageUrl;
    private final int hp;

    public PokemonCard(String id, String name, String supertype, String subtypes,
                       String types, String imageUrl, int hp) {
        this.id = id;
        this.name = name;
        this.supertype = supertype;
        this.subtypes = subtypes;
        this.types = types;
        this.imageUrl = imageUrl;
        this.hp = hp;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSupertype() { return supertype; }
    public String getSubtypes() { return subtypes; }
    public String getTypes() { return types; }
    public String getImageUrl() { return imageUrl; }
    public int getHp() { return hp; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PokemonCard that = (PokemonCard) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
