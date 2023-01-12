package thePackmaster.cards.ringofpainpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import thePackmaster.cards.AbstractPackmasterCard;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

public class Vision extends AbstractPackmasterCard {
    public final static String ID = makeID(Vision.class.getSimpleName());

    private static final int SEEK = 2;
    private static final int UP_SEEK = 1;

    public Vision() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = SEEK;
        exhaust = true;
        setBackgroundTexture(
                "anniv5Resources/images/512/ringofpain/" + type.name().toLowerCase() + ".png",
                "anniv5Resources/images/1024/ringofpain/" + type.name().toLowerCase() + ".png"
        );
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new BetterDrawPileToHandAction(this.magicNumber));
        atb(new ApplyPowerAction(p, p, new EquilibriumPower(p, 1), 1));
    }

    public void upp() {
        upgradeMagicNumber(UP_SEEK);
    }
}