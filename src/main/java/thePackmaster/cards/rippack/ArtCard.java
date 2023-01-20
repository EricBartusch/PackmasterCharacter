package thePackmaster.cards.rippack;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thePackmaster.patches.rippack.TypeOverridePatch;
import thePackmaster.vfx.rippack.ArtAttackArtEffect;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE;
import static thePackmaster.SpireAnniversary5Mod.*;
import static thePackmaster.SpireAnniversary5Mod.makeShaderPath;
import static thePackmaster.util.Wiz.atb;

@NoCompendium
@NoPools
@AutoAdd.Ignore
public class ArtCard extends AbstractRipCard {
    public final static String ID = makeID("ArtCard");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Rip"));

    public AbstractCard sourceCard;
    public static ShaderProgram shader = null;
    private static ArrayList<TooltipInfo> consumableTooltip;

    public ArtCard() {
        super(ID, 0, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public ArtCard(AbstractCard sourceCard) {
        super(ID, sourceCard.cost, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE);
        this.sourceCard = sourceCard;
        if(sourceCard instanceof ArtAttack) {
            target = CardTarget.ENEMY;
        }
        if(sourceCard.type == CardType.ATTACK) {
            TypeOverridePatch.setOverride(this, uiStrings.TEXT[2]);
        } else if(sourceCard.type == CardType.SKILL) {
            TypeOverridePatch.setOverride(this, uiStrings.TEXT[3]);
        }
        setDisplayRarity(sourceCard.rarity);
        name = sourceCard.name;
        initializeTitle();
        loadCardImage(makeImagePath("cards/" + sourceCard.cardID.replace(modID + ":", "") + ".png"));
        exhaust = true;
        baseMagicNumber = magicNumber = 12;
        upgraded = sourceCard.upgraded;
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(makeImagePath("cards/"+ name +"_p.png"));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(sourceCard instanceof ArtAttack) {
            AbstractGameEffect rainbowEffect = ArtAttackArtEffect.RainbowBoomerang(m);

            atb(new SFXAction(makeID("RipPack_Yay")));
            atb(new VFXAction(rainbowEffect));
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    if (rainbowEffect.isDone) {
                        isDone = true;
                    }
                }
            });
            atb(new SFXAction(makeID("RipPack_Harp")));
            atb(new LoseHPAction(m, m, magicNumber));
            atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, NONE)));
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (consumableTooltip == null) {
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

    @Override
    public void setDisplayRarity(CardRarity rarity) {
        switch(rarity) {
            case BASIC:
            case CURSE:
            case SPECIAL:
            case COMMON:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_COMMON;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_COMMON_L;
                switch(sourceCard.type) {
                    case STATUS:
                    case ATTACK:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON_L;
                        break;
                    case POWER:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_COMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_COMMON_L;
                        break;
                    default:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_COMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                }

                this.frameMiddleRegion = ImageMaster.CARD_COMMON_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_COMMON_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_COMMON_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_COMMON_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT_L;
                break;
            case UNCOMMON:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_UNCOMMON;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_UNCOMMON_L;
                switch(sourceCard.type) {
                    case STATUS:
                    case ATTACK:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON_L;
                        break;
                    case POWER:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON_L;
                        break;
                    default:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                }

                this.frameMiddleRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT_L;
                break;
            case RARE:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
                switch(sourceCard.type) {
                    case STATUS:
                    case ATTACK:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                        break;
                    case POWER:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_RARE;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_RARE_L;
                        break;
                    default:
                        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
                        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                }

                this.frameMiddleRegion = ImageMaster.CARD_RARE_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_RARE_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_RARE_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_RARE_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_RARE_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_RARE_FRAME_RIGHT_L;
                break;
            default:
                System.out.println("Attempted to set display rarity to an unknown rarity: " + rarity.name());
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new ArtCard(sourceCard);
    }

    private static void initShader() {
        if (shader == null) {
            try {
                shader = new ShaderProgram(
                        Gdx.files.internal(makeShaderPath("rippack/artHalf/vertex.vs")),
                        Gdx.files.internal(makeShaderPath("rippack/artHalf/fragment.fs"))
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
}
