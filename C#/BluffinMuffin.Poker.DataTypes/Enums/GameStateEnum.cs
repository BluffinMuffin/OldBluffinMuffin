namespace BluffinMuffin.Poker.DataTypes.Enums
{
    public enum GameStateEnum
    {
        Init,
        WaitForPlayers,
        WaitForBlinds,
        Playing,
        Showdown,
        DecideWinners,
        DistributeMoney,
        End
    }
}
