package thePackmaster.cards.rippack;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import thePackmaster.cards.AbstractPackmasterCard;

import java.util.ArrayList;
import java.util.List;

import static thePackmaster.SpireAnniversary5Mod.*;

public abstract class AbstractRippedArtCard extends AbstractPackmasterCard {

    AbstractRippableCard sourceCard;
    private static ShaderProgram shader = null;
    private static final Texture ART_GLOW = ImageMaster.loadImage(modID + "Resources/images/512/rip/card_art.png");
    private static ArrayList<TooltipInfo> consumableTooltip;


    public AbstractRippedArtCard(String cardID, AbstractRippableCard sourceCard) {
        super(cardID, sourceCard.cost, sourceCard.type, sourceCard.rarity, CardTarget.NONE);
        this.sourceCard = sourceCard;
        exhaust = true;
    }

    public AbstractRippedArtCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if(sourceCard != null) {
            sourceCard.upgradeJustSource();
        }
    }

    @Override
    public void upp() {}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (consumableTooltip == null)
        {
            consumableTooltip = new ArrayList<>();
            consumableTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("art_card")), BaseMod.getKeywordDescription(makeID("art_card"))));
        }
        List<TooltipInfo> compoundList = new ArrayList<>(consumableTooltip);
        if (super.getCustomTooltipsTop() != null) compoundList.addAll(super.getCustomTooltipsTop());
        return compoundList;
    }

    @Override
    public void render(SpriteBatch sb) {
        initShader();
        sb.setShader(shader);
        super.render(sb);
        sb.setShader(null);
    }

    @Override
    public void renderHoverShadow(SpriteBatch sb) {

    }

    private static void initShader() {
        if (shader == null) {
            try {
                shader = new ShaderProgram(
                        Gdx.files.internal(makeShaderPath("artHalf/vertex.vs")),
                        Gdx.files.internal(makeShaderPath("artHalf/fragment.fs"))
                );
                if (!shader.isCompiled()) {
                    System.err.println(shader.getLog());
                }
                if (shader.getLog().length() > 0) {
                    System.out.println(shader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("ERROR: Failed to init artHalf shader:");
                e.printStackTrace();
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait")
    public static class SkipPortraitPre {

        @SpirePrefixPatch()
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractRippedArtCard) {
                sb.setShader(null);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderPortrait")
    public static class SkipPortraitPost {

        @SpirePostfixPatch()
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractRippedArtCard) {
                sb.setShader(shader);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderTitle")
    public static class SkipTitleRenderPre {

        @SpirePrefixPatch()
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractRippedArtCard) {
                sb.setShader(null);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderTitle")
    public static class SkipTitleRenderPost {

        @SpirePostfixPatch()
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractRippedArtCard) {
                sb.setShader(shader);
            }
        }
    }

    @SpirePatch(clz = CardFlashVfx.class, method = "render")
    public static class CutOffFlash {

        @SpirePrefixPatch()
        public static void Prefix(CardFlashVfx __instance, SpriteBatch sb, AbstractCard ___card, Color ___color, boolean ___isSuper) {
            if (___card instanceof AbstractRippedArtCard) {
                ReflectionHacks.setPrivate(__instance, CardFlashVfx.class, "img", new TextureAtlas.AtlasRegion(ART_GLOW, 0, 0, ART_GLOW.getWidth(), ART_GLOW.getHeight()));

            }
        }
    }

    @SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, Color.class})
    public static class CutOffGlow {

        @SpirePostfixPatch()
        public static void Postfix(CardGlowBorder __instance, AbstractCard ___card) {
            if (___card instanceof AbstractRippedArtCard) {
                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", new TextureAtlas.AtlasRegion(ART_GLOW, 0, 0, ART_GLOW.getWidth(), ART_GLOW.getHeight()));
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "getCardBgAtlas")
    public static class CutOffCardBg {

        @SpirePrefixPatch()
        public static SpireReturn<TextureAtlas.AtlasRegion> Postfix(AbstractCard __instance) {
            if (__instance instanceof AbstractRippedArtCard) {
                return SpireReturn.Return(new TextureAtlas.AtlasRegion(ART_GLOW, 0, 0, ART_GLOW.getWidth(), ART_GLOW.getHeight()));
            }
            return SpireReturn.Continue();
        }
    }
}
