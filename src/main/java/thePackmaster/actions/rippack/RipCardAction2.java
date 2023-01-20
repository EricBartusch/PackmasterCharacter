package thePackmaster.actions.rippack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thePackmaster.cards.rippack.AbstractRippableCard;
import thePackmaster.cards.rippack.ArtCard;

import static thePackmaster.util.Wiz.att;

public class RipCardAction2 extends AbstractGameAction {
    private AbstractRippableCard rippedCard;
    private ArtCard artCard;

    public RipCardAction2(AbstractRippableCard rippedCard) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_MED;
        this.rippedCard = rippedCard;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean found = false;
        for (AbstractCard card : p.hand.group) {
            if (card == rippedCard) {
                found = true;
                break;
            }
        }
        if(found && rippedCard != null) {
            artCard = new ArtCard(rippedCard);
            if (AbstractDungeon.player.hoveredCard == rippedCard) {
                AbstractDungeon.player.releaseCard();
            }
            AbstractDungeon.actionManager.cardQueue.removeIf(q -> q.card == rippedCard);
            att(new MakeTempCardInHandAction(artCard));
            att(new MakeTempCardInHandAction(rippedCard));
            rippedCard.onRip();
            p.hand.applyPowers();
            p.hand.glowCheck();
            artCard.superFlash();
        }
        isDone = true;
    }
}
