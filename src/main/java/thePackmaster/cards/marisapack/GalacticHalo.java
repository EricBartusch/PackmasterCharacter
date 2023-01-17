package thePackmaster.cards.marisapack;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.powers.marisapack.ChargeUpPower;
import thePackmaster.util.Wiz;
import thePackmaster.vfx.marisapack.BubbleEffect;

import java.util.Locale;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class GalacticHalo extends AbstractMarisaCard {
    public final static String ID = makeID(GalacticHalo.class.getSimpleName());
    private static final int BLK = 10, BLK_UPG = 3;
    private static final int MAGIC = 3;

    public GalacticHalo() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = MAGIC;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.vfx(new BubbleEffect(Color.SKY, ""), Settings.ACTION_DUR_FAST);
        blck();
        Wiz.applyToSelf(new ChargeUpPower(magicNumber));
    }

    public void upp() {
       upgradeBlock(BLK_UPG);
    }
}
