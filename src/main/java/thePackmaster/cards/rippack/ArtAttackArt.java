package thePackmaster.cards.rippack;

import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT;
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
        atb(new LoseHPAction(m, m, magicNumber));
        atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, BLUNT_LIGHT)));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        target = CardTarget.ENEMY;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (consumableTooltip == null)
        {
            consumableTooltip = new ArrayList<>();
            consumableTooltip.add(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[1], cardStrings.EXTENDED_DESCRIPTION[2]));
            consumableTooltip.add(new TooltipInfo(name, cardStrings.EXTENDED_DESCRIPTION[0]));
        }
        List<TooltipInfo> compoundList = new ArrayList<>(consumableTooltip);
        return compoundList;
    }

    public ArtAttackArt(ArtAttack sourceCard) {
        super(ID, sourceCard);
    }
}
