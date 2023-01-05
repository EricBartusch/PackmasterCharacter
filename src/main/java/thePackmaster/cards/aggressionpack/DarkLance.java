package thePackmaster.cards.aggressionpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.patches.aggressionpack.DarkLancePatch;

public class DarkLance extends AbstractPackmasterCard {
    public static final String ID = SpireAnniversary5Mod.makeID("DarkLance");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int DRAW = 2;
    private static final int UPGRADE_DRAW = 1;

    public DarkLance() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public void upp() {
        this.upgradeDamage(UPGRADE_DAMAGE);
        this.upgradeMagicNumber(UPGRADE_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (this.stanceChangeCheck()) {
            this.addToBot(new DrawCardAction(this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (this.stanceChangeCheck()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    private boolean stanceChangeCheck() {
        return DarkLancePatch.changedStancesThisTurn;

    }
}
