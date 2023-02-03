package thePackmaster.cards.dimensiongateabstracts;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractDimensionalCardVault extends AbstractDimensionalCard {

    public AbstractDimensionalCardVault(final String cardID, final int cost, final CardRarity rarity, final CardType type, final CardTarget target) {
        super(cardID, cost, rarity, type, target, "dimension/vault");
    }

    public AbstractDimensionalCardVault(final String cardID, final int cost, final CardRarity rarity, final CardType type, final CardTarget target, final CardColor color) {
        super(cardID, cost, rarity, type, target, "dimension/vault", color);
    }

}