package thePackmaster.cards.bitingcoldpack;

import thePackmaster.cards.AbstractPackmasterCard;

import static thePackmaster.SpireAnniversary5Mod.makeImagePath;

public abstract class BitingColdCard extends AbstractPackmasterCard {
    public BitingColdCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        setBackgroundTexture(
                makeImagePath("512/bitingcold/" + type.name().toLowerCase() + ".png"),
                makeImagePath("1024/bitingcold/" + type.name().toLowerCase() + ".png")
        );
    }
}
