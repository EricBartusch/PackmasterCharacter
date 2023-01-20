package thePackmaster.cards.rippack;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.actions.rippack.RipCardAction2;
import thePackmaster.packs.AbstractCardPack;
import thePackmaster.vfx.rippack.ShowCardAndRipEffect;

import java.util.ArrayList;
import java.util.List;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.SpireAnniversary5Mod.makeShaderPath;
import static thePackmaster.util.Wiz.att;

public abstract class AbstractRippableCard extends AbstractRipCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Rip"));
    private AbstractGameAction action;
    private static ArrayList<TooltipInfo> consumableTooltip;
    public static int cardsRippedThisTurn;
    public boolean isRipped = false;
    public static ShaderProgram shader = null;

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);

        if (!SpireAnniversary5Mod.oneFrameMode)
            setBackgroundTexture(
                    "anniv5Resources/images/512/rip/" + type.name().toLowerCase() + "-rippable.png",
                    "anniv5Resources/images/1024/rip/" + type.name().toLowerCase() + "-rippable.png"
            );
    }

    public AbstractRippableCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(cardID, cost, type, rarity, target, ThePackmaster.Enums.PACKMASTER_RAINBOW);
    }


    public void onRightClick() {
        if(action == null && !isRipped) {
            if (canRip()) {
                action = new RipCardAction2(this);
                att(action);
                att(new WaitAction(0.1f));
                att(new WaitAction(0.1f));
                att(new WaitAction(0.1f));
                att(new WaitAction(0.1f));
                att(new VFXAction(new ShowCardAndRipEffect(this)));
            } else{
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 2.0F, uiStrings.TEXT[1], true));
            }
        }
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
        return AbstractDungeon.player.hand.size() != BaseMod.MAX_HAND_SIZE;
    }

    public void onRip() {
        cardsRippedThisTurn++;
    }


    @Override
    public void atTurnStart() {
        super.atTurnStart();
        cardsRippedThisTurn = 0;
    }

    @Override
    public List<String> getCardDescriptors() {
        if(!isRipped) {
            ArrayList<String> retVal = new ArrayList<>();
            retVal.add(uiStrings.TEXT[0]);
            return retVal;
        } else {
            return super.getCardDescriptors();
        }
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

    @Override
    public void render(SpriteBatch sb) {
        if(isRipped) {
            initShader();
            sb.setShader(shader);
            super.render(sb);
            sb.setShader(null);
        } else {
            super.render(sb);
        }
    }

    @Override
    public String getTopText() {
        if(isRipped) {
            return null;
        } else {
            return super.getTopText();
        }
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
