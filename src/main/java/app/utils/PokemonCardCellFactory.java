package app.utils;

import app.model.PokemonCard;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class PokemonCardCellFactory implements Callback<ListView<PokemonCard>, ListCell<PokemonCard>> {
    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 140;

    @Override
    public ListCell<PokemonCard> call(ListView<PokemonCard> pokemonCardListView) {
        return new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(PokemonCard card, boolean empty) {
                super.updateItem(card, empty);

                if (empty || card == null) {
                    clearContent();
                } else {
                    setText(buildCardText(card));
                    setGraphic(loadCardImage(card.getImageUrl()));
                }
            }

            private void clearContent() {
                setText(null);
                setGraphic(null);
            }

            private String buildCardText(PokemonCard card) {
                return String.format(
                        "%s (%s)\nType: %s\nHP: %d\n",
                        card.getName(),
                        card.getSubtypes().isEmpty() ? "No Subtype" : card.getSubtypes(),
                        card.getTypes().isEmpty() ? "No Type" : card.getTypes(),
                        card.getHp()
                );
            }

            private ImageView loadCardImage(String imageUrl) {
                try {
                    Image image = new Image(imageUrl, IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
                    imageView.setImage(image);
                    return imageView;
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + imageUrl);
                    return null;
                }
            }
        };
    }
}
