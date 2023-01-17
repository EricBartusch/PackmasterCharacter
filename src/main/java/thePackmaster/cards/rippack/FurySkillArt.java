package thePackmaster.cards.rippack;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import static thePackmaster.SpireAnniversary5Mod.makeID;

@NoCompendium
@NoPools
public class FurySkillArt extends AbstractRippedArtCard {
    public final static String ID = makeID("FurySkillArt");

    public FurySkillArt() {
        super(ID, new FurySkill(), CardColor.COLORLESS);
    }

    public FurySkillArt(FurySkill sourceCard) {
        super(ID, sourceCard, CardColor.COLORLESS);
    }
}
