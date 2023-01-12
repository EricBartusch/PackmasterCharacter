package thePackmaster.cards.WitchesStrike;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.purple.CutThroughFate;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.witchesstrikepack.ManifestAction;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.orbs.WitchesStrike.CrescentMoon;
import thePackmaster.orbs.WitchesStrike.FullMoon;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class FullMoonHalo extends AbstractPackmasterCard {
    public final static String ID = makeID("FullMoonHalo");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public FullMoonHalo() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 10;
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new ManifestAction(new FullMoon()));
    }

    public void upp() {
        upgradeBlock(4);
    }
    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }
}
