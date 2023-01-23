package thePackmaster.patches.rippack;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import thePackmaster.actions.rippack.RipCardAction;
import thePackmaster.vfx.rippack.ShowCardAndRipEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.SpireAnniversary5Mod.makeShaderPath;
import static thePackmaster.util.Wiz.att;

public class AnyCardRippablePatches {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Rip"));
    private static AbstractGameAction action;
    private static AbstractCard card;
    public static ShaderProgram shader = null;


    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class AbstractCardFields {
        public static SpireField<Boolean> isRippable = new SpireField(() -> false);
        public static SpireField<Boolean> isRipped = new SpireField(() -> false);
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

    @SpirePatch(clz = AbstractCard.class, method = "render", paramtypez = { SpriteBatch.class })
    public static class TheAngelFromMyNightmare {

        @SpirePrefixPatch()
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if(AbstractCardFields.isRipped.get(__instance)) {
                initShader();
                sb.setShader(shader);
            }
        }

        @SpirePostfixPatch()
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if(AbstractCardFields.isRipped.get(__instance)) {
                sb.setShader(null);
            }
        }
    }

    public static void clickUpdate() {
        if (!AbstractDungeon.isScreenUp && HitboxRightClick.rightClicked.get(card.hb) && !AbstractDungeon.actionManager.turnHasEnded) {
            onRightClick();
        }
    }

    public static void onRightClick() {
        if(action == null && AbstractCardFields.isRippable.get(card)) {
            if (canRip()) {
                AnyCardRippablePatches.AbstractCardFields.isRipped.set(card, true);
                AnyCardRippablePatches.AbstractCardFields.isRippable.set(card, false);
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

    private static void initShader() {
        if (shader == null) {
            try {
                shader = new ShaderProgram(
                        Gdx.files.internal(makeShaderPath("rippack/textHalf/vertex.vs")),
                        Gdx.files.internal(makeShaderPath("rippack/textHalf/fragment.fs"))
                );
                if (!shader.isCompiled()) {
                    System.err.println(shader.getLog());
                }
                if (shader.getLog().length() > 0) {
                    System.out.println(shader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("ERROR: Failed to init textHalf shader:");
                e.printStackTrace();
            }
        }
    }

}
