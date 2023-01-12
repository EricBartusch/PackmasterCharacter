package thePackmaster.cards.ringofpainpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.powers.ringofpainpack.ConnectionPower;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

public class Connection extends AbstractPackmasterCard {
    public final static String ID = makeID(Connection.class.getSimpleName());

    public Connection() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setBackgroundTexture(
                "anniv5Resources/images/512/ringofpain/" + type.name().toLowerCase() + ".png",
                "anniv5Resources/images/1024/ringofpain/" + type.name().toLowerCase() + ".png"
        );
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new ConnectionPower(p), 0));
    }

    public void upp() {
        isInnate = true;
    }
}