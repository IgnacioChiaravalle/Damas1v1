package activity.damas1v1.TDAListaDE;

import java.util.Iterator;

public class ElementIterator<E> implements Iterator<E> {
	private PositionList<E> list;
	private Position<E> cursor;
	
	//Constructor:
	ElementIterator(PositionList<E> L) throws EmptyListException {
		list = L;
		cursor = (list.isEmpty()) ? null : list.first();
	}
	
	
	
	//Consultas:
	public boolean hasNext() {
		return cursor != null;
	}
	
	public E next() {
		try {
			if (cursor == null)
				throw new NoSuchElementException("No hay siguiente elemento.");
			E toReturn = cursor.element();
			cursor = (cursor == list.last()) ? null : list.next(cursor);
			return toReturn;
		}
		catch(EmptyListException | BoundaryViolationException | InvalidPositionException | NoSuchElementException exc) {
			System.out.println(exc.getMessage() + "\n");
			exc.printStackTrace();
			return null;
		}
	}
}
