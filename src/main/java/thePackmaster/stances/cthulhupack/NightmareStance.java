package thePackmaster.stances.cthulhupack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import thePackmaster.util.Wiz;
import thePackmaster.vfx.downfallpack.AncientStanceParticleEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.applyToSelf;


public class NightmareStance extends AbstractStance {

    public static final String STANCE_ID = makeID("Nightmare");
    private static long sfxId = -1L;

    public NightmareStance() {
        this.ID = STANCE_ID;
        this.name = CardCrawlGame.languagePack.getStanceString(STANCE_ID).NAME;
        this.updateDescription();
    }// 24

    @Override
    public void updateAnimation() {

            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new AncientStanceParticleEffect(new Color(0.7F, 0.4F, 0.4F, 0.0F)));
                }
            }

            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0F) {
                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
                AbstractDungeon.effectsQueue.add(new StanceAuraEffect(""));
            }

    }


    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

        CardCrawlGame.sound.play("JAW_WORM_DEATH");

        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));

    }

    @Override
    public void onExitStance() {
        super.onExitStance();
        Wiz.atb(new MakeTempCardInHandAction(new Nightmare()));
    }

    public void atStartOfTurn() {
        Wiz.atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        Wiz.atb(new DrawCardAction(1));
    }

    @Override
    public void updateDescription() {
        this.description = CardCrawlGame.languagePack.getStanceString(STANCE_ID).DESCRIPTION[0];
    }
}
