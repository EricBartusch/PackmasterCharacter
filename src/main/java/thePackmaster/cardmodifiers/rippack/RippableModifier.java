package thePackmaster.cardmodifiers.rippack;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;
import java.util.List;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.SpireAnniversary5Mod.modID;

public class RippableModifier extends AbstractCardModifier {

    public static String ID = makeID("RippableModifier");

    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final Texture PERFORATION = ImageMaster.loadImage(modID + "Resources/images/512/rip/perforation.png");
    public static final Texture PERFORATION_SCV = ImageMaster.loadImage(modID + "Resources/images/1024/rip/perforation.png");

    boolean isInherent;

    public RippableModifier() {
        this(true);
    }

    public RippableModifier(boolean isInherent) {
        this.isInherent = isInherent;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, RippableModifier.ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return isInherent;
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0] + cardName;
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        sb.draw(PERFORATION_SCV, (Settings.WIDTH / 2) - 512.0f, (Settings.HEIGHT / 2) - 512.0f,
                512.0f, 512.0f, 1024.0f, 1024.0f,
                Settings.scale,  Settings.scale,
                card.angle, 0, 0, 1024, 1024, false, false);
    }
    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        sb.draw(PERFORATION, card.current_x - 256.0f, card.current_y - 256.0f,
                256.0f, 256.0f, 512.0f, 512.0f,
                card.drawScale * Settings.scale,  card.drawScale * Settings.scale,
                card.angle, 0, 0, 512, 512, false, false);
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        ArrayList<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("rippable")), BaseMod.getKeywordDescription(makeID("rippable"))));
        return tooltips;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RippableModifier();
    }

    public static boolean isRippable(AbstractCard card) {
        return CardModifierManager.hasModifier(card, ID);
    }

}
