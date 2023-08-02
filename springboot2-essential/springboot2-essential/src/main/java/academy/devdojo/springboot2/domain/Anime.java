//O que tem no banco de Dados

package academy.devdojo.springboot2.domain;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data //Get e Set etc..
@AllArgsConstructor //Cria constructor para todos
public class Anime {
    private long id;
    private  String name;
}
