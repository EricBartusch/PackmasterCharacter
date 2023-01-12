package thePackmaster.cards.transmutationpack;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.transmutationpack.TransmuteCardAction;
import thePackmaster.cardmodifiers.transmutationpack.*;
import thePackmaster.cards.AbstractPackmasterCard;

import java.util.ArrayList;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.*;

public class GhostlyMist extends AbstractHydrologistCard implements TransmutableCard {
    public final static String ID = makeID("GhostlyMist");
    // intellij stuff skill, none, rare, , , , , 5, 

    public GhostlyMist() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE, Subtype.STEAM);
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new TransmuteCardAction(this));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
    
    @Override
    public ArrayList<AbstractExtraEffectModifier> getMutableAbilities() {
        ArrayList<AbstractExtraEffectModifier> list = new ArrayList<>();
        list.add(new TransmuteSelfEffect(this, true));
        return list;
    }

    @Override
    public void onTransmuted(AbstractCard newCard) {
        CardModifierManager.addModifier(newCard, new PurityModifier(magicNumber));
    }
}