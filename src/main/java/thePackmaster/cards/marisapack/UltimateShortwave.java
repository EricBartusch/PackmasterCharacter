package thePackmaster.cards.marisapack;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.powers.marisapack.ChargeUpPower;
import thePackmaster.util.Wiz;

import java.util.Locale;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class UltimateShortwave extends AbstractPackmasterCard implements AmplifyCard {
    public final static String ID = makeID(UltimateShortwave.class.getSimpleName());
    private static final int MAGIC = 1, S_MAGIC = 2, INC = 1;

    public UltimateShortwave() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = S_MAGIC;

        setBackgroundTexture("anniv5Resources/images/512/marisapack/" + type.name().toLowerCase(Locale.ROOT)+".png",
                "anniv5Resources/images/1024/marisapack/" + type.name().toLowerCase(Locale.ROOT)+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.vfx(new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.YELLOW.cpy(), ShockWaveEffect.ShockWaveType.NORMAL), Settings.ACTION_DUR_FASTER);
        Wiz.atb(new GainEnergyAction(magicNumber));
        Wiz.applyToSelf(new ChargeUpPower(secondMagic));
    }

    public void upp() {
        isInnate = true;
    }

    @Override
    public boolean skipUseOnAmplify() {
        return false;
    }

    @Override
    public void useAmplified(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                UltimateShortwave.this.magicNumber = UltimateShortwave.this.baseMagicNumber += INC;
                UltimateShortwave.this.secondMagic = UltimateShortwave.this.baseSecondMagic += INC;
                isDone = true;
            }
        });
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        isSecondMagicModified = secondMagic != S_MAGIC;
        isMagicNumberModified = magicNumber != MAGIC;
    }

    @Override
    public int getAmplifyCost() {
        return 1;
    }
}
