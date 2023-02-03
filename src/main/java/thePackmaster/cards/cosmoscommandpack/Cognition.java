package thePackmaster.cards.cosmoscommandpack;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThirdEyeEffect;
import thePackmaster.cards.marisapack.AmplifyCard;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;
import static thePackmaster.util.Wiz.vfx;

public class Cognition extends AbstractCosmosCard implements AmplifyCard {
    public final static String ID = makeID("Cognition");

    public Cognition() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        secondMagic = baseSecondMagic = 2;
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(secondMagic));
    }

    @Override
    public boolean skipUseOnAmplify() {
        return true;
    }

    @Override
    public void useAmplified(AbstractPlayer p, AbstractMonster m) {
        vfx(new ThirdEyeEffect(p.hb.cX, p.hb.cY));
        atb(new WaitAction(0.1F));
        atb(new ScryAction(magicNumber));
        atb(new DrawCardAction(secondMagic));
    }

    @Override
    public int getAmplifyCost() {
        return 1;
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}