package thePackmaster.cards.dragonwrathpack;

import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.cards.AbstractPackmasterCard;

import java.util.Locale;

public abstract class AbstractDragonwrathCard extends AbstractPackmasterCard
{
    public AbstractDragonwrathCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);

        if (!SpireAnniversary5Mod.oneFrameMode) {
            setBackgroundTexture(
                    "anniv5Resources/images/512/dragonwrath/" + type.name().toLowerCase(Locale.ROOT) + ".png",
                    "anniv5Resources/images/1024/dragonwrath/" + type.name().toLowerCase(Locale.ROOT) + ".png"
            );
            setOrbTexture(
                    "anniv5Resources/images/512/dragonwrath/orb.png",
                    "anniv5Resources/images/1024/dragonwrath/orb.png"
            );
        }
    }

    public AbstractDragonwrathCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this (cardID, cost, type, rarity, target, ThePackmaster.Enums.PACKMASTER_RAINBOW);
    }
}
