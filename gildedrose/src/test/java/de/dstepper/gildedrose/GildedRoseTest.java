package de.dstepper.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {


    @Test
    public void testQualityDecreasesAfterOneDay() {
        // Arrange
        Item[] items = new Item[]{new Item("foo", 1, 1)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

    @Test
    public void testSellByDateHasPassedTheQualityDegradesTwiceAsFast() {
        //Once the sell by date has passed, Quality degrades twice as fast
        Item[] items = new Item[]{new Item("foo", 0, 4)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, items[0].sellIn);
        assertEquals(2, items[0].quality);
    }

    @Test
    public void testTheQualityOfAnItemIsNeverNegative() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality);
    }

    @Test
    public void testAgedBrieActuallyIncreasesInQualityTheOlderItGets() {
        Item[] items = new Item[]{new Item("Aged Brie", 1, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].sellIn);
        assertEquals(1, items[0].quality);
    }

    @Test
    public void testAgedBrieAfterSellInIncreaseQualityByTwo() {
        Item[] items = new Item[]{new Item("Aged Brie", -1, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-2, items[0].sellIn);
        assertEquals(2, items[0].quality);
    }

    @Test
    public void testTheQualityOfAnItemIsNeverMoreThan50() {
        Item[] items = new Item[]{new Item("Aged Brie", 1, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    public void testSulfurasNeverHasToBeSoldOrDecreasesInQuality() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 1, 1)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, items[0].sellIn);
        assertEquals(1, items[0].quality);
    }

    @Test
    public void testBackStagePass() {
        // "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
        // Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
        // Quality drops to 0 after the concert
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 11, 1)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, items[0].quality);

        app.updateQuality();
        assertEquals(4, items[0].quality);

        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 6, 1)};
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, items[0].quality);

        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 1)};
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, items[0].quality);

        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 4, 1)};
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, items[0].quality);

        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 1, 1)};
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, items[0].quality);

        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 1)};
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality);
    }
}
