package clientGame;

import java.util.TreeMap;

import pokerLogic.Card;



/**
 * @author Hocus
 *         This class is a OpaqueImagePanel and only display card.
 */
public class CardPanel extends OpaqueImagePanel
{
    private static final long serialVersionUID = 1L;
    
    private static final String PATH_IMAGE_CARD = "images/cards/";
    // private static final String DEFAULT_CARD = "default.png";
    private static final double SCALE = 50.0 / 100.0;
    
    /**
     * Array of card panels of a regular deck.
     */
    private static final TreeMap<Card, String> m_cardPanels = new TreeMap<Card, String>();
    
    /**
     * Retrieves the instance of a card panel using its card ID.
     */
    public static CardPanel getInstance(Card p_card)
    {
        return new CardPanel(CardPanel.getURL(p_card));
    }
    
    /**
     * Retrieves the instance of a card panel using its card ID.
     */
    private static String getURL(Card p_card)
    {
        if (!CardPanel.m_cardPanels.containsKey(p_card))
        {
            final String url = CardPanel.PATH_IMAGE_CARD + ((p_card.isNoCard() || p_card.isHidden()) ? "default" : p_card.getCode() + ".png");
            CardPanel.m_cardPanels.put(p_card, url);
            
        }
        
        return CardPanel.m_cardPanels.get(p_card);
    }
    
    private CardPanel(String p_url)
    {
        super(p_url, CardPanel.SCALE);
    }
    
    /**
     * Change card that is displayed.
     */
    public void changeCard(Card p_card)
    {
        setImage(Image.getInstance(CardPanel.getURL(p_card), CardPanel.SCALE));
        this.setVisible(!p_card.isNoCard());
    }
}
