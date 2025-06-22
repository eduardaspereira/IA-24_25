package basicneuralnetwork.utilities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Adaptador para serializar e desserializar interfaces/classes abstratas, permitindo que sejam convertidas para JSON ou a partir de JSON pela biblioteca Google Gson.
 *
 * @author Andreia Qiu 79856
 * @author Eduarda Pereira 79749
 * @author Guilherme Carmo 79860
 * @version 1.0 (09/12/2024)
 * @inv A classe garante que as interfaces e classes abstratas sejam corretamente serializadas e desserializadas, preservando o tipo real durante o processo.
 */
// https://stackoverflow.com/questions/4795349/how-to-serialize-a-class-with-an-interface/9550086#9550086
public class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    /**
     * Serializa um objeto para JSON, incluindo o tipo real do objeto.
     *
     * @param object O objeto a ser serializado.
     * @param interfaceType O tipo da interface ou classe abstrata.
     * @param context O contexto de serialização.
     * @return O elemento JSON resultante da serialização.
     */
    @Override
    public final JsonElement serialize(final T object, final Type interfaceType, final JsonSerializationContext context) {
        final JsonObject member = new JsonObject();

        member.addProperty("type", object.getClass().getName());

        member.add("data", context.serialize(object));

        return member;
    }

    /**
     * Desserializa um elemento JSON para um objeto, recuperando o tipo real do objeto.
     *
     * @param elem O elemento JSON a ser desserializado.
     * @param interfaceType O tipo da interface ou classe abstrata.
     * @param context O contexto de desserialização.
     * @return O objeto resultante da desserialização.
     * @throws JsonParseException Se ocorrer um erro durante a desserialização.
     */
    @Override
    public final T deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) elem;
        final JsonElement typeString = get(member, "type");
        final JsonElement data = get(member, "data");
        final Type actualType = typeForName(typeString);

        return context.deserialize(data, actualType);
    }

    /**
     * Obtém o tipo real do objeto a partir do elemento JSON.
     *
     * @param typeElem O elemento JSON que contém o nome da classe.
     * @return O tipo real do objeto.
     * @throws JsonParseException Se a classe não for encontrada.
     */
    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * Obtém um elemento JSON a partir de um objeto JSON.
     *
     * @param wrapper O objeto JSON que contém o elemento.
     * @param memberName O nome do membro a ser obtido.
     * @return O elemento JSON correspondente ao membro.
     * @throws JsonParseException Se o membro não for encontrado.
     */
    private JsonElement get(final JsonObject wrapper, final String memberName) {
        final JsonElement elem = wrapper.get(memberName);

        if (elem == null) {
            throw new JsonParseException(
                    "no '" + memberName + "' member found in json file.");
        }
        return elem;
    }

}
