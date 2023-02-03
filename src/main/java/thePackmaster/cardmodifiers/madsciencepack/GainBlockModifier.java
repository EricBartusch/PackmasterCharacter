package thePackmaster.cardmodifiers.madsciencepack;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thePackmaster.SpireAnniversary5Mod;

public class GainBlockModifier extends AbstractMadScienceModifier {

    public GainBlockModifier(int valueIn){
        super(valueIn);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString(SpireAnniversary5Mod.makeID("MadScienceModifiers")).TEXT[4];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
     
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, value));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainBlockModifier(value);
    }
}