package thePackmaster.cards.rippack;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thePackmaster.actions.rippack.RipCardAction;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.powers.rippack.DividedFuryPower;
import thePackmaster.vfx.rippack.ShowCardAndRipEffect;

import java.util.ArrayList;
import java.util.List;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;
import static thePackmaster.util.Wiz.att;

public abstract class AbstractRippableCard extends AbstractPackmasterCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Rip"));
    private AbstractGameAction action;
    protected ArrayList<AbstractCard> rippedParts;
    private static ArrayList<TooltipInfo> consumableTooltip;

    public static int cardsRippedThisTurn;

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    protected void setRippedCards(AbstractRippedArtCard artCard, AbstractRippedTextCard textCard) {
        rippedParts = new ArrayList<>();
        if(upgraded) {
            artCard.upgrade();
            textCard.upgrade();
        }
        rippedParts.add(artCard);
        rippedParts.add(textCard);
    }

    public ArrayList<AbstractCard> getRippedParts() {
        return rippedParts;
    }

    public void onRightClick() {
        if (canRip() && action == null) {
            CardCrawlGame.sound.play("MAP_CLOSE", 0.0F);
            att(new RipCardAction(this, rippedParts.get(0), rippedParts.get(1)));
            att(new WaitAction(0.1f));
            att(new WaitAction(0.1f));
            att(new WaitAction(0.1f));
            att(new WaitAction(0.1f));
            att(new VFXAction(new ShowCardAndRipEffect(this)));
        }
    }

    //For use when needing an upgraded version of the source card for `use`
    public void upgradeJustSource() {
        super.upgrade();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        rippedParts.get(0).upgrade();
        rippedParts.get(1).upgrade();
    }

    @Override
    public void update() {
        super.update();
        if (action != null && action.isDone) {
            action = null;
        }
        if (AbstractDungeon.player != null) {
            clickUpdate();
        }
    }

    public boolean canRip() {
        return AbstractDungeon.player.hand.group.size() != BaseMod.MAX_HAND_SIZE;
    }

    public void onRip() {
        cardsRippedThisTurn++;
        AbstractPower pow = AbstractDungeon.player.getPower(DividedFuryPower.POWER_ID);
        if(pow != null) {
            int amount = pow.amount;
            if(type == CardType.SKILL) {
                pow.flash();
                atb(new GainBlockAction(AbstractDungeon.player, amount));

            } else if(type == CardType.ATTACK) {
                pow.flash();
                atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));}
        }
    }

    @Override
    public List<String> getCardDescriptors() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(uiStrings.TEXT[0]);
        return retVal;
    }

    public void clickUpdate() {
        if (!AbstractDungeon.isScreenUp && HitboxRightClick.rightClicked.get(this.hb) && !AbstractDungeon.actionManager.turnHasEnded) {
            onRightClick();
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (consumableTooltip == null)
        {
            consumableTooltip = new ArrayList<>();
            consumableTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("rippable")), BaseMod.getKeywordDescription(makeID("rippable"))));
        }
        List<TooltipInfo> compoundList = new ArrayList<>(consumableTooltip);
        if (super.getCustomTooltipsTop() != null) compoundList.addAll(super.getCustomTooltipsTop());
        return compoundList;
    }
}
