package thePackmaster.cardmodifiers.rippack;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class AddRippableModifier extends AbstractCardModifier {

    public static String ID = makeID("AddRippableModifier");

    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    boolean isInherent;

    public AddRippableModifier() {
        this(true);
    }

    public AddRippableModifier(boolean isInherent) {
        this.isInherent = isInherent;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return isInherent;
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0] + cardName;
    }

    public ArrayList<TooltipInfo> getCustomTooltips(AbstractCard card) {
        ArrayList<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("rippable")), BaseMod.getKeywordDescription(makeID("rippable"))));
        return tooltips;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new AddRippableModifier();
    }
}
