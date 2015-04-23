using System;
using System.Linq;
using System.Reflection;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace BluffinMuffin.Protocol
{
    public abstract class AbstractBluffinCommand : AbstractJsonCommand
    {
        public abstract BluffinCommandEnum CommandType { get; }

        /// <summary>
        /// Browsing all Types inheriting "AbstractBluffinCommand", it finds the type named exactly like the "CommandName" attribute in the JSON.
        /// </summary>
        public static AbstractBluffinCommand DeserializeCommand(string data)
        {
            JObject jObj = JsonConvert.DeserializeObject<dynamic>(data);
            var commandName = jObj["CommandName"].Value<String>();
            Type commType = Assembly.GetAssembly(typeof(AbstractBluffinCommand)).GetTypes().Single(t => t.IsClass && !t.IsAbstract && t.IsSubclassOf(typeof(AbstractBluffinCommand)) && t.Name == commandName);
            MethodInfo method = typeof(JsonConvert).GetMethods().First(m => m.Name == "DeserializeObject" && m.IsGenericMethod).MakeGenericMethod(new[] { commType });
            return (AbstractBluffinCommand)method.Invoke(null, new object[] { data });
        }
    }
}
