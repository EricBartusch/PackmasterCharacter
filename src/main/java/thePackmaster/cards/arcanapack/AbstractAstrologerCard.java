package thePackmaster.cards.arcanapack;

import thePackmaster.cards.AbstractPackmasterCard;

public abstract class AbstractAstrologerCard extends AbstractPackmasterCard {
    public AbstractAstrologerCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        setBackgroundTexture(
                "anniv5Resources/images/512/astrologer/" + type.name().toLowerCase() + ".png",
                "anniv5Resources/images/1024/astrologer/" + type.name().toLowerCase() + ".png"
        );
        setOrbTexture(
                "anniv5Resources/images/512/astrologer/orb.png",
                "anniv5Resources/images/1024/astrologer/orb.png"
        );
    }

    public AbstractAstrologerCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        setBackgroundTexture(
                "anniv5Resources/images/512/astrologer/" + type.name().toLowerCase() + ".png",
                "anniv5Resources/images/1024/astrologer/" + type.name().toLowerCase() + ".png"
        );
        setOrbTexture(
                "anniv5Resources/images/512/astrologer/orb.png",
                "anniv5Resources/images/1024/astrologer/orb.png"
        );
    }
}
