package app.utils;

import app.model.PokemonCard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CardLoader {
    public static List<PokemonCard> loadPokemonCardsFromJson(String jsonPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonPath));

            if (rootNode.isArray()) {
                return StreamSupport.stream(rootNode.spliterator(), false)
                        .map(CardLoader::parseCardNode)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Pokemon cards from JSON: " + e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    private static PokemonCard parseCardNode(JsonNode cardNode) {
        String id = getTextValue(cardNode, "id");
        String name = getTextValue(cardNode, "name");
        String supertype = getTextValue(cardNode, "supertype");
        String types = parseArrayField(cardNode, "types");
        String subtypes = parseArrayField(cardNode, "subtypes");

        String firstType = types.equals("None") ? "Unknown" : types.split(",")[0].trim();
        String imageUrl = String.format("/assets/pokemon card image/%s/%s - %s.png", firstType, firstType, name);

        int hp = getIntValue(cardNode, "hp", 0);

        return new PokemonCard(id, name, supertype, subtypes, types, imageUrl, hp);
    }

    private static String getTextValue(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asText() : "Unknown";
    }

    private static int getIntValue(JsonNode node, String fieldName, int defaultValue) {
        return node.has(fieldName) ? node.get(fieldName).asInt() : defaultValue;
    }

    private static String parseArrayField(JsonNode node, String fieldName) {
        if (node.has(fieldName) && node.get(fieldName).isArray()) {
            return StreamSupport.stream(node.get(fieldName).spliterator(), false)
                    .map(JsonNode::asText)
                    .collect(Collectors.joining(", "));
        }
        return "None";
    }
}
