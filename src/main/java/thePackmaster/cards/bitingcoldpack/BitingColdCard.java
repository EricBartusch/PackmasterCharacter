package thePackmaster.cards.bitingcoldpack;

import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.AbstractPackmasterCard;

import static thePackmaster.SpireAnniversary5Mod.makeImagePath;

public abstract class BitingColdCard extends AbstractPackmasterCard {
    public BitingColdCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target, "bitingcold");

    }
}
