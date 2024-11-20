package app.utils;

import app.model.PokemonCard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardLoader {
    public static List<PokemonCard> loadPokemonCardsFromJson(String jsonPath) {
        List<PokemonCard> cards = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonPath));

            if (rootNode.isArray()) {
                for (JsonNode cardNode : rootNode) {
                    String id = cardNode.get("id").asText();
                    String name = cardNode.get("name").asText();
                    String supertype = cardNode.get("supertype").asText();

                    String types;
                    if (cardNode.has("types") && cardNode.get("types").isArray() && cardNode.get("types").size() > 0) {
                        StringBuilder typeBuilder = new StringBuilder();
                        for (JsonNode typeNode : cardNode.get("types")) {
                            if (typeBuilder.length() > 0) {
                                typeBuilder.append(", ");
                            }
                            typeBuilder.append(typeNode.asText());
                        }
                        types = typeBuilder.toString();
                    } else {
                        types = "None";
                    }

                    String subtypes;
                    if (cardNode.has("subtypes") && cardNode.get("subtypes").isArray() && cardNode.get("subtypes").size() > 0) {
                        StringBuilder subtypeBuilder = new StringBuilder();
                        for (JsonNode subtypeNode : cardNode.get("subtypes")) {
                            if (subtypeBuilder.length() > 0) {
                                subtypeBuilder.append(", ");
                            }
                            subtypeBuilder.append(subtypeNode.asText());
                        }
                        subtypes = subtypeBuilder.toString();
                    } else {
                        subtypes = "None";
                    }

                    String firstType = types.equals("None") ? "Unknown" : types.split(",")[0].trim();
                    String imageUrl = "/assets/pokemon card image/" + firstType + "/" + firstType + " - " + name + ".png";

                    int hp = cardNode.has("hp") ? Integer.parseInt(cardNode.get("hp").asText()) : 0;

                    PokemonCard card = new PokemonCard(
                            id,
                            name,
                            supertype,
                            subtypes,
                            types,
                            imageUrl,
                            hp
                    );
                    cards.add(card);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cards;
    }
}