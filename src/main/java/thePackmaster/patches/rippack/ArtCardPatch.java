package thePackmaster.patches.rippack;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import static thePackmaster.SpireAnniversary5Mod.modID;
import static thePackmaster.patches.rippack.AllCardsRippablePatches.RipStatus.ART;

public class ArtCardPatch {

    static ShaderProgram shader = null;
    private static final Texture ART_GLOW = ImageMaster.loadImage(modID + "Resources/images/512/rip/card_art.png");


    //This pair of patches removes the shader from rendering the portrait
    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait")
    public static class SkipPortraitPre {

        @SpirePrefixPatch()
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                sb.setShader(null);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait")
    public static class SkipPortraitPost {

        @SpirePostfixPatch()
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                sb.setShader(shader);
            }
        }
    }

    //This pair of shaders removes the shader from rendering the title
    //A side effect of the shader causes some characters of the title to be removed
    @SpirePatch(clz = AbstractCard.class, method = "renderTitle")
    public static class SkipTitleRenderPre {

        @SpirePrefixPatch()
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                sb.setShader(null);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderTitle")
    public static class SkipTitleRenderPost {

        @SpirePostfixPatch()
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                sb.setShader(shader);
            }
        }
    }

    //Removes the flash from appearing where there is no card
    @SpirePatch(clz = CardFlashVfx.class, method = "render")
    public static class CutOffFlash {

        @SpirePrefixPatch()
        public static void Prefix(CardFlashVfx __instance, SpriteBatch sb, AbstractCard ___card, Color ___color, boolean ___isSuper) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(___card) == ART) {
                ReflectionHacks.setPrivate(__instance, CardFlashVfx.class, "img", new TextureAtlas.AtlasRegion(ART_GLOW, 0, 0, ART_GLOW.getWidth(), ART_GLOW.getHeight()));
            }
        }
    }

    //Removes the card glowing from appearing where there is no card
    @SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, Color.class})
    public static class CutOffGlow {

        @SpirePostfixPatch()
        public static void Postfix(CardGlowBorder __instance, AbstractCard ___card) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(___card) == ART) {
                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", new TextureAtlas.AtlasRegion(ART_GLOW, 0, 0, ART_GLOW.getWidth(), ART_GLOW.getHeight()));
            }
        }
    }

    //Removes the card background shadow from appearing where there is no card
    @SpirePatch(clz = AbstractCard.class, method = "getCardBgAtlas")
    public static class CutOffCardBg {

        @SpirePrefixPatch()
        public static SpireReturn<TextureAtlas.AtlasRegion> Postfix(AbstractCard __instance) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                return SpireReturn.Return(new TextureAtlas.AtlasRegion(ART_GLOW, 0, 0, ART_GLOW.getWidth(), ART_GLOW.getHeight()));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
    public static class SkipDescription {

        @SpirePrefixPatch()
        public static SpireReturn Postfix(AbstractCard __instance) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class SetCardToStatusAndDontDoStuff {

        @SpirePrefixPatch()
        public static SpireReturn Prefix(AbstractPlayer __instance, @ByRef AbstractCard card[], AbstractMonster monster, int energyOnUse) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(card[0]) == ART) {
                card[0].type = AbstractCard.CardType.STATUS;
                card[0].exhaust = true;
                AbstractDungeon.actionManager.addToBottom(new UseCardAction(card[0], monster));
                if (!card[0].dontTriggerOnUseCard) {
                    __instance.hand.triggerOnOtherCardPlayed(card[0]);
                }
                __instance.hand.removeCard(card[0]);
                __instance.cardInUse = card[0];
                card[0].target_x = (Settings.WIDTH / 2);
                card[0].target_y = (Settings.HEIGHT / 2);
                if (!__instance.hand.canUseAnyCard() && !__instance.endTurnQueued) {
                    AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCard.class, AbstractCreature.class })
    public static class SetCardToStatus {

        @SpirePrefixPatch()
        public static void Prefix(UseCardAction __instance, @ByRef AbstractCard card[], AbstractCreature target) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(card[0]) == ART) {
                card[0].type = AbstractCard.CardType.STATUS;
            }
        }
    }
}
