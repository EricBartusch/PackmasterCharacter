package thePackmaster.actions.summonspack;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.util.TexLoader;

import static thePackmaster.SpireAnniversary5Mod.makePath;
import static thePackmaster.util.Wiz.adp;

public class PewcumberAction extends AbstractGameAction {
    private AbstractMonster m;
    private static final float DURATION = 1F;
    private static final String CUCUMBER_PATH = makePath("images/vfx/summonspack/Cucumber.png");
    private static final String POTATO_PATH = makePath("images/vfx/summonspack/Potato.png");
    private static final String EGGPLANT_PATH = makePath("images/vfx/summonspack/Eggplant.png");
    private static final String CARROT_PATH = makePath("images/vfx/summonspack/Carrot.png");
    private static final String BELL_GREEN_PATH = makePath("images/vfx/summonspack/BellPepperGreen.png");
    private static final String BELL_RED_PATH = makePath("images/vfx/summonspack/BellPepperRed.png");
    private static final String BELL_YELLOW_PATH = makePath("images/vfx/summonspack/BellPepperYellow.png");
    private static final String BELL_ORANGE_PATH = makePath("images/vfx/summonspack/BellPepperOrange.png");

    private static final float AIR_TIME = 0.3f;
    private static final float FADE_TIME = 0.5f;
    
    private AbstractPlayer p = adp();
    private DamageInfo info;
    private boolean thunkEffect;
    private Texture vegetableImage;
    private boolean pew;

    public PewcumberAction(AbstractMonster monster, DamageInfo info, boolean pew) {
        this.m = monster;
        this.info = info;
        this.pew = pew;
        actionType = ActionType.DAMAGE;
        duration = DURATION;
        thunkEffect = false;
        
        int x = MathUtils.random(0, 5);
        if (x == 0)
            vegetableImage = TexLoader.getTexture(CUCUMBER_PATH);
        else if (x == 1)
            vegetableImage = TexLoader.getTexture(POTATO_PATH);
        else if (x == 2)
            vegetableImage = TexLoader.getTexture(EGGPLANT_PATH);
        else if (x == 3)
            vegetableImage = TexLoader.getTexture(CARROT_PATH);
        else {
            int y = MathUtils.random(0, 3);
            if (y == 0)
                vegetableImage = TexLoader.getTexture(BELL_GREEN_PATH);
            else if (y == 1)
                vegetableImage = TexLoader.getTexture(BELL_RED_PATH);
            else if (y == 2)
                vegetableImage = TexLoader.getTexture(BELL_YELLOW_PATH);
            else
                vegetableImage = TexLoader.getTexture(BELL_ORANGE_PATH);
        }
    }

    public void update() {
        if (m == null || m.isDeadOrEscaped()) {
            isDone = true;
            return;
        }

        float targetX = 0f;
        float targetY = 0f;
        if (duration == DURATION) {
            targetX = m.hb.cX + AbstractDungeon.miscRng.random(-25.0f*Settings.xScale, 25.0f*Settings.xScale);
            targetY = m.hb.cY + AbstractDungeon.miscRng.random(-25.0f*Settings.yScale, 25.0f*Settings.yScale);
            float targetX2 = targetX + AbstractDungeon.miscRng.random(-400.0f*Settings.xScale, 400.0f*Settings.xScale);
            float targetY2 = targetY + AbstractDungeon.miscRng.random(-400.0f*Settings.yScale, 400.0f*Settings.yScale);
            AbstractGameEffect vegetableEffect = new VfxBuilder(vegetableImage, p.hb.cX, p.hb.cY, AIR_TIME)
                    .moveX(p.hb.cX, targetX, VfxBuilder.Interpolations.LINEAR)
                    .moveY(p.hb.cY, targetY, VfxBuilder.Interpolations.LINEAR)
                    .rotate(720.0f)
                    .andThen(AIR_TIME)
                    .moveX(targetX, targetX2, VfxBuilder.Interpolations.LINEAR)
                    .moveY(targetY, targetY2, VfxBuilder.Interpolations.LINEAR)
                    .rotate(360.0f)
                    .fadeOut(FADE_TIME)
                    .build();

            AbstractDungeon.topLevelEffects.add(vegetableEffect);
            if (pew)
                CardCrawlGame.sound.play(SpireAnniversary5Mod.PEW_KEY);
        }

        if (duration <= DURATION - AIR_TIME && !thunkEffect) {
            thunkEffect = true;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(targetX, targetY, AttackEffect.BLUNT_LIGHT));
            if (m != null && p.currentHealth > 0) {
                m.damage(info);
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
            isDone = true;
        }

        tickDuration();
    }
}
