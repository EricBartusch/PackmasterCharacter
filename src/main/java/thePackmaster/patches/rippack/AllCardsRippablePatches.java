package thePackmaster.patches.rippack;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CorruptionPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import thePackmaster.actions.rippack.RipCardAction;
import thePackmaster.cards.rippack.ArtAttack;
import thePackmaster.vfx.rippack.ShowCardAndRipEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.SpireAnniversary5Mod.makeShaderPath;
import static thePackmaster.cardmodifiers.rippack.RippableModifier.isRippable;
import static thePackmaster.patches.rippack.AllCardsRippablePatches.RipStatus.ART;
import static thePackmaster.util.Wiz.*;

//Houses patches related to the act of ripping cards
//Intial place the shader is applied when rendering art/text halves

public class AllCardsRippablePatches {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Rip"));
    private static AbstractGameAction action;
    private static AbstractCard card;

    static ShaderProgram artShader = null;
    static ShaderProgram textShader = null;

    public enum RipStatus {
        WHOLE,
        ART,
        TEXT
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class AbstractCardFields {
        public static SpireField<RipStatus> ripStatus = new SpireField(() -> RipStatus.WHOLE);
    }

    @SpirePatch(clz = AbstractCard.class, method = "update")
    public static class HelloThere {

        @SpirePostfixPatch()
        public static void Postfix(AbstractCard __instance) {
            if (action != null && action.isDone) {
                action = null;
            }
            if (AbstractDungeon.player != null) {
                card = __instance;
                clickUpdate();
            }
        }
    }

    public static void clickUpdate() {
        if (!AbstractDungeon.isScreenUp && HitboxRightClick.rightClicked.get(card.hb) && !AbstractDungeon.actionManager.turnHasEnded) {
            onRightClick();
        }
    }

    public static void onRightClick() {
        if(action == null && isRippable(card)) {
            if (canRip()) {
                action = new RipCardAction(card);
                att(action);
                att(new WaitAction(0.1f));
                att(new WaitAction(0.1f));
                att(new WaitAction(0.1f));
                att(new WaitAction(0.1f));
                att(new VFXAction(new ShowCardAndRipEffect(card)));
            } else{
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 2.0F, uiStrings.TEXT[1], true));
            }
        }
    }

    public static boolean canRip() {
        return AbstractDungeon.player.hand.size() != BaseMod.MAX_HAND_SIZE;
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class CopyRippedStatus {

        @SpirePostfixPatch
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            AbstractCardFields.ripStatus.set(__result, AbstractCardFields.ripStatus.get(__instance));
            if(!isWholeCard(__result)) {
                __result.exhaust = true;
            }
            return __result;
        }
    }

    //I don't want to see the quick attack animation when playing Art Halves of Attack cards
    //Skips over other stuff at the start of useCard, picks up at UseCardAction since I do want playing card type side-effects to happen
    //I'm sorry
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class DontDoStuffWhenArtCardUnlessArtAttackWhoopsLol {

        @SpirePrefixPatch()
        public static SpireReturn Prefix(AbstractPlayer __instance, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(card) == ART && card.cardID != ArtAttack.ID) {
                AbstractDungeon.actionManager.addToBottom(new UseCardAction(card, monster));
                if (!card.dontTriggerOnUseCard) {
                    __instance.hand.triggerOnOtherCardPlayed(card);
                }
                __instance.hand.removeCard(card);
                __instance.cardInUse = card;
                card.target_x = (Settings.WIDTH / 2);
                card.target_y = (Settings.HEIGHT / 2);
                if (card.costForTurn > 0
                        && !card.freeToPlay()
                        && !card.isInAutoplay
                        && (!__instance.hasPower(CorruptionPower.POWER_ID) || card.type != AbstractCard.CardType.SKILL)) {
                    __instance.energy.use(card.costForTurn);
                }
                if (!__instance.hand.canUseAnyCard() && !__instance.endTurnQueued) {
                    AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "render", paramtypez = {SpriteBatch.class})
    public static class RenderArtAndTextCardsWithShader {

        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (isArtCard(__instance)) {
                initArtShader();
                sb.setShader(artShader);
            }
            if (isTextCard(__instance)) {
                initTextShader();
                sb.setShader(textShader);
            }
        }

        @SpirePostfixPatch
        public static void PostFix(AbstractCard __instance, SpriteBatch sb) {
            if (!isWholeCard(__instance)) {
                sb.setShader(null);
            }
        }
    }

    private static void initArtShader() {
        if (artShader == null) {
            try {
                artShader = new ShaderProgram(
                        Gdx.files.internal(makeShaderPath("rippack/artHalf/vertex.vs")),
                        Gdx.files.internal(makeShaderPath("rippack/artHalf/fragment.fs"))
                );
                if (!artShader.isCompiled()) {
                    System.err.println(artShader.getLog());
                }
                if (artShader.getLog().length() > 0) {
                    System.out.println(artShader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("ERROR: Failed to init artHalf shader:");
                e.printStackTrace();
            }
        }
    }

    private static void initTextShader() {
        if (textShader == null) {
            try {
                textShader = new ShaderProgram(
                        Gdx.files.internal(makeShaderPath("rippack/textHalf/vertex.vs")),
                        Gdx.files.internal(makeShaderPath("rippack/textHalf/fragment.fs"))
                );
                if (!textShader.isCompiled()) {
                    System.err.println(textShader.getLog());
                }
                if (textShader.getLog().length() > 0) {
                    System.out.println(textShader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("ERROR: Failed to init textHalf shader:");
                e.printStackTrace();
            }

        }
    }
}
