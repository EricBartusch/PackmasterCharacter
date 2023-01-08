package thePackmaster.cards.rippack;

import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thePackmaster.vfx.rippack.ArtAttackEffect;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE;
import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

@NoCompendium
@NoPools
public class ArtAttackArt extends AbstractRippedArtCard {
    public final static String ID = makeID("ArtAttackArt");

    private static ArrayList<TooltipInfo> consumableTooltip;

    public ArtAttackArt() {
        super(ID, new ArtAttack());
        baseMagicNumber = magicNumber = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameEffect rainbowEffect = ArtAttackEffect.RainbowBoomerang(m);

        CardCrawlGame.sound.play(makeID("yay"));
        atb(new VFXAction(rainbowEffect));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (rainbowEffect.isDone) {
                    isDone = true;
                }
            }
        });
        atb(new LoseHPAction(m, m, magicNumber));
        atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, NONE)));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        target = CardTarget.ENEMY;
    }

    public ArtAttackArt(ArtAttack sourceCard) {
        super(ID, sourceCard);
    }
}
