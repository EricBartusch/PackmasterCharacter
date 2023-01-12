package thePackmaster.cards.prismaticpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.AbstractPackmasterCard;

public class ManyAsOne extends AbstractPackmasterCard {
    public static final String ID = SpireAnniversary5Mod.makeID("ManyAsOne");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 4;

    public ManyAsOne() {
        super(ID, COST, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = DAMAGE;
    }

    @Override
    public void upp() {
        this.upgradeDamage(UPGRADE_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : p.discardPile.group) {
                    if (c.color != p.getCardColor()) {
                        this.addToBot(new DiscardToHandAction(c));
                    }
                }
                this.isDone = true;
            }
        });
    }
}
