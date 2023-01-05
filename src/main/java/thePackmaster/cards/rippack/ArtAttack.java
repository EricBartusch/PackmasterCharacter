package thePackmaster.cards.rippack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class ArtAttack extends AbstractRippableCard {
    public final static String ID = makeID("ArtAttack");

    public ArtAttack() {
        this(null, null);
    }

    public ArtAttack(AbstractRippedArtCard artCard, AbstractRippedTextCard textCard) {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 12;
        baseMagicNumber = magicNumber = 12;
        if (artCard == null && textCard == null) {
            setRippedCards(new ArtAttackArt(this), new ArtAttackText(this));
        } else if(artCard == null){
            setRippedCards(new ArtAttackArt(this), textCard);
        } else {
            setRippedCards(artCard, new ArtAttackText(this));
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }
}
