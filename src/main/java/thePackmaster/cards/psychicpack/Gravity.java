package thePackmaster.cards.psychicpack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.powers.psychicpack.GravityPower;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.*;

public class Gravity extends AbstractPackmasterCard {
    public final static String ID = makeID("Gravity");
    // intellij stuff power, none, uncommon, , , , , , 

    public Gravity() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GravityPower(p, 1));
    }

    public void upp() {
        isEthereal = false;
    }
}