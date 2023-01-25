package thePackmaster.patches.rippack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static thePackmaster.SpireAnniversary5Mod.makeShaderPath;
import static thePackmaster.patches.rippack.AllCardsRippablePatches.RipStatus.ART;
import static thePackmaster.patches.rippack.AllCardsRippablePatches.RipStatus.TEXT;

public class AllCardsRippablePatches {
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

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class CopyRippedStatus {

        @SpirePostfixPatch
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            AbstractCardFields.ripStatus.set(__result, AbstractCardFields.ripStatus.get(__instance));
            if(AbstractCardFields.ripStatus.get(__result) != RipStatus.WHOLE) {
                __result.exhaust = true;
            }
            return __result;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "render", paramtypez = {SpriteBatch.class})
    public static class RenderArtCardWithShader {

        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART) {
                initArtShader();
                sb.setShader(artShader);
            }
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == TEXT) {
                initTextShader();
                sb.setShader(textShader);
            }
        }

        @SpirePostfixPatch
        public static void PostFix(AbstractCard __instance, SpriteBatch sb) {
            if (AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == ART ||
                    AllCardsRippablePatches.AbstractCardFields.ripStatus.get(__instance) == TEXT) {
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
