package thePackmaster.actions.rippack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thePackmaster.cards.rippack.AbstractRippedArtCard;

import static thePackmaster.util.Wiz.att;

public class ExhaustAllArtCardsAction extends AbstractGameAction {
    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractRippedArtCard) {
                att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractRippedArtCard) {
                att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractRippedArtCard) {
                att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
            }
        }
        isDone = true;
    }
}
