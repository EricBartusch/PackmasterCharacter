package thePackmaster.cards.rippack;

import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.cards.AbstractPackmasterCard;

import static thePackmaster.SpireAnniversary5Mod.modID;

public abstract class AbstractRipCard extends AbstractPackmasterCard {
    public AbstractRipCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(cardID, cost, type, rarity, target, ThePackmaster.Enums.PACKMASTER_RAINBOW);
    }

    public AbstractRipCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        this(cardID, cost, type, rarity, target, color, getCardTextureString(cardID.replace(modID + ":", ""), type));
    }

    public AbstractRipCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color, String textureString) {
        super(cardID, cost, type, rarity, target, color, textureString);

        if (!SpireAnniversary5Mod.oneFrameMode)
        setBackgroundTexture(
                "anniv5Resources/images/512/rip/" + type.name().toLowerCase() + ".png",
                "anniv5Resources/images/1024/rip/" + type.name().toLowerCase() + ".png"
        );
    }
}
