package thePackmaster.cards.rippack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.rippack.ExhaustRandomNonArtCardsAction;
import thePackmaster.vfx.rippack.SlashDiagonalOppositeEffect;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;
import static thePackmaster.util.Wiz.att;

public class HazardousStrike extends AbstractRippableCard {
    public final static String ID = makeID("HazardousStrike");

    public HazardousStrike() {
        this(null, null);
    }

    public HazardousStrike(AbstractRippedArtCard artCard, AbstractRippedTextCard textCard) {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 28;
        baseMagicNumber = magicNumber = 3;
        if (artCard == null && textCard == null) {
            setRippedCards(new HazardousStrikeArt(this), new HazardousStrikeText(this));
        } else if(artCard == null){
            setRippedCards(new HazardousStrikeArt(this), textCard);
        } else {
            setRippedCards(artCard, new HazardousStrikeText(this));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(6);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new VFXAction(new SlashDiagonalOppositeEffect(m.hb.cX, m.hb.cY)));
    }

    @Override
    public void onRip() {
        super.onRip();
        att(new ExhaustRandomNonArtCardsAction(magicNumber)); //att to it runs before making the new text/art cards in hand
    }
}
