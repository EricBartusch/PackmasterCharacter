package thePackmaster.packs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.cards.rippack.*;

import java.util.ArrayList;

public class RipPack extends AbstractCardPack {
    public static final String ID = SpireAnniversary5Mod.makeID("RipPack");
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    public static final String NAME = UI_STRINGS.TEXT[0];
    public static final String DESC = UI_STRINGS.TEXT[1];
    public static final String AUTHOR = UI_STRINGS.TEXT[2];

    public RipPack() {
        super(ID, NAME, DESC, AUTHOR);
    }

    @Override
    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(ArtAttack.ID);
        cards.add(ArtAttackArt.ID);
        cards.add(ArtAttackText.ID);
        cards.add(FlimsyBash.ID);
        cards.add(FlimsyBashArt.ID);
        cards.add(FlimsyBashText.ID);
        cards.add(FragileShrug.ID);
        cards.add(FragileShrugArt.ID);
        cards.add(FragileShrugText.ID);
        cards.add(Inspiration.ID);
        cards.add(InspirationArt.ID);
        cards.add(InspirationText.ID);
        cards.add(DividedFury.ID);
        cards.add(SurprisePack.ID);
        cards.add(SurprisePackArt.ID);
        cards.add(SurprisePackText.ID);
        cards.add(ViciousCycle.ID);
        cards.add(ViciousCycleArt.ID);
        cards.add(ViciousCycleText.ID);
        return cards;
    }
}
