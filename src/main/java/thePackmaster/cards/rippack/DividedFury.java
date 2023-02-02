package thePackmaster.cards.rippack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cardmodifiers.rippack.RippableModifier;

import static thePackmaster.SpireAnniversary5Mod.makeID;


public class DividedFury extends AbstractRipCard {
    public final static String ID = makeID("DividedFury");

    public DividedFury() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void upp() {
        isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard c : p.hand.group) {
            CardModifierManager.addModifier(c, new RippableModifier());
        }
//        applyToSelf(new DividedFuryPower(magicNumber));
    }
}
