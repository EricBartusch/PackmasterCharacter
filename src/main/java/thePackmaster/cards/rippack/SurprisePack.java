package thePackmaster.cards.rippack;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.util.Wiz.atb;

public class SurprisePack extends AbstractRippableCard {
    public final static String ID = makeID("SurprisePack");

    public SurprisePack() {
        this(null, null);
    }

    public SurprisePack(AbstractRippedArtCard artCard, AbstractRippedTextCard textCard) {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
        if (artCard == null && textCard == null) {
            setRippedCards(new SurprisePackArt(this), new SurprisePackText(this));
        } else if(artCard == null){
            setRippedCards(new SurprisePackArt(this), textCard);
        } else {
            setRippedCards(artCard, new SurprisePackText(this));
        }
    }

    @Override
    public void upp() {
        upgradeSecondMagic(1);
        uDesc();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    @Override
    public void onRip() {
        super.onRip();
        atb(new GainEnergyAction(secondMagic));
    }
}
