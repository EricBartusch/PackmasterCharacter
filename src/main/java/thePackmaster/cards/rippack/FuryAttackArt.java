package thePackmaster.cards.rippack;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import java.awt.*;

import static thePackmaster.SpireAnniversary5Mod.makeID;

@NoCompendium
@NoPools
public class FuryAttackArt extends AbstractRippedArtCard {
    public final static String ID = makeID("FuryAttackArt");

    public FuryAttackArt() {
        super(ID, new FuryAttack(), CardColor.COLORLESS);
    }

    public FuryAttackArt(FuryAttack sourceCard) {
        super(ID, sourceCard, CardColor.COLORLESS);
    }
}
