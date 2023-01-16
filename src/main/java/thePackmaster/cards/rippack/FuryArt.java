package thePackmaster.cards.rippack;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import static thePackmaster.SpireAnniversary5Mod.makeID;

@NoCompendium
@NoPools
public class FuryArt extends AbstractRippedArtCard {
    public final static String ID = makeID("FuryArt");

    public FuryArt() {
        super(ID, new Fury());
    }

    public FuryArt(Fury sourceCard) {
        super(ID, sourceCard);
    }
}
