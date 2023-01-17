package thePackmaster.cards.WitchesStrike;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SeverSoul;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.witchesstrikepack.MoonlightBarrageAction;
import thePackmaster.actions.witchesstrikepack.MysticFlourishAction;
import thePackmaster.cardmodifiers.witchesstrikepack.InscribedMod;
import thePackmaster.cards.AbstractPackmasterCard;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class MoonlightBarrage extends AbstractWitchStrikeCard {
    public final static String ID = makeID("MoonlightBarrage");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public MoonlightBarrage() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        CardModifierManager.addModifier(this,new InscribedMod(true,true));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MoonlightBarrageAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
    @Override
    public String cardArtCopy() {
        return SeverSoul.ID;
    }
}
