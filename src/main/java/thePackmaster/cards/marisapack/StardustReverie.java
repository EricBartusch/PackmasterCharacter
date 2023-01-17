package thePackmaster.cards.marisapack;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.actions.marisapack.ExhaustForChargeAction;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.util.Wiz;

import java.util.Locale;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class StardustReverie extends AbstractMarisaCard {
    public final static String ID = makeID(StardustReverie.class.getSimpleName());
    private static final int MAGIC = 1, UPG_MAGIC = 1;
    public static final int COST_MULT = 2;

    public StardustReverie() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ExhaustForChargeAction(magicNumber));
        Wiz.atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}
