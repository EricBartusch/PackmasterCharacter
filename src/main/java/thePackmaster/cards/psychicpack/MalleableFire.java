package thePackmaster.cards.psychicpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.EasyXCostAction;
import thePackmaster.actions.upgradespack.SuperUpgradeAction;
import thePackmaster.cards.AbstractPackmasterCard;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.*;

public class MalleableFire extends AbstractPackmasterCard {
    public final static String ID = makeID("MalleableFire");
    // intellij stuff attack, enemy, rare, 9, 3, , , , 

    public MalleableFire() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        exhaust = true;
        this.cardsToPreview = new MoldedFire();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this,
                (x, params)->{
                    MoldedFire fire = new MoldedFire(x);
                    for (int i = 0; i < params[0]; ++i) {
                        SuperUpgradeAction.silentForceUpgrade(fire);
                    }

                    addToTop(new MakeTempCardInDrawPileAction(fire, 1, true, true));

                    for (int i = 0; i < x; ++i)
                    {
                        addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE, true));
                    }
                    return true;
                }, timesUpgraded));
    }

    public void upp() {
        upgradeDamage(3);
        if (cardsToPreview.timesUpgraded < this.timesUpgraded)
            SuperUpgradeAction.silentForceUpgrade(cardsToPreview);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if (c.cardsToPreview != null) {
            while (c.cardsToPreview.timesUpgraded < this.timesUpgraded) {
                SuperUpgradeAction.silentForceUpgrade(c.cardsToPreview);
            }
        }
        return c;
    }
}