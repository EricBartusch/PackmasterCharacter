package thePackmaster.vfx.rippack;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.SpireAnniversary5Mod.modID;

public class HazardousStrikeEffect {

    public static Texture SWORD = new Texture(modID + "Resources/images/vfx/rippack/sword.png");
    public static Texture BOLT = new Texture(modID + "Resources/images/vfx/rippack/bolt.png");
    public static float scale = 0.3f;


    public static AbstractGameEffect SwordThrow(AbstractCreature c) {
        AbstractPlayer p = AbstractDungeon.player;
        float angle = getAngle(c);
        Color color = new Color(1.0f, 1.0f, 120.0f / 255.0f, 1.0f);

        return new VfxBuilder(SWORD, p.hb.cX, p.hb.cY + (p.hb.height / 2) , 0.5f)
                .rotate(angle)
                .setColor(color)
                .scale(0.0f, scale)
                .andThen(0.75f)
                .playSoundAt(0.01f, makeID("RipPack_Charge"))
                .emitEvery(
                        (x, y) -> new VfxBuilder(BOLT, x, y, 0.25f)
                                .fadeOut(0.3f)
                                .setX(x + MathUtils.random(-300.0f * Settings.scale, 300.0f * Settings.scale))
                                .setY(y + MathUtils.random(-100.0f * Settings.scale, 100.0f * Settings.scale))
                                .setScale(0.1f)
                                .setColor(color)
                                .build(), 0.01f)
                .andThen(0.2f)
                .playSoundAt(0.01f, "ATTACK_HEAVY")
                .setScale(scale)
                .moveX(p.hb.cX, c.hb.cX)
                .moveY(p.hb.cY + (p.hb.height / 2), c.hb.cY)
                .emitEvery(
                        (x, y) -> new VfxBuilder(SWORD, x, y, 0.5f)
                                .fadeOutFromAlpha(0.3f, 0.4f)
                                .setScale(scale)
                                .setAngle(angle)
                                .setColor(color)
                                .build(), 0.05f)
                .build();
    }

    private static float getAngle(AbstractCreature c) {
        AbstractPlayer p = AbstractDungeon.player;

        float distance = c.hb.cX - p.hb.cX;
        float height = p.hb.cY + (p.hb.height / 2) - c.hb.cY;
        float angle = (float) Math.toDegrees(Math.atan(distance / height));

        //If the enemy hb is higher than the character's, the image gets flipped
        if(height < 0 ) {
            return angle + 180.0f;
        }
        return angle;
    }
}
