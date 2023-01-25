package thePackmaster.cards.rippack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cardmodifiers.rippack.RippableModifier;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class FurySkill extends AbstractRipCard {
    public final static String ID = makeID("FurySkill");


    public FurySkill() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        baseBlock = 10;
        CardModifierManager.addModifier(this, new RippableModifier());
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }
}
