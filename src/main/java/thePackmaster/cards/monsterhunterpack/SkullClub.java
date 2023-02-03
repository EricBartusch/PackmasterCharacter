package thePackmaster.cards.monsterhunterpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.actions.monsterhunterpack.EnrageFollowupAction;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class SkullClub extends AbstractMonsterHunterCard {
    public final static String ID = makeID("SkullClub");

    public static final int DAMAGE = 20;
    public static final int UPG_DAMAGE = 6;
    public static final int MAGIC = 2;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SkullClub() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new EnrageFollowupAction(p, m, magicNumber));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}