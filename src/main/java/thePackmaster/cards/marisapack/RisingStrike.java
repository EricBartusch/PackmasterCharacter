package thePackmaster.cards.marisapack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.powers.marisapack.BuildingChargePower;
import thePackmaster.util.Wiz;

import java.util.Locale;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class RisingStrike extends AbstractMarisaCard {
    public final static String ID = makeID(RisingStrike.class.getSimpleName());
    private static final int DMG = 5, UPG_DMG = 3;
    private static final int MAGIC = 1;

    public RisingStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = DMG;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(CardTags.STRIKE);
        //cardsToPreview = new Spark();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.vfx(new PlasmaOrbActivateEffect(m.hb.cX, m.hb.cY), Settings.ACTION_DUR_FAST);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.applyToSelf(new BuildingChargePower(MAGIC));
        //Wiz.makeInHand(cardsToPreview);
    }

    public void upp() {
        upgradeDamage(UPG_DMG);
    }
}
