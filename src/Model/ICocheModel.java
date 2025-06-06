package Model;
import java.util.List;


public interface ICocheModel {

	List<Coche> list();

	int edit(Coche coche);

	int delete(int id);

	int create(Coche coche, Usuario usuario);

}
