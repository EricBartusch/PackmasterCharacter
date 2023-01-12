package thePackmaster.cards.transmutationpack;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.transmutationpack.TransmuteCardAction;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.*;

public class GlacialSynthesis extends AbstractHydrologistCard {
    public final static String ID = makeID("GlacialSynthesis");
    // intellij stuff SKILL, SELF, COMMON, , , 7, , 1, 1

    public GlacialSynthesis() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, Subtype.ICE);
        baseBlock = 7;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, block));
        atb(new TransmuteCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}