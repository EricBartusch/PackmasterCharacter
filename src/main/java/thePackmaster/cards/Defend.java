package thePackmaster.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cardmodifiers.rippack.RippableModifier;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class Defend extends AbstractPackmasterCard {
    public final static String ID = makeID("Defend");
    // intellij stuff skill, self, basic, , ,  5, 3, , 

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
        CardModifierManager.addModifier(this, new RippableModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}