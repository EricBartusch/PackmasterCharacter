package thePackmaster.hats;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import javassist.CtBehavior;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.packs.AbstractCardPack;

import java.io.IOException;


@SpirePatch(
        clz = VictoryScreen.class,
        method = "update"
)
public class VictoryScreenUnlockPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void Insert(VictoryScreen __instance) {
        // HAT UNLOCKS

        if (SpireAnniversary5Mod.allPacksMode) SpireAnniversary5Mod.logger.info("All packs mode - no Hat unlocks!");
        if (AbstractDungeon.player.chosenClass.equals(ThePackmaster.Enums.THE_PACKMASTER) && !SpireAnniversary5Mod.allPacksMode) {
            SpireAnniversary5Mod.logger.info("Unlocking new hats!");
            for (AbstractCardPack p : SpireAnniversary5Mod.currentPoolPacks) {
                SpireAnniversary5Mod.logger.info("Adding " + p.packID + " to unlocked hats!");
                if (!HatMenu.currentlyUnlockedHats.contains(p.packID)) {
                    HatMenu.currentlyUnlockedHats.add(p.packID);
                }
                HatMenu.refreshHatDropdown();

            }
            try {
                SpireAnniversary5Mod.logger.info("Saving unlocked hats!");
                SpireAnniversary5Mod.saveUnlockedHats(HatMenu.currentlyUnlockedHats);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ReturnToMenuButton.class, "hide");
            int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
            return lines;
        }
    }
}