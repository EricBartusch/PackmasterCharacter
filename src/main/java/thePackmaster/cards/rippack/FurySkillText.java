package thePackmaster.cards.rippack;

import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;

import static thePackmaster.SpireAnniversary5Mod.makeID;

@NoCompendium
@NoPools
public class FurySkillText extends AbstractRippedTextCard {
    public final static String ID = makeID("FurySkillText");

    public FurySkillText() {
        super(ID, new FurySkill(), CardColor.COLORLESS);
    }

    public FurySkillText(FurySkill sourceCard) {
        super(ID, sourceCard, CardColor.COLORLESS);
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}
