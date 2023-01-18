package thePackmaster.powers.dragonwrathpack;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import thePackmaster.actions.dragonwrathpack.SmiteAction;
import thePackmaster.cards.AbstractPackmasterCard;
import thePackmaster.cards.dragonwrathpack.HolyWrath;
import thePackmaster.powers.AbstractPackmasterPower;
import thePackmaster.util.Wiz;

import static thePackmaster.SpireAnniversary5Mod.addPotions;
import static thePackmaster.SpireAnniversary5Mod.makeID;

public class confessionpower extends AbstractPackmasterPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public static final String POWER_ID = makeID("confession");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public confessionpower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("mantra");

        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
        for (AbstractMonster m : Wiz.getEnemies()){
            addToBot(new SmiteAction(m,new DamageInfo(owner,amount,  DamageInfo.DamageType.THORNS)));
        }
        if (AbstractDungeon.actionManager.turnHasEnded){
            addToBot(new ReducePowerAction(owner,owner,this,amount/2));
        }
        return damageAmount;
    }
    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.GOLD.cpy());
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new confessionpower(owner, amount);
    }
    @Override
    public void onInitialApplication() {
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c instanceof HolyWrath && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE){
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
        }
    }
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c instanceof HolyWrath && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE){
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
        }
    }
}
