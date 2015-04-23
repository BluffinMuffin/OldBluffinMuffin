namespace BluffinMuffin.Protocol.Game
{
    public class PlayerSitOutCommand : AbstractGameCommand
    {
        public PlayerSitOutResponse Response(bool success)
        {
            return new PlayerSitOutResponse(this) { Success = success };
        }
    }
}
