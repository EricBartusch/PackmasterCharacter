package thePackmaster.cards.rippack;

import basemod.helpers.CardModifierManager;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.cardmodifiers.rippack.RippableModifier;

public abstract class AbstractRippableCard extends AbstractRipCard {

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);

        CardModifierManager.addModifier(this, new RippableModifier());
        if (!SpireAnniversary5Mod.oneFrameMode)
            setBackgroundTexture(
                    "anniv5Resources/images/512/rip/" + type.name().toLowerCase() + "-rippable.png",
                    "anniv5Resources/images/1024/rip/" + type.name().toLowerCase() + "-rippable.png"
            );
    }

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(cardID, cost, type, rarity, target, ThePackmaster.Enums.PACKMASTER_RAINBOW);
    }
}
