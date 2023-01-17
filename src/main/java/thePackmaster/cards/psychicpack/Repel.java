package thePackmaster.cards.psychicpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.vfx.psychicpack.DarkBounceEffect;
import thePackmaster.vfx.psychicpack.PushEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.*;

public class Repel extends LockingCard {
    public final static String ID = makeID("Repel");
    // intellij stuff attack, enemy, uncommon, 6, 1, 7, 1, 1, 1

    public Repel() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseBlock = 8;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        if (m != null)
        {
            atb(new VFXAction(new PushEffect(p.hb.cX, m.hb.cX, m.hb.cY), 0.25f));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
            int amt = this.damage / 2;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    this.isDone = true;
                    for(int i = 0; i < amt; ++i) {
                        AbstractDungeon.effectList.add(new DarkBounceEffect(m.hb.cX, m.hb.cY));
                    }
                }
            });
        }

        blck();
        applyToEnemy(m, new WeakPower(m, this.magicNumber, false));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}