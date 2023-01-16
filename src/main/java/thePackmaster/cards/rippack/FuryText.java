package thePackmaster.cards.rippack;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import static thePackmaster.SpireAnniversary5Mod.makeID;

@NoCompendium
@NoPools
public class FuryText extends AbstractRippedTextCard {
    public final static String ID = makeID("FuryText");

    public FuryText() {
        super(ID, new Fury());
    }

    public FuryText(Fury sourceCard) {
        super(ID, sourceCard);
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }
}
