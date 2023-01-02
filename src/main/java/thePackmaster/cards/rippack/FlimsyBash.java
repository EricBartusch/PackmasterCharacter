package thePackmaster.cards.rippack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class FlimsyBash extends AbstractRippableCard {
    public final static String ID = makeID("FlimsyBash");

    public FlimsyBash() {
        this(null, null);
    }

    public FlimsyBash(AbstractRippedArtCard artCard, AbstractRippedTextCard textCard) {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 2;
        if (artCard == null && textCard == null) {
            setRippedCards(new FlimsyBashArt(this), new FlimsyBashText(this));
        } else if(artCard == null){
            setRippedCards(new FlimsyBashArt(this), textCard);
        } else {
            setRippedCards(artCard, new FlimsyBashText(this));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }
}
