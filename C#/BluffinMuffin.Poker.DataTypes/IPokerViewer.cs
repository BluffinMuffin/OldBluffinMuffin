namespace BluffinMuffin.Poker.DataTypes
{
    public interface IPokerViewer
    {
        void SetGame(IPokerGame c, string n);
        void Start();
    }
}
