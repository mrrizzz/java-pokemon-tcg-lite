package app.utils;

import app.model.PokemonCard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.smartcardio.Card;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardLoader {
    public static List<PokemonCard> loadPokemonCardsFromJson(String jsonPath){
        List<PokemonCard> cards = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonPath));

            if (rootNode.isArray()){
                for (JsonNode cardNode: rootNode){
                    String id = cardNode.get("id").asText();
                    String name = cardNode.get("name").asText();
                    String supertype = cardNode.get("supertype").asText();
                    String type = cardNode.has("types") ? cardNode.get("types").get(0).asText() : "None";
                    String subtype = cardNode.has("subtypes") ? cardNode.get("subtypes").get(0).asText() : "None";
                    String imageUrl = "/assets/pokemon card image/" + type + "/" + type + " - " + name + ".png";
                    int hp = cardNode.has("hp") ? Integer.parseInt(cardNode.get("hp").asText()) : 0;

                    PokemonCard card = new PokemonCard(
                            id,
                            name,
                            supertype,
                            Collections.singletonList(subtype),
                            Collections.singletonList(type),
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
