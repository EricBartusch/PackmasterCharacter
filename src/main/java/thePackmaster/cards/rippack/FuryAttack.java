package thePackmaster.cards.rippack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import thePackmaster.cardmodifiers.rippack.RippableModifier;
import thePackmaster.vfx.rippack.DividedFuryEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

public class FuryAttack extends AbstractRipCard {
    public final static String ID = makeID("FuryAttack");


    public FuryAttack() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 14;
        CardModifierManager.addModifier(this, new RippableModifier());
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameEffect spear = Settings.FAST_MODE ? DividedFuryEffect.LightningSpearThrowFast(m, false) : DividedFuryEffect.LightningSpearThrow(m, false);
        atb(new VFXAction(spear));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (spear.isDone) {
                    isDone = true;
                }
            }
        });
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }
}
