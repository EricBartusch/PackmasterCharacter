package thePackmaster.cards.rippack;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import static thePackmaster.SpireAnniversary5Mod.makeID;

@NoCompendium
@NoPools
public class FuryAttackText extends AbstractRippedTextCard {
    public final static String ID = makeID("FuryAttackText");

    public FuryAttackText() {
        super(ID, new FuryAttack(), CardColor.COLORLESS);
    }

    public FuryAttackText(FuryAttack sourceCard) {
        super(ID, sourceCard, CardColor.COLORLESS);
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }
}
