package thePackmaster.cardmodifiers.warriorpack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thePackmaster.SpireAnniversary5Mod;

public class FrontDamage extends AbstractDamageModifier {
    public static final String ID = SpireAnniversary5Mod.makeID("FrontDamage");

    public FrontDamage() {}

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target != null)
            if (target.currentBlock > 0)
                return super.atDamageFinalGive(damage, type, target, card) * 1.5F;
        return super.atDamageFinalGive(damage, type, target, card);
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new FrontDamage();
    }

    public boolean isInherent() {
        return true;
    }
}