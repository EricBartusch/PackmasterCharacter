package thePackmaster.cards.rippack;

import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.cardmodifiers.rippack.AddRippableModifier;
import thePackmaster.patches.rippack.AllCardsRippablePatches;

import java.util.ArrayList;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public abstract class AbstractRippableCard extends AbstractRipCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Rip"));
    private AbstractGameAction action;
    private static ArrayList<TooltipInfo> consumableTooltip;
    public static int cardsRippedThisTurn;
    public boolean isRipped = false;

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);

        AllCardsRippablePatches.AbstractCardFields.isRippable.set(this, true);
        CardModifierManager.addModifier(this, new AddRippableModifier());
        if (!SpireAnniversary5Mod.oneFrameMode)
            setBackgroundTexture(
                    "anniv5Resources/images/512/rip/" + type.name().toLowerCase() + "-rippable.png",
                    "anniv5Resources/images/1024/rip/" + type.name().toLowerCase() + "-rippable.png"
            );
    }

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(cardID, cost, type, rarity, target, ThePackmaster.Enums.PACKMASTER_RAINBOW);
    }

    public void onRip() { }

//    @Override
//    public List<String> getCardDescriptors() {
//        if(!isRipped) {
//            ArrayList<String> retVal = new ArrayList<>();
//            retVal.add(uiStrings.TEXT[0]);
//            return retVal;
//        } else {
//            return super.getCardDescriptors();
//        }
//    }
//
//    @Override
//    public List<TooltipInfo> getCustomTooltipsTop() {
//        if (consumableTooltip == null)
//        {
//            consumableTooltip = new ArrayList<>();
//            consumableTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("rippable")), BaseMod.getKeywordDescription(makeID("rippable"))));
//        }
//        List<TooltipInfo> compoundList = new ArrayList<>(consumableTooltip);
//        if (super.getCustomTooltipsTop() != null) compoundList.addAll(super.getCustomTooltipsTop());
//        return compoundList;
//    }
//
//    @SpireOverride
//    public void renderEnergy(SpriteBatch sb) {
//        if(!isRipped) {
//            SpireSuper.call(sb);
//        }
//    }
//
//    @Override
//    public String getTopText() {
//        if(isRipped) {
//            return null;
//        } else {
//            return super.getTopText();
//        }
//    }
}
