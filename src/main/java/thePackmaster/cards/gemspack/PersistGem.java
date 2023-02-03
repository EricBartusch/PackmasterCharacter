package thePackmaster.cards.gemspack;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.cardmodifiers.gemspack.PersistGemMod;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class PersistGem extends AbstractGemsCard {
    public final static String ID = makeID("PersistGem");

    public PersistGem() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(CardTags.HEALING);
    }

    @Override
    public AbstractCardModifier myMod() {
        return new PersistGemMod();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

 
}