package thePackmaster.cards.utilitypack;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.AbstractPackmasterCard;

public class Whispers extends AbstractPackmasterCard {
    public static final String ID = SpireAnniversary5Mod.makeID("Whispers");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DRAW = 3;
    private static final int UPGRADE_DRAW = 1;

    public Whispers() {
        super(ID, COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public void upp() {
        this.upgradeMagicNumber(UPGRADE_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster unused) {
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.addToBot(new MakeTempCardInHandAction(new Dazed(), 1));
    }
}
