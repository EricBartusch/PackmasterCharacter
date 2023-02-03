package thePackmaster.cards.dimensiongatepack2;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cards.dimensiongateabstracts.AbstractDimensionalCardGordian;
import thePackmaster.powers.shamanpack.IgnitePower;
import thePackmaster.util.Wiz;

import static thePackmaster.SpireAnniversary5Mod.MAGIC;
import static thePackmaster.SpireAnniversary5Mod.makeID;

public class FlamePillar extends AbstractDimensionalCardGordian {
    public final static String ID = makeID("FlamePillar");

    public FlamePillar() {
        super(ID, 0, CardRarity.SPECIAL, CardType.SKILL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        tags.add(MAGIC);
        cardsToPreview = new Channel();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new IgnitePower(m, magicNumber));
        Wiz.atb(new MakeTempCardInHandAction(new Channel()));

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}